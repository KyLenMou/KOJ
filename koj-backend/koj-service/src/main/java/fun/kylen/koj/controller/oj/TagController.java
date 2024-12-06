package fun.kylen.koj.controller.oj;

import cn.dev33.satoken.annotation.SaCheckLogin;
import fun.kylen.koj.common.R;
import fun.kylen.koj.model.oj.vo.TagVO;
import fun.kylen.koj.service.oj.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/list")
    public R<List<TagVO>> getTagList() {
        return R.ok(tagService.getTagList());
    }

}
