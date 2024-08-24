package fun.kylen.koj.controller;

import fun.kylen.koj.common.R;
import fun.kylen.koj.model.vo.UserInfoVO;
import fun.kylen.koj.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: KyLen
 * @Date: 2024/8/11 16:20
 * @Description:
 */
@RestController
@RequestMapping("/oauth")
public class OAuthController {
    @Autowired
    private OAuthService oAuthService;

    @GetMapping("/github")
    public R<UserInfoVO> handleGithubPassport(@RequestParam("code") String code) {
        return R.ok(oAuthService.handleGithubPassport(code));
    }
}
