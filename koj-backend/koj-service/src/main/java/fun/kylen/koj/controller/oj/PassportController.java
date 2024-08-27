package fun.kylen.koj.controller.oj;

import fun.kylen.koj.common.R;
import fun.kylen.koj.model.dto.UserLoginDTO;
import fun.kylen.koj.model.dto.UserRegisterDTO;
import fun.kylen.koj.model.vo.UserInfoVO;
import fun.kylen.koj.service.oj.PassportService;
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
    private PassportService passportService;

    @PostMapping("/register")
    public R<UserInfoVO> userRegister(@Validated @RequestBody UserRegisterDTO userRegisterDTO) {
        return R.ok(passportService.userRegister(userRegisterDTO));
    }

    @GetMapping("/get-register-code")
    public R<Void> getRegisterCode(@RequestParam("email") @Email String email) {
        passportService.sendRegisterCode(email);
        return R.okWithMessage("已发送验证码");
    }

    @PostMapping("/login")
    public R<UserInfoVO> userLogin(@Validated @RequestBody UserLoginDTO userLoginDTO) {
        return R.ok(passportService.userLogin(userLoginDTO));
    }

    @PostMapping("/logout")
    public R<Void> userLogout() {
        passportService.userLogout();
        return R.okWithMessage("退出登录成功");
    }

    @GetMapping("/current-user")
    public R<UserInfoVO> getCurrentUserInfo() {
        return R.ok(passportService.getCurrentUserInfo());
    }

}