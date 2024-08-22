package fun.kylen.kojservice.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import fun.kylen.kojservice.common.BusinessException;
import fun.kylen.kojservice.common.ResultEnum;
import fun.kylen.kojservice.model.vo.UserInfoVO;
import fun.kylen.kojservice.service.OAuthService;
import fun.kylen.kojservice.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: KyLen
 * @Date: 2024/8/11 17:34
 * @Description:
 */
@Service
public class OAuthServiceImpl implements OAuthService {
    @Value("${oauth2.github-secret}")
    private String secret;
    @Value("${oauth2.github-client-id}")
    private String clientId;
    @Autowired
    private PassportService passportService;

    private final static String github_url = "https://github.com/login/oauth/access_token";

    @Override
    public UserInfoVO handleGithubPassport(String code) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("client_id",clientId);
        paramMap.put("client_secret",secret);
        paramMap.put("code",code);
        // 使用code获取access_token
        try (HttpResponse result = HttpRequest.post(github_url)
                .form(paramMap)
                .execute()) {
            String body = result.body();
            String accessToken = StrUtil.subBetween(body, "access_token=", "&scope");
            String tokenType = StrUtil.subAfter(body, "token_type=", true);
            // 使用access_token获取用户信息
            try (HttpResponse response = HttpUtil.createGet("https://api.github.com/user")
                    .header("Authorization", tokenType + " " + accessToken)
                    .execute()) {
                JSONObject jsonObject = new JSONObject(response.body());
                String githubUsername = jsonObject.getStr("login");
                String githubUserId = jsonObject.getStr("id");
                String avatarUrl = jsonObject.getStr("avatar_url");
                return passportService.handleGithubPassport(githubUsername,githubUserId,avatarUrl);
            }
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.FAIL, "Github请求失败，请稍后重试");
        }
    }
}
