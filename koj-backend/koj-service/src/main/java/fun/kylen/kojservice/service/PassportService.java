package fun.kylen.kojservice.service;

import fun.kylen.kojservice.model.dto.UserLoginDTO;
import fun.kylen.kojservice.model.dto.UserRegisterDTO;
import fun.kylen.kojservice.model.vo.UserInfoVO;

/**
* @author KyLen
* @description 针对表【user_info】的数据库操作Service
* @createDate 2024-08-03 20:14:15
*/
public interface PassportService {

    UserInfoVO userRegister(UserRegisterDTO userRegisterDTO);

    UserInfoVO userPassportByGithub(String githubUsername, String githubUserId);

    UserInfoVO userLogin(UserLoginDTO userLoginDTO);

    void userLogout();

    void sendRegisterCode(String email);
}
