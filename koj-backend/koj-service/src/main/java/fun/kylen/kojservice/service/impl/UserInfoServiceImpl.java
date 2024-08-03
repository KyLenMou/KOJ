package fun.kylen.kojservice.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.kylen.kojservice.common.BusinessException;
import fun.kylen.kojservice.common.ResultEnum;
import fun.kylen.kojservice.common.StpEnum;
import fun.kylen.kojservice.common.UserRoleCodeEnum;
import fun.kylen.kojservice.model.domain.UserInfo;
import fun.kylen.kojservice.model.domain.UserRole;
import fun.kylen.kojservice.model.dto.UserLoginDTO;
import fun.kylen.kojservice.model.dto.UserRegisterDTO;
import fun.kylen.kojservice.model.vo.UserHomeInfoVO;
import fun.kylen.kojservice.model.vo.UserInfoVO;
import fun.kylen.kojservice.service.UserInfoService;
import fun.kylen.kojservice.mapper.UserInfoMapper;
import fun.kylen.kojservice.service.UserRoleService;
import fun.kylen.kojservice.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
* @author KyLen
* @description 针对表【user_info】的数据库操作Service实现
* @createDate 2024-08-03 20:14:15
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public void userRegister(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername().trim();
        String password = userRegisterDTO.getPassword().trim();
        String confirmPassword = userRegisterDTO.getConfirmPassword();
        // 检查两次密码是否相同
        if (!password.equals(confirmPassword)) {
            throw new BusinessException(ResultEnum.FAIL, "两次输入密码不相同");
        }
        // 密码要求
        if (password.length() <= 5 || password.length() > 32) {
            throw new BusinessException(ResultEnum.FAIL, "密码长度应在6-32位之间 ");
        }
        // 用户名要求
        if (username.length() > 32) {
            throw new BusinessException(ResultEnum.FAIL, "用户名不能多于32个字符");
        }
        // 检查用户名是否重复
        Long count = lambdaQuery().eq(UserInfo::getUsername, username).count();
        if (count > 0) {
            throw new BusinessException(ResultEnum.FAIL, "用户名已存在，请更换用户名");
        }
        // 密码加盐加密
        password = PasswordUtils.encrypt(password);
        // 用户
        UserInfo user = new UserInfo();
        user.setUsername(username);
        user.setUserPassword(password);
        // 保存到数据库
        boolean save = save(user);
        if (!save) {
            throw new BusinessException(ResultEnum.FAIL, "注册失败");
        }
        // 设置用户权限
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(UserRoleCodeEnum.DEFAULT_USER.getId());
        userRoleService.save(userRole);
    }

    @Override
    public UserInfoVO userLogin(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        // 查询用户名是否存在
        UserInfo user = lambdaQuery().eq(UserInfo::getUsername,username).one();
        if (user == null) {
            throw new BusinessException(ResultEnum.FAIL, "用户名或密码错误");
        }
        // 查询用户密码
        String encryptedPassword = user.getUserPassword();
        // 判断密码是否正确
        if (!PasswordUtils.check(password,encryptedPassword)) {
            throw new BusinessException(ResultEnum.FAIL, "用户名或密码错误");
        }
        // 密码正确，返回用户脱敏信息
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtil.copyProperties(user,userInfoVO);
        // 记录登录态
        StpUtil.login(user.getId());
        StpUtil.getSession().set(StpEnum.CURRENT_USER.getStr(), userInfoVO);
        return userInfoVO;
    }

    @Override
    public void userLogout() {
        StpUtil.logout();
    }

    @Override
    public UserInfoVO getCurrentUserInfo() {
        UserInfoVO currentUser = (UserInfoVO) StpUtil.getSession().get(StpEnum.CURRENT_USER.getStr());
        if (currentUser == null) {
            throw new BusinessException(ResultEnum.NOT_LOGIN);
        }
        return currentUser;
    }

    @Override
    public UserHomeInfoVO getUserHomeInfo(String userId, String username) {
        if (userId != null || username != null) {
            UserInfo userInfo = lambdaQuery().eq(userId != null, UserInfo::getId, userId)
                    .eq(username != null, UserInfo::getUsername, username)
                    .one();
            if (userInfo == null) {
                throw new BusinessException(ResultEnum.PARAMS_ERROR);
            }
            UserHomeInfoVO userHomeInfoVO = new UserHomeInfoVO();
            BeanUtil.copyProperties(userInfo,userHomeInfoVO);
            userHomeInfoVO.setCfRating(1399);
            userHomeInfoVO.setNowcoderRating(1499);
            userHomeInfoVO.setLeetcodeRating(1599);
            return userHomeInfoVO;
        }
        // 查询自己的
        UserInfoVO userInfoVO = (UserInfoVO) StpUtil.getSession().get(StpEnum.CURRENT_USER.getStr());
        userId = userInfoVO.getId();
        username = userInfoVO.getUsername();
        UserInfo userInfo = lambdaQuery().eq(userId != null, UserInfo::getId, userId)
                .eq(username != null, UserInfo::getUsername, username)
                .one();
        if (userInfo == null) {
            throw new BusinessException(ResultEnum.FAIL);
        }
        UserHomeInfoVO userHomeInfoVO = new UserHomeInfoVO();
        BeanUtil.copyProperties(userInfo,userHomeInfoVO);
        userHomeInfoVO.setCfRating(1399);
        userHomeInfoVO.setNowcoderRating(1499);
        userHomeInfoVO.setLeetcodeRating(1599);
        return userHomeInfoVO;
    }
}




