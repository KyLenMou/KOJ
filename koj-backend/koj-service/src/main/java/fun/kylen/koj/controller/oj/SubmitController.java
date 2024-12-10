package fun.kylen.koj.controller.oj;

import cn.dev33.satoken.annotation.SaCheckLogin;
import fun.kylen.koj.common.R;
import fun.kylen.koj.model.oj.dto.DebugDTO;
import fun.kylen.koj.model.oj.dto.SubmissionDTO;
import fun.kylen.koj.service.oj.SubmitService;
import fun.kylen.koj.vo.DebugVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: KyLen
 * @Date: 2024/9/9 11:42
 * @Description:
 */
@RestController
@SaCheckLogin
@Validated
public class SubmitController {
    @Autowired
    private SubmitService submitService;

    /**
     * 对于出现系统错误的提交，用户可以重测
     * @param submissionId
     * @return
     */
    @PostMapping
    public R<Void> reJudge(@RequestParam(value = "submissionId") Long submissionId) {
        return R.okWithMessage("重测成功");
    }

    @PostMapping("/submit")
    public R<String> submit(@Validated @RequestBody SubmissionDTO submission) {
        return R.ok(submitService.submit(submission),"提交成功");
    }

    @PostMapping("/debug")
    public R<String> debug(@Validated @RequestBody DebugDTO debugDTO) {
        return R.ok(submitService.debug(debugDTO),"测试成功");
    }

    @GetMapping("/debug")
    public R<DebugVO> getDebugResult(@RequestParam(value = "debugId") String debugId) {
        return R.ok(submitService.getDebugResult(debugId));
    }
}
