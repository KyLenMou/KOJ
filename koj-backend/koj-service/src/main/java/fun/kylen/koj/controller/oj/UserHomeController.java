package fun.kylen.koj.controller.oj;

import fun.kylen.koj.common.R;
import fun.kylen.koj.model.oj.vo.UserHomeInfoVO;
import fun.kylen.koj.service.oj.UserHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: KyLen
 * @Date: 2024/8/3 22:41
 * @Description:
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserHomeController {

    @Autowired
    private UserHomeService userHomeService;

    @GetMapping("/get-user-home-info")
    public R<UserHomeInfoVO> getUserHomeInfo(@RequestParam(value = "userId", required = false) String userId,
                                             @RequestParam(value = "username", required = false) String username) {
        return R.ok(userHomeService.getUserHomeInfo(userId, username));
    }

}
