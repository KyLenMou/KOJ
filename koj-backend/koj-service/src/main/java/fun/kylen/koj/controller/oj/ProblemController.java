package fun.kylen.koj.controller.oj;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.R;
import fun.kylen.koj.model.oj.vo.ProblemDetailVO;
import fun.kylen.koj.model.oj.vo.ProblemInfoCardVO;
import fun.kylen.koj.model.oj.vo.ProblemsetVO;
import fun.kylen.koj.service.oj.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public R<Page<ProblemsetVO>> listProblemsetVOByPage(@RequestParam("current") Integer current,
                                                        @RequestParam("pageSize") Integer pageSize,
                                                        @RequestParam(value = "searchText", required = false) String searchText,
                                                        @RequestParam(value = "judgeMode", required = false) String judgeMode,
                                                        @RequestParam(value = "tagId", required = false) List<Long> tagId,
                                                        @RequestParam(value = "difficulty", required = false) List<Integer> difficulty) {
        return R.ok(problemService.listProblemsetVOByPage(current, pageSize, searchText));
    }

    @GetMapping("/es")
    public R<Page<ProblemsetVO>> listProblemsetVOFromEs(@RequestParam("current") Integer current,
                                                        @RequestParam("pageSize") Integer pageSize,
                                                        @RequestParam("searchText") String searchText) {
        return R.ok(problemService.listProblemsetVOFromEs(current, pageSize, searchText));
    }

    @GetMapping("/detail")
    public R<ProblemDetailVO> getProblemDetailVO(@RequestParam("problemDisplayId") String problemDisplayId) {
        return R.ok(problemService.getProblemDetailVO(problemDisplayId));
    }

    @GetMapping("/info-card")
    public R<ProblemInfoCardVO> getProblemInfoCard(@RequestParam("problemId") String problemId) {
        return R.ok(problemService.getProblemInfoCard(problemId));
    }
}
