package fun.kylen.koj.service.impl;

import fun.kylen.koj.constant.JudgeConstant;
import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.ProblemCase;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.judge.JudgeContext;
import fun.kylen.koj.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        // 拿到所有的输入输出测试用例
        List<ProblemCase> problemCaseList = problemCaseEntityService.lambdaQuery().eq(ProblemCase::getProblemId,
                                                                                      problemId).list();
        List<String> inputList = problemCaseList.stream().map(ProblemCase::getInput).collect(Collectors.toList());
        List<String> outputList = problemCaseList.stream().map(ProblemCase::getOutput).collect(Collectors.toList());
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
                           inputList,
                           outputList,
                           timeLimit,
                           memoryLimit,
                           stackLimit);
    }
}
