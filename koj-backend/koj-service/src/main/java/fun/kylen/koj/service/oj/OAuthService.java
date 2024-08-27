package fun.kylen.koj.service.oj;

import fun.kylen.koj.model.vo.UserInfoVO;

/**
 * @Author: KyLen
 * @Date: 2024/8/11 17:34
 * @Description:
 */
public interface OAuthService {
    UserInfoVO handleGithubPassport(String code);
}
