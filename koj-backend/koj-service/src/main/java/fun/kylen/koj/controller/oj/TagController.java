package fun.kylen.koj.controller.oj;

import cn.dev33.satoken.annotation.SaCheckLogin;
import fun.kylen.koj.service.oj.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: KyLen
 * @Date: 2024/8/25 21:37
 * @Description:
 */
@RestController
@RequestMapping("/tag")
@SaCheckLogin
public class TagController {
    @Autowired
    private TagService tagService;

    // @PostMapping("/list/vo")
    // public R<Page<TagVO>> listTagVOByPage(@RequestBody PageDTO pageDTO) {
    //     return R.ok(tagService.listTagVOByPage(pageDTO));
    // }

}
