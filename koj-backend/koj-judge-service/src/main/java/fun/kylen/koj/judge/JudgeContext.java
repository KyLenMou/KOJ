package fun.kylen.koj.judge;

import fun.kylen.koj.constant.JudgeConstant;
import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.ProblemCase;
import fun.kylen.koj.domain.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    // public void judge(Long submitId) {
    //     judgeStrategy.judge(language, code, inputList, outputList, timeLimit, memoryLimit, stackLimit);
    // }
}
