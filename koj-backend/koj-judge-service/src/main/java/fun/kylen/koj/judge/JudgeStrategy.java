package fun.kylen.koj.judge;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import fun.kylen.koj.constant.JudgeStatusConstant;
import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.domain.ProblemCase;
import fun.kylen.koj.model.LanguageCmdArgs;
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

    private final static Pattern EOL_PATTERN = Pattern.compile("[^\\S\\n]+(?=\\n)");

    public void judge(Long submitId,
                      String fileId,
                      LanguageCmdArgs languageCmdArgs,
                      Integer timeLimit,
                      Integer memoryLimit,
                      Integer stackLimit,
                      String judgeMode,
                      List<ProblemCase> problemCaseList) {
        // 获取gojudge调用参数
        List<String> args = JudgeUtil.getArgs(languageCmdArgs.getRunCommand());

        // 遇错止评 todo 全部评测
        for (ProblemCase problemCase : problemCaseList) {
            String input = problemCase.getInput();
            // 答案
            String answer = problemCase.getOutput();
            // 获取评测结果 todo 运行时间需要+200ms
            Long time = timeLimit + 200L;
            Long memory = (long) memoryLimit;
            JSONArray result = sandboxRunner.run(input,
                                                 args,
                                                 languageCmdArgs.getRunEnvs(),
                                                 time,
                                                 memory,
                                                 stackLimit,
                                                 32L * 1024 * 1024,
                                                 languageCmdArgs.getExeName(),
                                                 fileId);

            // 获取result的评测状态，先判断是否出现error，再进行答案比对
            JSONObject resultJson = (JSONObject) result.get(0);
            Integer status = resultJson.getInt("status");

            // 通过，继续下一个
            if (status == JudgeStatusConstant.ACCEPTED) {
                continue;
            }
            switch (status) {
                case JudgeStatusConstant.TIME_LIMIT_EXCEEDED:
                    // 处理超时的情况

                    break;
                case JudgeStatusConstant.MEMORY_LIMIT_EXCEEDED:
                    // 处理内存超限的情况
                    break;
                case JudgeStatusConstant.STACK_LIMIT_EXCEEDED:
                    // 处理栈溢出的情况
                    break;
                case JudgeStatusConstant.RUNTIME_ERROR:
                    // 处理运行时错误的情况
                    break;
                case JudgeStatusConstant.OUTPUT_LIMIT_EXCEEDED:
                    // 处理输出超限的情况
                    break;
                default:
                    // 其他情况
                    break;
            }

            // 用户输出
            String output = (String) result.getByPath("0.files.stdout");
            if (!check(answer, output)) {
                break;
            }
        }
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
