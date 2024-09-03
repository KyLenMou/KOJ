package fun.kylen.koj.controller.admin;

import fun.kylen.koj.common.R;
import fun.kylen.koj.model.dto.ProblemDTO;
import fun.kylen.koj.service.admin.AdminProblemService;
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
    public R<Void> saveProblem(@Validated @RequestBody ProblemDTO problemDTO) {
        adminProblemService.saveProblem(problemDTO);
        return R.ok();
    }
}
