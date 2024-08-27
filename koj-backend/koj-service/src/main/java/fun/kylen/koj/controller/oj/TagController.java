package fun.kylen.koj.controller.oj;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.R;
import fun.kylen.koj.domain.Tag;
import fun.kylen.koj.model.dto.PageDTO;
import fun.kylen.koj.model.vo.TagVO;
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
