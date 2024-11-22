package fun.kylen.koj.controller.oj;

import fun.kylen.koj.common.R;
import fun.kylen.koj.model.oj.dto.UserLoginDTO;
import fun.kylen.koj.model.oj.dto.UserRegisterDTO;
import fun.kylen.koj.model.oj.vo.UserInfoVO;
import fun.kylen.koj.service.oj.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
    public R<Void> userRegister(@Validated @RequestBody UserRegisterDTO userRegisterDTO) {
        passportService.userRegister(userRegisterDTO);
        return R.okWithMessage("注册成功，请登录");
    }

    @PostMapping("/send-register-code")
    public R<Void> sendRegisterCode(@RequestParam("email") @Email @NotBlank String email) {
        passportService.sendRegisterCode(email);
        return R.okWithMessage("已发送验证码");
    }

    @PostMapping("/login")
    public R<UserInfoVO> userLogin(@Validated @RequestBody UserLoginDTO userLoginDTO) {
        return R.ok(passportService.userLogin(userLoginDTO),"登录成功");
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

    /**
     * github oauth2 方式登录
     * @param code
     * @return
     */
    @GetMapping("/github")
    public R<UserInfoVO> handleGithubPassport(@RequestParam("code") String code) {
        return R.ok(passportService.handleGithubPassport(code));
    }
}