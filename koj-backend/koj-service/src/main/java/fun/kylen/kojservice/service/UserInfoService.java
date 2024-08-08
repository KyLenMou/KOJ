package fun.kylen.kojservice.service;

import fun.kylen.kojservice.model.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.kylen.kojservice.model.dto.UserLoginDTO;
import fun.kylen.kojservice.model.dto.UserRegisterDTO;
import fun.kylen.kojservice.model.vo.UserHomeInfoVO;
import fun.kylen.kojservice.model.vo.UserInfoVO;

/**
* @author KyLen
* @description 针对表【user_info】的数据库操作Service
* @createDate 2024-08-03 20:14:15
*/
public interface UserInfoService extends IService<UserInfo> {

    void userRegister(UserRegisterDTO userRegisterDTO);

    UserInfoVO userLogin(UserLoginDTO userLoginDTO);

    void userLogout();

    UserInfoVO getCurrentUserInfo();

    UserHomeInfoVO getUserHomeInfo(String userId, String username);

    void sendRegisterCode(String email);
}
