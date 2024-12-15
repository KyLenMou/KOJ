package fun.kylen.koj.judge;

import cn.hutool.core.util.StrUtil;
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
     *
     * @param submissionId
     */
    public void judge(Long submissionId) {
        // 设置最新正在评测的submissionId，用于获得某一提交的前方排队数量
        redisUtil.setQueueFront(submissionId);
        // 拿到代码
        Submission submission = daoUtil.getSubmissionById(submissionId);
        if (submission == null) {
            log.error("不存在submissionId为 {} 的提交", submissionId);
            throw new RuntimeException();
        }
        // 拿到代码和语言
        String language = submission.getLanguage();
        String code = submission.getCode();
        // 拿到题目
        Long problemId = submission.getProblemId();
        Problem problem = daoUtil.getProblemById(problemId);
        if (problem == null) {
            log.error("不存在problemId为 {} 的题目，submissionId：{}", problemId, submissionId);
            throw new RuntimeException();
        }
        // 拿到时空限制
        Integer timeLimit = problem.getTimeLimit();
        Integer memoryLimit = problem.getMemoryLimit();
        Integer stackLimit = problem.getStackLimit();
        // 拿到判题类型
        String judgeMode = problem.getJudgeMode();
        // 拿到题目测试用例
        List<ProblemCase> problemCaseList = daoUtil.getProblemCaseByProblemId(problemId);
        // 拿到用户在该题目提交下的测试用例（koj-service已经生成了）
        List<SubmissionCase> submissionCaseList = daoUtil.getSubmissionCaseBySubmissionId(submissionId,
                                                                                          problemId,
                                                                                          submission.getUserId());
        // 如果两个大小不一样则出错
        if (problemCaseList.size() != submissionCaseList.size()) {
            log.error("题目测试用例数量与提交测试用例数量不匹配，submissionId: {} ", submissionId);
            throw new RuntimeException();
        }
        // 拿到语言配置
        LanguageCmdArgs languageCmdArgs = languageMap.get(language);
        if (languageCmdArgs == null) {
            log.error("不存在该语言的配置：{}，submissionId：{}", language, submissionId);
            throw new RuntimeException();
        }
        // 1.编译代码
        // 设置状态为编译中
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
            // 更新各个测试点的状态
            submissionCaseList.forEach(s -> {
                s.setVerdict(JudgeVerdictConstant.COMPILE_ERROR);
                daoUtil.updateSubmissionCase(s);
            });
            return;
        } catch (SystemError e) {
            // 系统错误
            log.error("编译代码后无编译文件ID，submissionId: {} ，报错： {}", submissionId, e.getMessage());
            submission.setVerdict(JudgeVerdictConstant.SYSTEM_ERROR);
            submission.setRunMessage(e.getErrorMessage());
            daoUtil.updateSubmissionVerdict(submission);
            // 更新各个测试点的状态
            submissionCaseList.forEach(s -> {
                s.setVerdict(JudgeVerdictConstant.SYSTEM_ERROR);
                daoUtil.updateSubmissionCase(s);
            });
            throw new RuntimeException();
        } catch (Exception e) {
            log.error("编译代码出现了错误，submissionId: {} ，报错： {}", submissionId, e.getMessage());
            submission.setVerdict(JudgeVerdictConstant.SYSTEM_ERROR);
            submission.setRunMessage("编译时出现了问题，请联系管理员");
            daoUtil.updateSubmissionVerdict(submission);
            // 更新各个测试点的状态
            submissionCaseList.forEach(s -> {
                s.setVerdict(JudgeVerdictConstant.SYSTEM_ERROR);
                daoUtil.updateSubmissionCase(s);
            });
            throw new RuntimeException();
        }

        try {
            // 2. 并发运行代码，获取结果列表
            // todo 作为文件保存，设置好最大输出上限
            // 设置状态为运行中
            submission.setVerdict(JudgeVerdictConstant.RUNNING);
            daoUtil.updateSubmissionVerdict(submission);
            List<String> inputList = problemCaseList.stream().map(ProblemCase::getInput).collect(Collectors.toList());
            List<String> outputList = problemCaseList.stream().map(ProblemCase::getOutput).collect(Collectors.toList());
            List<RunResult> runResult = codeExecutor.runAll(languageCmdArgs,
                                                            inputList,
                                                            timeLimit,
                                                            memoryLimit,
                                                            stackLimit,
                                                            outputList.stream().map(String::length).collect(Collectors.toList()),
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
            int submissionVerdict = JudgeVerdictConstant.ACCEPTED;
            boolean existAccepted = false;
            int runTime = 0;
            int runMemory = 0;
            for (int i = 0; i < runResult.size(); i++) {
                RunResult result = runResult.get(i);
                SubmissionCase submissionCase = submissionCaseList.get(i);
                submissionCase.setVerdict(result.getVerdict());
                submissionCase.setTime(result.getRunTime());
                submissionCase.setMemory(result.getRunMemory());
                submissionCase.setJudgeOutput(result.getMessage());
                runTime = Math.max(runTime, result.getRunTime());
                runMemory = Math.max(runMemory, result.getRunMemory());
                // 结果比对
                if (submissionCase.getVerdict() == JudgeVerdictConstant.ACCEPTED) {
                    if (JudgeUtil.check(outputList.get(i), result.getUserOutput())) {
                        existAccepted = true;
                    } else {
                        submissionCase.setVerdict(JudgeVerdictConstant.WRONG_ANSWER);
                    }
                }
                // 设置提交状态，默认为按照评测点的第一个非Accepted状态
                if (submissionCase.getVerdict() != JudgeVerdictConstant.ACCEPTED && submissionVerdict == JudgeVerdictConstant.ACCEPTED) {
                    submissionVerdict = submissionCase.getVerdict();
                }
                // 如果没有通过但是存在AC的测试点，则设置为PAC
                if (submissionVerdict != JudgeVerdictConstant.ACCEPTED && existAccepted) {
                    submissionVerdict = JudgeVerdictConstant.PARTIALLY_ACCEPTED;
                }
                // 更新当前评测点到数据库
                daoUtil.updateSubmissionCase(submissionCase);
            }
            // 更新提交的评测状态，如果不是Accepted，则按照顺序取第一个出现的非Accepted状态
            // todo submission.setRunMessage();
            // 时空取最大值
            submission.setRunTime(runTime);
            submission.setRunMemory(runMemory);
            submission.setVerdict(submissionVerdict);
            daoUtil.updateSubmissionVerdict(submission);
        } catch (Exception e) {
            log.error("运行代码或判题出现了问题：{}", e.getMessage());
            submission.setVerdict(JudgeVerdictConstant.SYSTEM_ERROR);
            submission.setRunMessage("运行代码或判题时出现了问题，请联系管理员或稍后重试");
            daoUtil.updateSubmissionVerdict(submission);
        } finally {
            // 4. 删除文件
            codeExecutor.delete(fileId);
        }
    }

    /**
     * 编译代码，运行代码，测试数据
     *
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
            // 限制一下时空防止恶意调用
            timeLimit = Math.min(timeLimit, 10000);
            memoryLimit = Math.min(memoryLimit, 1024);
            stackLimit = Math.min(stackLimit, 1024);
            List<RunResult> debugResult = codeExecutor.runAll(languageCmdArgs,
                                                              userInputList,
                                                              timeLimit,
                                                              memoryLimit,
                                                              stackLimit,
                                                              expectedOutputList.stream().map(String::length).collect(
                                                                      Collectors.toList()),
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
                // 结果比对
                if (verdict.get(i) == JudgeVerdictConstant.ACCEPTED) {
                    if (!JudgeUtil.check(expectedOutputList.get(i), userOutputList.get(i))) {
                        verdict.set(i, JudgeVerdictConstant.WRONG_ANSWER);
                    }
                }
            }
            redisUtil.setDebugVO(debugId, debugVO);
        } catch (Exception e) {
            log.error("调试代码出现了问题：{}", e.getMessage());
            for (int i = 0; i < verdict.size(); i++) {
                verdict.set(i, JudgeVerdictConstant.SYSTEM_ERROR);
                message.set(i, "运行时出现了问题，请联系管理员");
            }
            redisUtil.setDebugVO(debugId, debugVO);
        } finally {
            // 4. 删除文件
            codeExecutor.delete(fileId);
        }
    }
}
