package fun.kylen.koj.service.oj;

import fun.kylen.koj.model.dto.UserLoginDTO;
import fun.kylen.koj.model.dto.UserRegisterDTO;
import fun.kylen.koj.model.vo.UserInfoVO;

public interface PassportService {

    void userRegister(UserRegisterDTO userRegisterDTO);

    UserInfoVO handleGithubPassport(String code);

    UserInfoVO userLogin(UserLoginDTO userLoginDTO);

    void userLogout();

    void sendRegisterCode(String email);

    UserInfoVO getCurrentUserInfo();

}
