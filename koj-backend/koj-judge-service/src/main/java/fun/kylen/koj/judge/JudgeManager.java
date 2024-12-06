package fun.kylen.koj.judge;

import cn.hutool.core.util.StrUtil;
import fun.kylen.koj.constant.JudgeConstant;
import fun.kylen.koj.constant.JudgeVerdictConstant;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.ProblemCase;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.domain.SubmissionCase;
import fun.kylen.koj.exception.CompileError;
import fun.kylen.koj.exception.SystemError;
import fun.kylen.koj.model.LanguageCmdArgs;
import fun.kylen.koj.model.RunResult;
import fun.kylen.koj.sandbox.CodeExecutor;
import fun.kylen.koj.utils.CommonDaoUtil;
import fun.kylen.koj.utils.JudgeUtil;
import fun.kylen.koj.utils.RedisUtil;
import fun.kylen.koj.vo.DebugVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: KyLen
 * @Date: 2024/11/30 14:46
 * @Description: 管理编译代码，运行代码和判题逻辑
 */
@Component
@Slf4j
public class JudgeManager {
    @Autowired
    private CodeExecutor codeExecutor;
    @Resource
    private Map<String, LanguageCmdArgs> languageMap;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CommonDaoUtil daoUtil;

    /**
     * 编译代码，运行代码，判题
     * @param submitId
     */
    public void judge(Long submitId) {
        // todo validator
        // todo 理论上这里不需要对题目的数据进行校验了
        // 拿到代码
        Submission submission = daoUtil.getSubmissionById(submitId);
        if (submission == null) {
            throw new RuntimeException("不存在submitId为 " + submitId + " 的提交");
        }
        // 拿到代码和语言
        String language = submission.getLanguage();
        String code = submission.getCode();
        // 拿到题目
        Long problemId = submission.getProblemId();
        Problem problem = daoUtil.getProblemById(problemId);
        if (problem == null) {
            throw new RuntimeException("不存在problemId为 " + problemId + " 的题目");
        }
        // 拿到时空限制
        Integer timeLimit = problem.getTimeLimit();
        Integer memoryLimit = problem.getMemoryLimit();
        Integer stackLimit = problem.getStackLimit();
        // 拿到判题类型
        String judgeMode = problem.getJudgeMode();
        if (!judgeMode.equals(JudgeConstant.DEFAULT_JUDGE_TYPE) &&
                !judgeMode.equals(JudgeConstant.SPJ_JUDGE_TYPE) &&
                !judgeMode.equals(JudgeConstant.INTERACTIVE_JUDGE_TYPE)) {
            throw new RuntimeException("此判题类型不存在:" + judgeMode);
        }
        // 拿到题目测试用例
        List<ProblemCase> problemCaseList = daoUtil.getProblemCaseByProblemId(problemId);
        // 拿到用户在该题目提交下的测试用例（koj-service已经生成了）
        List<SubmissionCase> submissionCaseList = daoUtil.getSubmissionCaseBySubmissionId(submitId,
                                                                                          problemId,
                                                                                          submission.getUserId());
        // 如果两个大小不一样则出错
        if (problemCaseList.size() != submissionCaseList.size()) {
            log.error("题目测试用例数量与提交测试用例数量不匹配");
            throw new RuntimeException("题目测试用例数量与提交测试用例数量不匹配");
        }
        // 拿到语言配置
        LanguageCmdArgs languageCmdArgs = languageMap.get(language);
        if (languageCmdArgs == null) {
            log.error("不存在该语言的配置：{}", language);
            throw new RuntimeException();
        }
        // 1.编译代码
        submission.setVerdict(JudgeVerdictConstant.COMPILING);
        daoUtil.updateSubmissionVerdict(submission);
        String fileId = null;
        try {
            fileId = codeExecutor.compile(languageCmdArgs, code);
        } catch (CompileError e) {
            // 编译失败
            submission.setVerdict(JudgeVerdictConstant.COMPILE_ERROR);
            submission.setRunMessage(e.getErrorMessage());
            daoUtil.updateSubmissionVerdict(submission);
            throw new RuntimeException();
        } catch (SystemError e) {
            // 系统错误
            log.error("编译代码后无编译文件ID，submitId: {} ，报错： {}", submitId, e.getMessage());
            submission.setVerdict(JudgeVerdictConstant.SYSTEM_ERROR);
            submission.setRunMessage(e.getErrorMessage());
            daoUtil.updateSubmissionVerdict(submission);
            throw new RuntimeException();
        } catch (Exception e) {
            log.error("编译代码出现了错误，submitId: {} ，报错： {}", submitId, e.getMessage());
            submission.setVerdict(JudgeVerdictConstant.SYSTEM_ERROR);
            submission.setRunMessage("编译时出现了问题，请联系管理员");
            daoUtil.updateSubmissionVerdict(submission);
            throw new RuntimeException();
        }

        try {
            // 2. 并发运行代码，获取结果列表
            submission.setVerdict(JudgeVerdictConstant.RUNNING);
            daoUtil.updateSubmissionVerdict(submission);
            List<String> inputList = problemCaseList.stream().map(ProblemCase::getInput).collect(Collectors.toList());
            List<RunResult> runResult = codeExecutor.runAll(languageCmdArgs,
                                                              inputList,
                                                              timeLimit,
                                                              memoryLimit,
                                                              stackLimit,
                                                              fileId);
            if (inputList.size() != runResult.size()) {
                log.error("运行结果数量与测试用例数量不匹配");
                submission.setVerdict(JudgeVerdictConstant.SYSTEM_ERROR);
                submission.setRunMessage("运行结果数量与测试用例数量不匹配，请联系管理员");
                daoUtil.updateSubmissionVerdict(submission);
                throw new RuntimeException();
            }
            // 3. 运行完了，对结果进行一一比对，并更新评测状态到数据库
            submission.setVerdict(JudgeVerdictConstant.JUDGING);
            daoUtil.updateSubmissionVerdict(submission);
            List<String> outputList = problemCaseList.stream().map(ProblemCase::getOutput).collect(Collectors.toList());
            int verdict = JudgeVerdictConstant.ACCEPTED;
            for (int i = 0; i < outputList.size(); i++) {
                RunResult result = runResult.get(i);
                SubmissionCase submissionCase = submissionCaseList.get(i);
                submissionCase.setVerdict(result.getVerdict());
                submissionCase.setTime(result.getRunTime());
                submissionCase.setMemory(result.getRunMemory());
                submissionCase.setJudgeOutput(result.getMessage());
                // 结果比对
                if (submissionCase.getVerdict() == JudgeVerdictConstant.ACCEPTED) {
                    if (JudgeUtil.check(outputList.get(i), result.getUserOutput())) {
                        submissionCase.setVerdict(JudgeVerdictConstant.ACCEPTED);
                    } else {
                        submissionCase.setVerdict(JudgeVerdictConstant.WRONG_ANSWER);
                    }
                }
                // 设置提交状态
                if (submissionCase.getVerdict() != JudgeVerdictConstant.ACCEPTED && verdict == JudgeVerdictConstant.ACCEPTED) {
                    verdict = submissionCase.getVerdict();
                }
                // 更新当前评测点到数据库
                daoUtil.updateSubmissionCase(submissionCase);
            }
            // 更新提交的评测状态，如果不是Accepted，则按照顺序取第一个出现的非Accepted状态
            submission.setVerdict(verdict);
            daoUtil.updateSubmissionVerdict(submission);
        } catch (Exception e) {
            log.error("运行代码或判题出现了问题：{}",e.getMessage());
            submission.setVerdict(JudgeVerdictConstant.SYSTEM_ERROR);
            daoUtil.updateSubmissionVerdict(submission);
        } finally {
            // 4. 删除文件
            codeExecutor.delete(fileId);
        }
    }

    /**
     * 编译代码，运行代码，测试数据
     * @param debugId
     */
    public void debug(String debugId) {
        DebugVO debugVO = redisUtil.getDebugVO(debugId);
        if (debugVO == null || !StrUtil.equals(debugVO.getDebugId(), debugId)) return;

        String language = debugVO.getLanguage();
        String code = debugVO.getCode();
        Integer timeLimit = debugVO.getTimeLimit();
        Integer memoryLimit = debugVO.getMemoryLimit();
        Integer stackLimit = debugVO.getStackLimit();
        String judgeMode = debugVO.getJudgeMode();
        List<Integer> verdict = debugVO.getVerdict();
        List<Integer> runTime = debugVO.getRunTime();
        List<Integer> runMemory = debugVO.getRunMemory();
        List<String> message = debugVO.getMessage();
        List<String> userInputList = debugVO.getUserInputList();
        List<String> userOutputList = debugVO.getUserOutputList();
        List<String> expectedOutputList = debugVO.getExpectedOutputList();

        // todo 校验代码语言是否合法
        LanguageCmdArgs languageCmdArgs = languageMap.get(language);
        if (languageCmdArgs == null) return;
        // 1. 编译代码
        String fileId = null;
        try {
            fileId = codeExecutor.compile(languageCmdArgs, code);
        } catch (CompileError e) {
            // 编译失败
            for (int i = 0; i < verdict.size(); i++) {
                verdict.set(i, JudgeVerdictConstant.COMPILE_ERROR);
                message.set(i, e.getErrorMessage());
            }
            redisUtil.setDebugVO(debugId, debugVO);
            return;
        } catch (SystemError e) {
            // 系统错误
            for (int i = 0; i < verdict.size(); i++) {
                verdict.set(i, JudgeVerdictConstant.SYSTEM_ERROR);
                message.set(i, e.getErrorMessage());
            }
            redisUtil.setDebugVO(debugId, debugVO);
            return;
        } catch (Exception e) {
            for (int i = 0; i < verdict.size(); i++) {
                verdict.set(i, JudgeVerdictConstant.SYSTEM_ERROR);
                message.set(i, "编译时出现了问题，请联系管理员");
            }
            redisUtil.setDebugVO(debugId, debugVO);
            return;
        }

        try {
            // 2. 并发运行代码，获取结果列表
            List<RunResult> debugResult = codeExecutor.runAll(languageCmdArgs,
                                                              userInputList,
                                                              timeLimit,
                                                              memoryLimit,
                                                              stackLimit,
                                                              fileId);
            // 校验大小是否相同
            if (userInputList.size() != debugResult.size()) return;
            // 3. 运行完了，更新状态到缓存
            for (int i = 0; i < debugResult.size(); i++) {
                RunResult runResult = debugResult.get(i);
                verdict.set(i, runResult.getVerdict());
                runTime.set(i, runResult.getRunTime());
                runMemory.set(i, runResult.getRunMemory());
                message.set(i, runResult.getMessage());
                userOutputList.set(i, runResult.getUserOutput());
            }
            redisUtil.setDebugVO(debugId, debugVO);
        } finally {
            // 4. 删除文件
            codeExecutor.delete(fileId);
        }
    }
}
