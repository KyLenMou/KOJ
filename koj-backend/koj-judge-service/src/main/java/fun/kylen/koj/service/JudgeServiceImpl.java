package fun.kylen.koj.service;

import fun.kylen.koj.constant.JudgeConstant;
import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.SubmissionCaseEntityService;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.ProblemCase;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.domain.SubmissionCase;
import fun.kylen.koj.judge.JudgeContext;
import fun.kylen.koj.model.ProblemAndSubmissionCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/9/12 22:21
 * @Description:
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    @Autowired
    private ProblemEntityService problemEntityService;
    @Autowired
    private ProblemCaseEntityService problemCaseEntityService;
    @Autowired
    private SubmissionEntityService submissionEntityService;
    @Autowired
    private JudgeContext judgeContext;
    @Autowired
    private SubmissionCaseEntityService submissionCaseEntityService;

    /**
     * 只获取提交详情并验证合法性，传入给judgeContext进行判题
     *
     * @param submitId
     */
    @Override
    public void judge(Long submitId) {
        // todo validator
        // 拿到代码
        Submission submission = submissionEntityService.getById(submitId);
        if (submission == null) {
            throw new RuntimeException("不存在submitId为 " + submitId + " 的提交");
        }
        // 拿到代码和语言
        String language = submission.getLanguage();
        String code = submission.getCode();
        // 拿到题目
        Long problemId = submission.getProblemId();
        Problem problem = problemEntityService.lambdaQuery().eq(Problem::getId, problemId).one();
        if (problem == null) {
            throw new RuntimeException("不存在problemId为 " + problemId + " 的题目");
        }
        // 拿到题目测试用例
        List<ProblemCase> problemCaseList = problemCaseEntityService.lambdaQuery()
                .eq(ProblemCase::getProblemId, problemId)
                .list();
        // 拿到用户在该题目提交下的测试用例
        List<SubmissionCase> submissionCaseList = submissionCaseEntityService.lambdaQuery()
                .eq(SubmissionCase::getSubmissionId, submitId)
                .eq(SubmissionCase::getProblemId, problemId)
                .eq(SubmissionCase::getUserId, submission.getUserId())
                .list();
        // 如果两个大小不一样则出错
        if (problemCaseList.size() != submissionCaseList.size()) {
            throw new RuntimeException("题目测试用例数量出现错误，请联系管理员");
        }
        // 构建题目测试用例和用户在该题目提交下的测试用例组合，按顺序组合，todo 确保顺序一致
        List<ProblemAndSubmissionCase> problemAndSubmissionCaseList = new ArrayList<>();
        for (int i = 0; i < problemCaseList.size(); i++) {
            ProblemAndSubmissionCase problemAndSubmissionCase = new ProblemAndSubmissionCase();
            problemAndSubmissionCase.setProblemId(problemId);
            problemAndSubmissionCase.setProblemCaseId(problemCaseList.get(i).getId());
            problemAndSubmissionCase.setSubmissionCaseId(submissionCaseList.get(i).getId());
            problemAndSubmissionCase.setInput(problemCaseList.get(i).getInput());
            problemAndSubmissionCase.setOutput(problemCaseList.get(i).getOutput());
            problemAndSubmissionCaseList.add(problemAndSubmissionCase);
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
        judgeContext.judge(submitId,
                           language,
                           code,
                           judgeMode,
                           timeLimit,
                           memoryLimit,
                           stackLimit,
                           problemAndSubmissionCaseList);
    }
}
