package fun.kylen.koj.utils;

import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.SubmissionCaseEntityService;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.ProblemCase;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.domain.SubmissionCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/11/30 15:17
 * @Description:
 */
@Component
public class CommonDaoUtil {
    @Autowired
    private SubmissionEntityService submissionEntityService;
    @Autowired
    private ProblemEntityService problemEntityService;
    @Autowired
    private ProblemCaseEntityService problemCaseEntityService;
    @Autowired
    private SubmissionCaseEntityService submissionCaseEntityService;

    public Submission getSubmissionById(Long submitId) {
        return submissionEntityService.getById(submitId);
    }

    public Problem getProblemById(Long problemId) {
        return problemEntityService.lambdaQuery().eq(Problem::getId, problemId).one();
    }

    public List<ProblemCase> getProblemCaseByProblemId(Long problemId) {
        return problemCaseEntityService.lambdaQuery()
                .eq(ProblemCase::getProblemId, problemId)
                .list();
    }

    public List<SubmissionCase> getSubmissionCaseBySubmissionId(Long submitId, Long problemId, String userId) {
        return submissionCaseEntityService.lambdaQuery()
                .eq(SubmissionCase::getSubmissionId, submitId)
                .eq(SubmissionCase::getProblemId, problemId)
                .eq(SubmissionCase::getUserId, userId)
                .list();
    }

    public void updateSubmissionCase(SubmissionCase submissionCase) {
        submissionCaseEntityService.updateById(submissionCase);
    }

    public void updateSubmissionVerdict(Submission submission) {
        submissionEntityService.updateById(submission);
    }
}
