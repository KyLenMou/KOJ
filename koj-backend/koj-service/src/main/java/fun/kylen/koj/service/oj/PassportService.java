package fun.kylen.koj.service.oj;

import fun.kylen.koj.model.oj.dto.UserLoginDTO;
import fun.kylen.koj.model.oj.dto.UserRegisterDTO;
import fun.kylen.koj.model.oj.vo.UserInfoVO;

public interface PassportService {

    void userRegister(UserRegisterDTO userRegisterDTO);

    UserInfoVO handleGithubPassport(String code);

    UserInfoVO userLogin(UserLoginDTO userLoginDTO);

    void userLogout();

    void sendRegisterCode(String email);

    UserInfoVO getCurrentUserInfo();

}
