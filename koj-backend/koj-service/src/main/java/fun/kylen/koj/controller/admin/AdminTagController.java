package fun.kylen.koj.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.R;
import fun.kylen.koj.domain.Tag;
import fun.kylen.koj.model.oj.dto.PageDTO;
import fun.kylen.koj.service.admin.AdminTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/8/25 21:37
 * @Description:
 */
@RestController
@RequestMapping("/admin/tag")
@SaCheckRole(value = {"root", "admin"}, mode = SaMode.OR)
@Validated
public class AdminTagController {
    @Autowired
    private AdminTagService adminTagService;

    @PostMapping
    public R<Long> addTag(@RequestBody Tag tag) {
        return R.ok(adminTagService.addTag(tag));
    }

    @DeleteMapping
    public R<Boolean> deleteTag(@RequestParam("id") Long id) {
        return R.ok(adminTagService.deleteTag(id));
    }

    @PutMapping
    public R<Boolean> updateTag(@RequestBody @Validated Tag tag) {
        return R.ok(adminTagService.updateTag(tag));
    }

    @GetMapping
    public R<Tag> getTagById(@RequestParam("id") Long id) {
        return R.ok(adminTagService.getTagById(id));
    }

    @PostMapping("/list")
    public R<Page<Tag>> listTagByPage(@RequestBody PageDTO pageDTO) {
        return R.ok(adminTagService.listTagByPage(pageDTO));
    }
    @GetMapping("/list")
    public R<List<Tag>> listAllTag() {
        return R.ok(adminTagService.listAllTag());
    }


}
