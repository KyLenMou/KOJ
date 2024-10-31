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
    @GetMapping
    public R<Page<ProblemsetVO>> listProblemsetVOFromEs(@RequestParam("current") Integer current,
                                                        @RequestParam("pageSize") Integer pageSize,
                                                        @RequestParam("searchText") String searchText) {
        return R.ok(problemService.listProblemsetVOFromEs(current, pageSize, searchText));
    }

    @GetMapping("/detail")
    public R<ProblemInfoVO> getProblemDetail(@RequestParam("problemDisplayId") String problemDisplayId) {
        return R.ok(problemService.getProblemDetail(problemDisplayId));
    }



}
