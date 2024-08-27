package fun.kylen.koj.service.oj.impl;

import fun.kylen.koj.manager.oj.PassportManager;
import fun.kylen.koj.model.dto.UserLoginDTO;
import fun.kylen.koj.model.dto.UserRegisterDTO;
import fun.kylen.koj.model.vo.UserInfoVO;
import fun.kylen.koj.service.oj.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassportServiceImpl implements PassportService {
    @Autowired
    private PassportManager passportManager;

    @Override
    public UserInfoVO userRegister(UserRegisterDTO userRegisterDTO) {
        return passportManager.userRegister(userRegisterDTO);
    }

    @Override
    public UserInfoVO handleGithubPassport(String githubUsername, String githubUserId, String avatarUrl) {
        return passportManager.handleGithubPassport(githubUsername, githubUserId, avatarUrl);
    }

    @Override
    public UserInfoVO userLogin(UserLoginDTO userLoginDTO) {
        return passportManager.userLogin(userLoginDTO);
    }

    @Override
    public void userLogout() {
        passportManager.userLogout();
    }

    @Override
    public void sendRegisterCode(String email) {
        passportManager.sendRegisterCode(email);
    }

    @Override
    public UserInfoVO getCurrentUserInfo() {
        return passportManager.getCurrentUserInfo();
    }
}
