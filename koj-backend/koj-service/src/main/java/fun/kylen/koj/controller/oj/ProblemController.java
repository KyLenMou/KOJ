package fun.kylen.koj.controller.oj;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.R;
import fun.kylen.koj.model.dto.PageDTO;
import fun.kylen.koj.model.vo.ProblemInfoVO;
import fun.kylen.koj.model.vo.ProblemsetVO;
import fun.kylen.koj.service.oj.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: KyLen
 * @Date: 2024/8/27 22:28
 * @Description:
 */
@RestController
@RequestMapping("/problem")
@Validated
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    @PostMapping("/list")
    public R<Page<ProblemsetVO>> listProblemsetVOByPage(@RequestBody PageDTO pageDTO) {
        return R.ok(problemService.listProblemsetVOByPage(pageDTO));
    }

    @GetMapping
    public R<ProblemInfoVO> getProblemDetail(@RequestParam("problemId") String problemId) {
        return R.ok(problemService.getProblemDetail(problemId));
    }
}
