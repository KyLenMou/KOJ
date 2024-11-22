package fun.kylen.koj.controller.oj;

import cn.dev33.satoken.annotation.SaCheckLogin;
import fun.kylen.koj.common.R;
import fun.kylen.koj.model.oj.dto.SubmissionDTO;
import fun.kylen.koj.service.oj.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: KyLen
 * @Date: 2024/9/9 11:42
 * @Description:
 */
@RestController
@RequestMapping("/submit")
@SaCheckLogin
@Validated
public class SubmitController {
    @Autowired
    private SubmitService submitService;

    @PostMapping
    public R<Void> submit(@Validated @RequestBody SubmissionDTO submission) {
        submitService.submit(submission);
        return R.ok();
    }
}
