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
    public R<Long> addTag(@RequestParam("tagName") String tagName){
        return R.ok(adminTagService.addTag(tagName), "添加成功");
    }

    @DeleteMapping
    public R<Void> deleteTag(@RequestParam("id") Long id) {
        adminTagService.deleteTag(id);
        return R.okWithMessage("删除成功");
    }

    @PutMapping
    public R<Void> updateTag(@RequestBody @Validated Tag tag) {
        adminTagService.updateTag(tag);
        return R.okWithMessage("更新成功");
    }

    @GetMapping
    public R<Tag> getTagById(@RequestParam("id") Long id) {
        return R.ok(adminTagService.getTagById(id));
    }

    @GetMapping("/list")
    public R<List<Tag>> listAllTag() {
        return R.ok(adminTagService.listAllTag());
    }


}
