package fun.kylen.kojservice.controller;

import fun.kylen.kojservice.common.R;
import fun.kylen.kojservice.model.dto.UserLoginDTO;
import fun.kylen.kojservice.model.dto.UserRegisterDTO;
import fun.kylen.kojservice.model.vo.UserInfoVO;
import fun.kylen.kojservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

/**
 * @Author: KyLen
 * @Date: 2024/7/19 下午3:55
 * @Description:
 */
@RestController
@RequestMapping("/passport")
@Validated
public class PassportController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/register")
    public R<Void> userRegister(@Validated @RequestBody UserRegisterDTO userRegisterDTO) {
        userInfoService.userRegister(userRegisterDTO);
        return R.ok("注册成功");
    }

    @GetMapping("/get-register-code")
    public R<Void> getRegisterCode(@RequestParam("email") @Email String email) {
        userInfoService.sendRegisterCode(email);
        return R.ok("已发送验证码");
    }

    @PostMapping("/login")
    public R<UserInfoVO> userLogin(@Validated @RequestBody UserLoginDTO userLoginDTO) {
        return R.ok(userInfoService.userLogin(userLoginDTO));
    }

    @PostMapping("/logout")
    public R<Void> userLogout() {
        userInfoService.userLogout();
        return R.ok("退出登录成功");
    }

    @GetMapping("/current-user")
    public R<UserInfoVO> getCurrentUserInfo() {
        return R.ok(userInfoService.getCurrentUserInfo());
    }

}