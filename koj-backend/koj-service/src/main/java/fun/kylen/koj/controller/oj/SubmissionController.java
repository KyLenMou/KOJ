package fun.kylen.koj.controller.oj;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.R;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.model.dto.SubmissionDTO;
import fun.kylen.koj.service.oj.SubmissionService;
import fun.kylen.koj.service.oj.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: KyLen
 * @Date: 2024/9/9 11:42
 * @Description:
 */
@RestController
@RequestMapping("/submission")
public class SubmissionController {
    // @Autowired
    // private SubmissionService submissionService;
    //
    // @PostMapping
    // public R<Page<Submission>> listSubmissionByPage(SubmissionDTO submissionDTO) {
    //     return R.ok(submissionService.listSubmissionByPage(submissionDTO));
    // }

}
