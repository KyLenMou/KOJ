package fun.kylen.koj.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.R;
import fun.kylen.koj.model.oj.dto.ProblemDTO;
import fun.kylen.koj.model.admin.vo.AdminProblem;
import fun.kylen.koj.service.admin.AdminProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: KyLen
 * @Date: 2024/8/27 22:28
 * @Description:
 */
@RestController
@RequestMapping("/admin/problem")
@SaCheckRole(value = {"root", "admin"}, mode = SaMode.OR)
@Validated
public class AdminProblemController {
    @Autowired
    private AdminProblemService adminProblemService;
    @PostMapping
    public R<Void> saveProblem(@Validated @RequestBody ProblemDTO problemDTO) {
        adminProblemService.saveProblem(problemDTO);
        return R.ok();
    }

    @GetMapping("/list")
    public R<Page<AdminProblem>> listProblemByPage(@RequestParam("current") Integer current, @RequestParam("size") Integer size) {
        return R.ok(adminProblemService.listProblemByPage(current, size));
    }
}
