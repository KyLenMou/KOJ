package fun.kylen.koj.judge;

import fun.kylen.koj.constant.JudgeConstant;
import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.domain.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/9/14 16:16
 * @Description:
 */
@Component
public class JudgeStrategy {
    @Autowired
    private ProblemEntityService problemEntityService;
    @Autowired
    private ProblemCaseEntityService problemCaseEntityService;
    @Autowired
    private SubmissionEntityService submissionEntityService;

    public void judge(String language,
                      String code,
                      List<String> inputList,
                      List<String> outputList,
                      Integer timeLimit,
                      Integer memoryLimit,
                      Integer stackLimit) {
        // 准备编译代码，修改状态
        // submissionEntityService.lambdaUpdate()
        //         .set(Submission::getVerdict, JudgeConstant.COMPILING)
        //         .eq(Submission::getId, submitId).update();
        // 编译代码，获取编译后的文件id
        // 根据编程语言，进行编译，设置提交状态为编译中
        // 拿到go-judge编译后的文件ID
        // 使用go-judge运行程序，运行n次，每次输入对应的input，修改提交状态为运行中
        // 删除go-judge内的文件
    }
}
