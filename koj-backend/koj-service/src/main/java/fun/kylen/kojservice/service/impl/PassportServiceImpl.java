package fun.kylen.kojservice.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import fun.kylen.kojservice.common.BusinessException;
import fun.kylen.kojservice.common.ResultEnum;
import fun.kylen.kojservice.constant.RedisKeyConstant;
import fun.kylen.kojservice.constant.StpConstant;
import fun.kylen.kojservice.constant.UserRoleConstant;
import fun.kylen.kojservice.manager.EmailManager;
import fun.kylen.kojservice.model.domain.UserInfo;
import fun.kylen.kojservice.model.domain.UserRole;
import fun.kylen.kojservice.model.dto.UserLoginDTO;
import fun.kylen.kojservice.model.dto.UserRegisterDTO;
import fun.kylen.kojservice.model.vo.UserInfoVO;
import fun.kylen.kojservice.service.PassportService;
import fun.kylen.kojservice.service.UserInfoService;
import fun.kylen.kojservice.service.UserRoleService;
import fun.kylen.kojservice.utils.PasswordUtil;
import fun.kylen.kojservice.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: KyLen
 * @Date: 2024/8/11 17:38
 * @Description:
 */
@Service
@Slf4j
public class PassportServiceImpl implements PassportService {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private EmailManager emailManager;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public UserInfoVO userRegister(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername().trim();
        String password = userRegisterDTO.getPassword().trim();
        String confirmPassword = userRegisterDTO.getConfirmPassword();
        String email = userRegisterDTO.getEmail();
        String code = userRegisterDTO.getCode();
        // 检查两次密码是否相同
        if (!password.equals(confirmPassword)) {
            throw new BusinessException(ResultEnum.FAIL, "两次输入密码不相同");
        }
        // 密码要求
        if (password.length() <= 5 || password.length() > 32) {
            throw new BusinessException(ResultEnum.FAIL, "密码长度应在6-32位之间");
        }
        // 用户名要求
        if (username.length() > 32) {
            throw new BusinessException(ResultEnum.FAIL, "用户名不能多于32个字符");
        }
        // 邮箱验证码是否正确
        String registerCode = redisUtil.getStr(RedisKeyConstant.EMAIL_REGISTER + email);
        if (registerCode == null) {
            throw new BusinessException(ResultEnum.FAIL, "验证码已过期或不存在，请重新获取验证码");
        }
        if (!StrUtil.equals(registerCode, code)) {
            throw new BusinessException(ResultEnum.FAIL, "验证码错误");
        }
        // 检查用户名是否重复
        Long count = userInfoService.lambdaQuery().eq(UserInfo::getUsername, username).count();
        if (count > 0) {
            throw new BusinessException(ResultEnum.FAIL, "用户名已存在，请更换用户名");
        }
        // 密码加盐加密
        password = PasswordUtil.encrypt(password);
        // 用户
        UserInfo user = new UserInfo();
        user.setUsername(username);
        user.setUserPassword(password);
        user.setEmail(email);
        // 保存到数据库
        boolean save = userInfoService.save(user);
        if (!save) {
            throw new BusinessException(ResultEnum.FAIL, "注册失败");
        }
        // 设置用户权限
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(UserRoleConstant.DEFAULT_USER);
        userRoleService.save(userRole);
        // 登录
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtil.copyProperties(user, userInfoVO);
        StpUtil.login(user.getId());
        StpUtil.getSession().set(StpConstant.CURRENT_USER, userInfoVO);
        return userInfoVO;
    }

    @Override
    public UserInfoVO userPassportByGithub(String githubUsername, String githubUserId) {
        UserInfo userInfo = userInfoService.lambdaQuery().eq(UserInfo::getGithubId, githubUserId).one();
        if (userInfo == null) {
            // 未注册过，新增一个用户
            UserInfo user = new UserInfo();
            user.setGithubUsername(githubUsername);
            user.setGithubId(githubUserId);
            // 用户名默认为github用户名，但是检查是否重复
            String username = githubUsername;
            Long count = userInfoService.lambdaQuery().eq(UserInfo::getUsername, username).count();
            while (count > 0) {
                username = githubUsername + "_" + RandomUtil.randomString(5);
                log.warn("github用户{}的用户名出现重复，新的用户名为{}", githubUsername, username);
                count = userInfoService.lambdaQuery().eq(UserInfo::getUsername, username).count();
            }
            user.setUsername(username);
            // 添加到数据库
            boolean save = userInfoService.save(user);
            if (!save) {
                throw new BusinessException(ResultEnum.FAIL, "github注册失败");
            }
            // 登录
            UserInfoVO userInfoVO = new UserInfoVO();
            BeanUtil.copyProperties(user, userInfoVO);
            StpUtil.login(user.getId());
            StpUtil.getSession().set(StpConstant.CURRENT_USER, userInfoVO);
            return userInfoVO;
        } else {
            // 注册过
            UserInfoVO userInfoVO = new UserInfoVO();
            BeanUtil.copyProperties(userInfo, userInfoVO);
            StpUtil.login(userInfo.getId());
            StpUtil.getSession().set(StpConstant.CURRENT_USER, userInfoVO);
            return userInfoVO;
        }
    }

    @Override
    public UserInfoVO userLogin(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        // 查询用户名是否存在
        UserInfo user = userInfoService.lambdaQuery().eq(UserInfo::getUsername, username).one();
        if (user == null) {
            throw new BusinessException(ResultEnum.FAIL, "用户名或密码错误");
        }
        // 查询用户密码
        String encryptedPassword = user.getUserPassword();
        // 判断密码是否正确
        if (!PasswordUtil.check(password, encryptedPassword)) {
            throw new BusinessException(ResultEnum.FAIL, "用户名或密码错误");
        }
        // 密码正确，返回用户脱敏信息
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtil.copyProperties(user, userInfoVO);
        // 记录登录态
        StpUtil.login(user.getId());
        StpUtil.getSession().set(StpConstant.CURRENT_USER, userInfoVO);
        return userInfoVO;
    }

    @Override
    public void userLogout() {
        StpUtil.logout();
    }

    @Override
    public UserInfoVO getCurrentUserInfo() {
        UserInfoVO currentUser = (UserInfoVO) StpUtil.getSession().get(StpConstant.CURRENT_USER);
        if (currentUser == null) {
            throw new BusinessException(ResultEnum.NOT_LOGIN);
        }
        return currentUser;
    }

    @Override
    public void sendRegisterCode(String email) {
        // 是否开启邮箱注册功能

        // 白名单检验

        // 邮箱是否已被注册
        long count = userInfoService.lambdaQuery().eq(UserInfo::getEmail, email).count();
        if (count > 0) {
            throw new BusinessException(ResultEnum.FAIL, "该邮箱已被注册！");
        }
        // 五分钟内是否已经发送过
        String emailRegisterKey = RedisKeyConstant.EMAIL_REGISTER + email;
        if (redisUtil.hasKey(emailRegisterKey)) {
            throw new BusinessException(ResultEnum.FAIL, "发送验证码频率过快，请" + redisUtil.getExpireTime(emailRegisterKey) + "秒后再次发送！");
        }
        // 生成六位验证码
        String code = RandomUtil.randomNumbers(6);
        // 设置验证码，限制五分钟内
        boolean flag = redisUtil.set(emailRegisterKey, code, 300);
        if (!flag) {
            throw new BusinessException(ResultEnum.FAIL);
        }
        // 发送验证码
        emailManager.sendRegisterCode(email, code);
    }
}