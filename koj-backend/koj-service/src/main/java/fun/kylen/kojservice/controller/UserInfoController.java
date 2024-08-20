package fun.kylen.kojservice.controller;

import com.baomidou.mybatisplus.annotation.TableLogic;
import fun.kylen.kojservice.common.R;
import fun.kylen.kojservice.model.vo.UserHomeInfoVO;
import fun.kylen.kojservice.model.vo.UserInfoVO;
import fun.kylen.kojservice.service.UserInfoService;
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
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;


    @GetMapping("/get-user-home-info")
    public R<UserHomeInfoVO> getUserHomeInfo(@RequestParam(value = "userId", required = false) String userId,
                                             @RequestParam(value = "username", required = false) String username) {
        return R.ok(userInfoService.getUserHomeInfo(userId, username));
    }

    @GetMapping("/current-user")
    public R<UserInfoVO> getCurrentUserInfo() {
        return R.ok(userInfoService.getCurrentUserInfo());
    }
}
