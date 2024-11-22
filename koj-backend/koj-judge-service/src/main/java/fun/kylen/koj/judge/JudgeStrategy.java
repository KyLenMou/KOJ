package fun.kylen.koj.judge;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import fun.kylen.koj.constant.JudgeStatusConstant;
import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.SubmissionCaseEntityService;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.domain.SubmissionCase;
import fun.kylen.koj.model.LanguageCmdArgs;
import fun.kylen.koj.model.ProblemAndSubmissionCase;
import fun.kylen.koj.sandbox.SandboxRunner;
import fun.kylen.koj.utils.JudgeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author: KyLen
 * @Date: 2024/9/14 16:16
 * @Description:
 */
@Component
@Slf4j
public class JudgeStrategy {
    @Autowired
    private ProblemEntityService problemEntityService;
    @Autowired
    private ProblemCaseEntityService problemCaseEntityService;
    @Autowired
    private SubmissionEntityService submissionEntityService;
    @Autowired
    private SandboxRunner sandboxRunner;
    @Autowired
    private SubmissionCaseEntityService submissionCaseEntityService;

    private final static Pattern EOL_PATTERN = Pattern.compile("[^\\S\\n]+(?=\\n)");

    public void judge(Long submitId,
                      String fileId,
                      LanguageCmdArgs languageCmdArgs,
                      Integer timeLimit,
                      Integer memoryLimit,
                      Integer stackLimit,
                      String judgeMode,
                      List<ProblemAndSubmissionCase> problemAndSubmissionCases) {
        // 获取gojudge调用参数
        List<String> args = JudgeUtil.getArgs(languageCmdArgs.getRunCommand());
        List<String> runEnvs = languageCmdArgs.getRunEnvs();

        // 记录所有评测点时空最大值
        long maxTime = 0L;
        long maxMemory = 0L;
        // 遇错止评 todo 全部评测
        for (int i = 0; i < problemAndSubmissionCases.size(); i++) {
            int index = i + 1;
            ProblemAndSubmissionCase p = problemAndSubmissionCases.get(i);
            String input = p.getInput();
            // 答案
            String answer = p.getOutput();
            // 获取评测结果 ms mb
            Long time = (long) timeLimit + 200;
            Long memory = (long) memoryLimit;
            JSONArray result = sandboxRunner.run(input,
                                                 args,
                                                 runEnvs,
                                                 time,
                                                 memory,
                                                 stackLimit,
                                                 32L * 1024 * 1024,
                                                 languageCmdArgs.getExeName(),
                                                 fileId);

            // 获取result的评测状态，先判断是否出现error，再进行答案比对
            JSONObject resultJson = (JSONObject) result.get(0);
            Integer status = resultJson.getInt("status");
            // ms
            Integer actualTime = resultJson.getInt("time");
            // kb
            Integer actualMemory = resultJson.getInt("memory");

            maxTime = Math.max(maxTime, actualTime);
            maxMemory = Math.max(maxMemory, actualMemory);
            // 先设置评测点时空状态
            submissionCaseEntityService.lambdaUpdate()
                    .eq(SubmissionCase::getId, p.getSubmissionCaseId())
                    .set(SubmissionCase::getTime, actualTime)
                    .set(SubmissionCase::getMemory, actualMemory)
                    .update();
            // 如果出现了非WA错误
            if (status != JudgeStatusConstant.ACCEPTED) {
                // 设置当前评测点的错误状态
                submissionCaseEntityService.lambdaUpdate()
                        .eq(SubmissionCase::getId, p.getSubmissionCaseId())
                        .set(SubmissionCase::getVerdict, status)
                        .update();
                // 设置提交错误状态
                submissionEntityService.lambdaUpdate()
                        .eq(Submission::getId, submitId)
                        .set(Submission::getVerdict, status)
                        .update();
                // 遇错止评，结束
                break;
            }
            // 没有出现非WA错误，仍然判断时空是否超限
            if (actualTime >= timeLimit) {
                // 评测点
                submissionCaseEntityService.lambdaUpdate()
                        .eq(SubmissionCase::getId, p.getSubmissionCaseId())
                        .set(SubmissionCase::getVerdict, JudgeStatusConstant.TIME_LIMIT_EXCEEDED)
                        .update();
                // 提交
                submissionEntityService.lambdaUpdate()
                        .eq(Submission::getId, submitId)
                        .set(Submission::getVerdict, JudgeStatusConstant.TIME_LIMIT_EXCEEDED)
                        .set(Submission::getRunTime, actualTime)
                        .set(Submission::getRunMemory, actualMemory)
                        .update();
                break;
            }
            // kb mb
            if (actualMemory >= memoryLimit * 1024L) {
                // 评测点
                submissionCaseEntityService.lambdaUpdate()
                        .eq(SubmissionCase::getId, p.getSubmissionCaseId())
                        .set(SubmissionCase::getVerdict, JudgeStatusConstant.MEMORY_LIMIT_EXCEEDED)
                        .update();
                // 提交
                submissionEntityService.lambdaUpdate()
                        .eq(Submission::getId, submitId)
                        .set(Submission::getVerdict, JudgeStatusConstant.MEMORY_LIMIT_EXCEEDED)
                        .set(Submission::getRunTime, actualTime)
                        .set(Submission::getRunMemory, actualMemory)
                        .update();
                break;
            }
            // 获取用户输出
            String output = (String) result.getByPath("0.files.stdout");
            // 比对答案
            if (!check(answer, output)) {
                // 出现错误，设置该评测点为Wrong Answer
                submissionCaseEntityService.lambdaUpdate()
                        .eq(SubmissionCase::getId, p.getSubmissionCaseId())
                        .set(SubmissionCase::getVerdict, JudgeStatusConstant.WRONG_ANSWER)
                        .update();
                // 设置提交错误状态 todo 是否是oi赛制 是否是pac
                submissionEntityService.lambdaUpdate()
                        .eq(Submission::getId, submitId)
                        .set(Submission::getVerdict, JudgeStatusConstant.WRONG_ANSWER)
                        .update();
                // 遇错止评，结束
                break;
            }
            // 该测试用例正确
            submissionCaseEntityService.lambdaUpdate()
                    .eq(SubmissionCase::getId, p.getSubmissionCaseId())
                    .set(SubmissionCase::getVerdict, JudgeStatusConstant.ACCEPTED)
                    .update();
        }

        // 提交通过
        submissionEntityService.lambdaUpdate()
                .eq(Submission::getId, submitId)
                .set(Submission::getVerdict, JudgeStatusConstant.ACCEPTED)
                .set(Submission::getRunTime, maxTime)
                .set(Submission::getRunMemory, maxMemory)
                .update();
    }

    /**
     * 使用md5进行答案比对
     *
     * @param answer
     * @param output
     * @return
     */
    private boolean check(String answer, String output) {
        // todo answer默认去掉空格和多于换行符
        String ans = DigestUtil.md5Hex(removeBlankAndEOL(answer));
        String out = DigestUtil.md5Hex(removeBlankAndEOL(output));
        return StrUtil.equals(ans, out);
    }

    /**
     * 去除value中的末尾空白符和换行符前的空格
     *
     * @param value
     * @return
     */
    private String removeBlankAndEOL(String value) {
        if (value == null) return null;
        return EOL_PATTERN.matcher(StrUtil.trimEnd(value)).replaceAll("");
    }
}
