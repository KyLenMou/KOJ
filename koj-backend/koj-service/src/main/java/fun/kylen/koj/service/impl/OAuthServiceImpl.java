package fun.kylen.koj.service.impl;

import fun.kylen.koj.manager.OAuthManager;
import fun.kylen.koj.model.vo.UserInfoVO;
import fun.kylen.koj.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: KyLen
 * @Date: 2024/8/24 18:10
 * @Description:
 */
@Service
public class OAuthServiceImpl implements OAuthService {
    @Autowired
    private OAuthManager oAuthManager;
    @Override
    public UserInfoVO handleGithubPassport(String code) {
        return oAuthManager.handleGithubPassport(code);
    }
}
