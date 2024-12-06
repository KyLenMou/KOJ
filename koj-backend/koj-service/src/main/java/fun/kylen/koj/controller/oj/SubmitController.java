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

    @PostMapping("/submit")
    public R<Void> submit(@Validated @RequestBody SubmissionDTO submission) {
        submitService.submit(submission);
        return R.okWithMessage("提交成功");
    }

    @PostMapping("/debug")
    public R<String> debug(@Validated @RequestBody DebugDTO debugDTO) {
        return R.ok(submitService.debug(debugDTO),"提交成功");
    }

    @GetMapping("/debug")
    public R<DebugVO> getDebugResult(@RequestParam(value = "debugId") String debugId) {
        return R.ok(submitService.getDebugResult(debugId));
    }
}
