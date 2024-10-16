package fun.kylen.koj.manager.oj;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.config.RestTemplateConfig;
import fun.kylen.koj.model.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: KyLen
 * @Date: 2024/8/11 17:34
 * @Description:
 */
@Component
@Slf4j
public class OAuthManager{
    @Value("${oauth2.github-secret}")
    private String secret;
    @Value("${oauth2.github-client-id}")
    private String clientId;
    @Autowired
    private PassportManager passportManager;
    @Autowired
    private RestTemplate oAuthRestTemplate;

    private final static String github_url = "https://github.com/login/oauth/access_token";

    public UserInfoVO handleGithubPassport(String code) {
        if (StpUtil.isLogin()) {
            throw new BusinessException(ResultEnum.FAIL, "已登录，请勿重复操作");
        }
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("client_id",clientId);
        paramMap.put("client_secret",secret);
        paramMap.put("code",code);
        // 使用code获取access_token
        try {
            String body = oAuthRestTemplate.postForEntity(github_url, paramMap, String.class).getBody();
            String accessToken = StrUtil.subBetween(body, "access_token=", "&scope");
            String tokenType = StrUtil.subAfter(body, "token_type=", true);
            // 使用access_token获取用户信息
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", tokenType + " " + accessToken);
            body = oAuthRestTemplate.exchange(
                    "https://api.github.com/user",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            ).getBody();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            String githubUsername = jsonObject.getStr("login");
            String githubUserId = jsonObject.getStr("id");
            String avatarUrl = jsonObject.getStr("avatar_url");
            return passportManager.handleGithubPassport(githubUsername,githubUserId,avatarUrl);
        } catch (ResourceAccessException e) {
            log.error("Github请求超时:{}", e.getMessage());
            throw new BusinessException(ResultEnum.FAIL, "服务器请求Github超时，请稍后重试");
        } catch (Exception e) {
            log.error("Github请求失败:{}", e.getMessage());
            throw new BusinessException(ResultEnum.FAIL, "Github请求失败，请稍后重试");
        }
    }
}
