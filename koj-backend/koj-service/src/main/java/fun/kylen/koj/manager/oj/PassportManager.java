package fun.kylen.koj.manager.oj;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.constant.RedisKeyConstant;
import fun.kylen.koj.constant.StpConstant;
import fun.kylen.koj.constant.UserRoleConstant;
import fun.kylen.koj.domain.UserInfo;
import fun.kylen.koj.model.oj.dto.UserLoginDTO;
import fun.kylen.koj.model.oj.dto.UserRegisterDTO;
import fun.kylen.koj.model.oj.vo.UserInfoVO;
import fun.kylen.koj.dao.UserInfoEntityService;
import fun.kylen.koj.utils.PasswordUtil;
import fun.kylen.koj.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: KyLen
 * @Date: 2024/8/11 17:38
 * @Description:
 */
@Component
@Slf4j
public class PassportManager {
    @Autowired
    private UserInfoEntityService userInfoEntityService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private EmailManager emailManager;

    public void userRegister(UserRegisterDTO userRegisterDTO) {
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
        if (password.length() < 5 || password.length() > 32) {
            throw new BusinessException(ResultEnum.FAIL, "密码长度应在5-32位之间");
        }
        // 用户名要求
        if (username.length() < 5 ||username.length() > 32) {
            throw new BusinessException(ResultEnum.FAIL, "用户名长度应在5-32位之间");
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
        Long count = userInfoEntityService.lambdaQuery().eq(UserInfo::getUsername, username).count();
        if (count > 0) {
            throw new BusinessException(ResultEnum.FAIL, "用户名已存在，请更换用户名");
        }
        // 密码加盐加密
        password = PasswordUtil.encrypt(password);
        // 用户
        UserInfo user = new UserInfo();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        // 设置用户权限
        user.setRole(UserRoleConstant.DEFAULT_USER);
        // 保存到数据库
        boolean save = userInfoEntityService.save(user);
        if (!save) {
            log.error("用户{}注册失败", username);
            throw new BusinessException(ResultEnum.FAIL, "注册失败");
        }
    }


    /**
     * Github登录首次登陆自动注册
     * @param githubUsername
     * @param githubUserId
     * @param avatarUrl
     * @return
     */
    public UserInfoVO handleGithubPassport(String githubUsername, String githubUserId, String avatarUrl) {
        // 根据githubId查询用户
        UserInfo userInfo = userInfoEntityService.lambdaQuery().eq(UserInfo::getGithubId, githubUserId).one();
        if (userInfo == null) {
            // 未注册过，新增一个用户
            UserInfo user = new UserInfo();
            user.setGithubUsername(githubUsername);
            user.setGithubId(githubUserId);
            user.setAvatar(avatarUrl);
            // 用户名默认为github用户名，但是检查是否重复
            String username = githubUsername;
            Long count = userInfoEntityService.lambdaQuery().eq(UserInfo::getUsername, username).count();
            while (count > 0) {
                username = githubUsername + "_" + RandomUtil.randomString(2);
                log.warn("github用户{}的用户名出现重复，新的用户名为{}", githubUsername, username);
                count = userInfoEntityService.lambdaQuery().eq(UserInfo::getUsername, username).count();
            }
            user.setUsername(username);
            // 添加到数据库
            boolean save = userInfoEntityService.save(user);
            if (!save) {
                throw new BusinessException(ResultEnum.FAIL, "Github注册失败");
            }
            // 登录
            UserInfoVO userInfoVO = new UserInfoVO();
            BeanUtil.copyProperties(user, userInfoVO);
            StpUtil.login(user.getId());
            StpUtil.getSession().set(StpConstant.CURRENT_USER, userInfoVO);
            return userInfoVO;
        }

        // 注册过
        return loginWithSetCurrentUser(userInfo);
    }

    public UserInfoVO userLogin(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        // todo 邮箱登录
        // 查询用户名是否存在
        UserInfo user = userInfoEntityService.lambdaQuery().eq(UserInfo::getUsername, username).one();
        if (user == null) {
            throw new BusinessException(ResultEnum.FAIL, "用户名或密码错误");
        }
        // 查询用户密码
        String encryptedPassword = user.getPassword();
        // 判断密码是否正确
        if (!PasswordUtil.check(password, encryptedPassword)) {
            throw new BusinessException(ResultEnum.FAIL, "用户名或密码错误");
        }
        return loginWithSetCurrentUser(user);
    }

    public void userLogout() {
        StpUtil.logout();
    }

    public void sendRegisterCode(String email) {
        // 是否开启邮箱注册功能

        // 白名单检验

        // 邮箱是否已被注册
        long count = userInfoEntityService.lambdaQuery().eq(UserInfo::getEmail, email).count();
        if (count > 0) {
            throw new BusinessException(ResultEnum.FAIL, "该邮箱已被注册！");
        }
        // 一分钟内是否已经发送过
        String emailRegisterKey = RedisKeyConstant.EMAIL_REGISTER + email;
        if (redisUtil.hasKey(emailRegisterKey)) {
            Long expireTime = redisUtil.getExpireTime(emailRegisterKey);
            long remainTime = 300 - expireTime;
            if (remainTime < 60) {
                throw new BusinessException(ResultEnum.FAIL, "发送验证码频率过快，请" + (60 - remainTime) + "秒后再次发送！");
            }
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

    public UserInfoVO getCurrentUserInfo() {
        UserInfoVO currentUser = (UserInfoVO) StpUtil.getSession().get(StpConstant.CURRENT_USER);
        if (currentUser == null) {
            throw new BusinessException(ResultEnum.NOT_LOGIN);
        }
        return currentUser;
    }


    /**
     * 登录并设置当前用户
     * @param user
     * @return
     */
    private UserInfoVO loginWithSetCurrentUser(UserInfo user) {
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtil.copyProperties(user, userInfoVO);
        StpUtil.login(user.getId());
        StpUtil.getSession().set(StpConstant.CURRENT_USER, userInfoVO);
        return userInfoVO;
    }
}