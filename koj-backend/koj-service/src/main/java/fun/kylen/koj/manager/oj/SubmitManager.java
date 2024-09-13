package fun.kylen.koj.manager.oj;

import cn.hutool.core.bean.BeanUtil;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.dao.UserInfoEntityService;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.domain.UserInfo;
import fun.kylen.koj.model.dto.SubmissionDTO;
import fun.kylen.koj.mq.JudgeMessageDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

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

    public void submit(SubmissionDTO submissionDTO) {
        Submission submission = new Submission();
        BeanUtil.copyProperties(submissionDTO, submission);
        submission.setSubmitTime(new Date());
        UserInfo user = userInfoEntityService.getById(submission.getUserId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        // todo validator校验 (是否存在题目，题目是否可见等)
        // todo 限制5秒内提交次数
        submission.setUsername(user.getUsername());
        submission.setVerdict(0);
        submission.setVersion(0);
        boolean save = submissionEntityService.save(submission);
        if (!save) {
            throw new BusinessException(ResultEnum.FAIL, "提交失败，请稍后重试");
        }
        // 发送判题任务到消息队列
        judgeMessageDispatcher.dispatch(submission.getId());
    }
}