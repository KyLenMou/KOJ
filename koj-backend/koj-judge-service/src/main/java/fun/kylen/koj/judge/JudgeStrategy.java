package fun.kylen.koj.judge;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONArray;
import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.model.LanguageCmdArgs;
import fun.kylen.koj.sandbox.SandboxRunner;
import fun.kylen.koj.utils.JudgeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class JudgeStrategy {
    private static final Logger log = LoggerFactory.getLogger(JudgeStrategy.class);
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
                      List<String> inputList,
                      List<String> outputList,
                      Integer timeLimit,
                      Integer memoryLimit,
                      Integer stackLimit,
                      String judgeMode) {
        List<String> args = JudgeUtil.getArgs(languageCmdArgs.getRunCommand());
        for (int i = 0; i < inputList.size(); i++) {
            // 设置running on test (i+1)
            submissionEntityService.lambdaUpdate()
                    .eq(Submission::getId, submitId)
                    .set(Submission::getVerdict, i + 1);
            String input = inputList.get(i);
            String answer = outputList.get(i);

            JSONArray result = sandboxRunner.run(input,
                                                 args,
                                                 languageCmdArgs.getRunEnvs(),
                                                 languageCmdArgs.getMaxCpuTime(),
                                                 languageCmdArgs.getMaxMemory(),
                                                 stackLimit,
                                                 32L * 1024 * 1024,
                                                 languageCmdArgs.getExeName(),
                                                 fileId);

            String output = (String) result.getByPath("0.files.stdout");
            if (!check(answer, output)) {
                log.error("Wrong Answer On Test {}","" + (i + 1));
                break;
            }
        }
    }

    private boolean check(String answer, String output) {
        // todo answer默认去掉空格和多于换行符
        String ans = DigestUtil.md5Hex(removeBlankAndEOL(answer));
        String out = DigestUtil.md5Hex(removeBlankAndEOL(output));
        return StrUtil.equals(ans,out);
    }
    private String removeBlankAndEOL(String value) {
        if (value == null) return null;
        return EOL_PATTERN.matcher(StrUtil.trimEnd(value)).replaceAll("");
    }
}
