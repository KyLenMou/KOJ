package fun.kylen.koj.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.R;
import fun.kylen.koj.model.admin.dto.AdminEditProblemDTO;
import fun.kylen.koj.model.admin.vo.AdminProblemVO;
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
    public R<Void> editProblem(@Validated @RequestBody AdminEditProblemDTO adminEditProblemDTO, @RequestParam("isUpdate") Boolean isUpdate) {
        adminProblemService.editProblem(adminEditProblemDTO,isUpdate);
        return R.okWithMessage("操作成功");
    }

    @GetMapping("/list")
    public R<Page<AdminProblemVO>> listProblemByPage(@RequestParam("current") Integer current, @RequestParam("size") Integer size) {
        return R.ok(adminProblemService.listProblemByPage(current, size));
    }

    @GetMapping
    public R<AdminEditProblemDTO> getEditProblem(@RequestParam("problemId") Long problemId) {
        return R.ok(adminProblemService.getEditProblem(problemId));
    }

    @DeleteMapping
    public R<Void> deleteProblem(@RequestParam("problemId") Long problemId) {
        adminProblemService.deleteProblem(problemId);
        return R.okWithMessage("删除成功");
    }
}
