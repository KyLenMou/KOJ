package fun.kylen.koj.manager.oj;

import cn.hutool.core.bean.BeanUtil;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.dao.UserInfoEntityService;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.domain.UserInfo;
import fun.kylen.koj.model.dto.SubmissionDTO;
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

    public void submit(SubmissionDTO submissionDTO) {
        Submission submission = new Submission();
        BeanUtil.copyProperties(submissionDTO, submission);
        submission.setSubmitTime(new Date());
        UserInfo user = userInfoEntityService.getById(submission.getUserId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        submission.setUsername(user.getUsername());
        submissionEntityService.save(submission);
    }
}