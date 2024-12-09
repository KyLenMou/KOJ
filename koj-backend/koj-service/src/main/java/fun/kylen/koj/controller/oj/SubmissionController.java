package fun.kylen.koj.controller.oj;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.R;
import fun.kylen.koj.model.oj.vo.SubmissionDetailVO;
import fun.kylen.koj.model.oj.vo.SubmissionListVO;
import fun.kylen.koj.model.oj.vo.SubmissionVerdictVO;
import fun.kylen.koj.service.oj.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: KyLen
 * @Date: 2024/9/9 11:42
 * @Description:
 */
@RestController
@RequestMapping("/submission")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;
    @GetMapping
    public R<Page<SubmissionListVO>> listSubmissionByPage(@RequestParam(value = "current",required = true) Long current,
                                                          @RequestParam(value = "size",required = true) Long size,
                                                          @RequestParam(value = "problemId",required = false) Long problemId,
                                                          @RequestParam(value = "problemDisplayId",required = false) String problemDisplayId,
                                                          @RequestParam(value = "userId",required = false) String userId,
                                                          @RequestParam(value = "username",required = false) String username,
                                                          @RequestParam(value = "language",required = false) String language) {
        return R.ok(submissionService.listSubmissionByPage(current, size, problemId, problemDisplayId, userId, username, language));
    }

    @GetMapping("/detail")
    public R<SubmissionDetailVO> getSubmissionDetail(@RequestParam(value = "submissionId") Long submissionId) {
        return R.ok(submissionService.getSubmissionDetail(submissionId));
    }

    @GetMapping("/verdict") // todo 列表
    public R<SubmissionVerdictVO> getSubmissionVerdict(@RequestParam(value = "submissionId") Long submissionId) {
        return R.ok(submissionService.getSubmissionVerdict(submissionId));
    }
}
