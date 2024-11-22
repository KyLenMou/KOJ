package fun.kylen.koj.manager.oj;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.constant.JudgeStatusConstant;
import fun.kylen.koj.constant.StpConstant;
import fun.kylen.koj.dao.*;
import fun.kylen.koj.domain.*;
import fun.kylen.koj.model.oj.dto.SubmissionDTO;
import fun.kylen.koj.model.oj.vo.UserInfoVO;
import fun.kylen.koj.mq.JudgeMessageDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/9/9 11:45
 * @Description:
 */
@Component
public class SubmitManager {
    @Autowired
    private SubmissionEntityService submissionEntityService;
    @Autowired
    private UserInfoEntityService userInfoEntityService;
    @Autowired
    private JudgeMessageDispatcher judgeMessageDispatcher;
    @Autowired
    private SubmissionCaseEntityService submissionCaseEntityService;
    @Autowired
    private ProblemCaseEntityService problemCaseEntityService;
    @Autowired
    private ProblemEntityService problemEntityService;

    public void submit(SubmissionDTO submissionDTO) {
        UserInfoVO currentUser = (UserInfoVO) StpUtil.getSession().get(StpConstant.CURRENT_USER);
        // controller层已经做过校验
        if (currentUser == null) {
            throw new BusinessException(ResultEnum.FAIL, "用户不存在");
        }
        Submission submission = new Submission();
        BeanUtil.copyProperties(submissionDTO, submission);

        // 设置展示id
        Long problemId = submission.getProblemId();
        Problem problem = problemEntityService.lambdaQuery()
                .eq(Problem::getId, problemId)
                .one();
        submission.setProblemDisplayId(problem.getDisplayId());

        submission.setSubmitTime(new Date());
        submission.setUserId(currentUser.getId());
        // todo validator校验 (是否存在题目，题目是否可见等)
        // todo 校验代码语言是否合法
        // todo 限制5秒内提交次数
        // submission.setUsername(user.getUsername()); todo 不需要设置username，前端展示时通过查询展示username
        // 设置 in queue
        submission.setVerdict(JudgeStatusConstant.IN_QUEUE);
        // 设置乐观锁
        submission.setVersion(0);
        // 保存提交
        boolean save = submissionEntityService.save(submission);
        if (!save) {
            throw new BusinessException(ResultEnum.FAIL, "提交失败，请稍后重试");
        }
        // 为每个提交生成对应的所有测试用例评测情况
        String userId = submission.getUserId();
        Long submissionId = submission.getId();
        // 查询该题目的测试用例
        List<ProblemCase> problemCaseList = problemCaseEntityService.lambdaQuery()
                .eq(ProblemCase::getProblemId, problemId)
                .list();
        // 构造 用户 题目 提交 测试用例
        List<SubmissionCase> submissionCaseList = new ArrayList<>();
        problemCaseList.forEach(p -> {
            SubmissionCase submissionCase = new SubmissionCase();
            submissionCase.setSubmissionId(submissionId);
            submissionCase.setUserId(userId);
            submissionCase.setProblemId(problemId);
            // 其中一个测试用例
            submissionCase.setProblemCaseId(p.getId());
            // 默认未评测
            submissionCase.setVerdict(0);
            submissionCaseList.add(submissionCase);
        });
        // 将该用户对于该题目的提交的所有测试点添加到数据库
        submissionCaseEntityService.saveBatch(submissionCaseList);
        try {
            // 发送判题任务到消息队列
            judgeMessageDispatcher.dispatch(submissionId);
        } catch (Exception e) {
            // 发送到消息队列失败，用户需要重新对该提交进行判题 todo 新增重新判题接口，仅需把submitId发送到消息队列中即可
            submissionEntityService.lambdaUpdate()
                    .set(Submission::getVerdict, JudgeStatusConstant.SUBMIT_FAIL)
                    .eq(Submission::getId, submissionId)
                    .update();
        }
    }
}