package fun.kylen.kojservice.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import fun.kylen.kojservice.model.domain.UserInfo;
import fun.kylen.kojservice.model.vo.UserInfoVO;
import fun.kylen.kojservice.service.OAuthService;
import fun.kylen.kojservice.service.PassportService;
import fun.kylen.kojservice.service.UserInfoService;
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
    public UserInfoVO getUserGithubInfo(String code) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("client_id",clientId);
        paramMap.put("client_secret",secret);
        paramMap.put("code",code);
        try (HttpResponse result = HttpRequest.post(github_url).form(paramMap).execute()) {
            JSONObject jsonObject = new JSONObject(result.body());
            String githubUsername = jsonObject.getStr("login");
            Object id = jsonObject.get("id");
            String githubUserId = id.toString();
            return passportService.userPassportByGithub(githubUsername,githubUserId);
        }
    }
}
