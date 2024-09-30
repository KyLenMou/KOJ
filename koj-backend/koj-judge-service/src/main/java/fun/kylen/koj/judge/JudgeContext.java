package fun.kylen.koj.judge;

import fun.kylen.koj.constant.JudgeStatusConstant;
import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.domain.ProblemCase;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.model.LanguageCmdArgs;
import fun.kylen.koj.sandbox.CodeCompiler;
import fun.kylen.koj.sandbox.CodeDeleter;
import fun.kylen.koj.sandbox.SandboxRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: KyLen
 * @Date: 2024/9/13 12:12
 * @Description:
 */
@Component
public class JudgeContext {
    @Autowired
    private ProblemEntityService problemEntityService;
    @Autowired
    private ProblemCaseEntityService problemCaseEntityService;
    @Autowired
    private SubmissionEntityService submissionEntityService;
    @Autowired
    private JudgeStrategy judgeStrategy;
    @Autowired
    private CodeCompiler codeCompiler;
    @Autowired
    private SandboxRunner sandboxRunner;
    @Autowired
    private CodeDeleter codeDeleter;
    @Resource
    private Map<String, LanguageCmdArgs> languageMap;

    /**
     * 编译代码，对每个评测点进行运行与答案比对
     * @param submitId
     * @param language
     * @param code
     * @param judgeMode
     * @param timeLimit
     * @param memoryLimit
     * @param stackLimit
     * @param problemCaseList
     */
    public void judge(Long submitId,
                      String language,
                      String code,
                      String judgeMode,
                      Integer timeLimit,
                      Integer memoryLimit,
                      Integer stackLimit,
                      List<ProblemCase> problemCaseList) {

        // 准备编译代码，修改状态
        submissionEntityService.lambdaUpdate()
                .set(Submission::getVerdict, JudgeStatusConstant.COMPILING)
                .eq(Submission::getId, submitId)
                .update();

        // 根据编程语言，编译代码，获取编译后的相关信息
        String fileId = null;
        // todo 检查语言是否存在
        LanguageCmdArgs languageCmdArgs = languageMap.get(language);
        try {
            // 拿到go-judge编译后的文件ID
            fileId = codeCompiler.compile(languageCmdArgs, code);
            // todo 内测阶段，新增一个表来记录fileId和对应的提交记录，防止未删除的文件过多导致内存泄露
            judgeStrategy.judge(submitId,
                                fileId,
                                languageCmdArgs,
                                timeLimit,
                                memoryLimit,
                                stackLimit,
                                judgeMode,
                                problemCaseList);
        } catch (Exception e) {
            // todo 编译错误处理
        } finally {
            // 删除go-judge内的文件
            if (fileId != null) {
                codeDeleter.delete(fileId);
            }
        }

    }
}
