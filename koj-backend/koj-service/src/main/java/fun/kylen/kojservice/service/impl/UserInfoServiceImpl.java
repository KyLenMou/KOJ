package fun.kylen.kojservice.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import fun.kylen.kojservice.model.vo.UserHomeInfoVO;
import fun.kylen.kojservice.model.vo.UserInfoVO;
import fun.kylen.kojservice.service.UserInfoService;
import fun.kylen.kojservice.mapper.UserInfoMapper;
import fun.kylen.kojservice.service.UserRoleService;
import fun.kylen.kojservice.utils.PasswordUtil;
import fun.kylen.kojservice.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author KyLen
* @description 针对表【user_info】的数据库操作Service实现
* @createDate 2024-08-03 20:14:15
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService{
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
        UserInfoVO userInfoVO = (UserInfoVO) StpUtil.getSession().get(StpConstant.CURRENT_USER);
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




