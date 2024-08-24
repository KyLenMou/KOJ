package fun.kylen.koj.manager;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.constant.StpConstant;
import fun.kylen.koj.domain.UserInfo;
import fun.kylen.koj.model.vo.UserHomeInfoVO;
import fun.kylen.koj.model.vo.UserInfoVO;
import fun.kylen.koj.dao.UserInfoEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserHomeManager {
    @Autowired
    private UserInfoEntityService userInfoEntityService;
    public UserHomeInfoVO getUserHomeInfo(String userId, String username) {
        if (userId != null || username != null) {
            UserInfo userInfo = userInfoEntityService.lambdaQuery().eq(userId != null, UserInfo::getId, userId)
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
        UserInfo userInfo = userInfoEntityService.lambdaQuery().eq(userId != null, UserInfo::getId, userId)
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




