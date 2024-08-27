package fun.kylen.koj.controller.admin;

import fun.kylen.koj.common.R;
import fun.kylen.koj.model.dto.ProblemAddDTO;
import fun.kylen.koj.service.admin.AdminProblemService;
import fun.kylen.koj.service.oj.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: KyLen
 * @Date: 2024/8/27 22:28
 * @Description:
 */
@RestController
@RequestMapping("/admin/problem")
@Validated
public class AdminProblemController {
    @Autowired
    private AdminProblemService adminProblemService;
    @PostMapping
    public R<Void> addProblem(@Validated @RequestBody ProblemAddDTO problemAddDTO) {
        adminProblemService.addProblem(problemAddDTO);
        return R.ok();
    }
}
