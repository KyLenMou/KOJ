package fun.kylen.koj.manager.oj;

import cn.hutool.core.bean.BeanUtil;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.model.dto.SubmissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: KyLen
 * @Date: 2024/9/9 11:45
 * @Description:
 */
@Component
public class SubmissionManager {
    @Autowired
    private SubmissionEntityService submissionEntityService;

    public void submit(SubmissionDTO submissionDTO) {
        Submission submission = new Submission();
        BeanUtil.copyProperties(submissionDTO, submission);
        submissionEntityService.save(submission);
    }
}
