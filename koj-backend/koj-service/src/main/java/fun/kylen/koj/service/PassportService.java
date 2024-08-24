package fun.kylen.koj.service;

import fun.kylen.koj.model.dto.UserLoginDTO;
import fun.kylen.koj.model.dto.UserRegisterDTO;
import fun.kylen.koj.model.vo.UserInfoVO;

public interface PassportService {

    UserInfoVO userRegister(UserRegisterDTO userRegisterDTO);

    UserInfoVO handleGithubPassport(String githubUsername, String githubUserId, String avatarUrl);

    UserInfoVO userLogin(UserLoginDTO userLoginDTO);

    void userLogout();

    void sendRegisterCode(String email);

    UserInfoVO getCurrentUserInfo();

}
