package fun.kylen.koj.utils;

import cn.dev33.satoken.stp.StpUtil;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.constant.StpConstant;
import fun.kylen.koj.model.oj.vo.UserInfoVO;

/**
 * @Author: KyLen
 * @Date: 2024/11/24 08:59
 * @Description:
 */
public class PassportUtil {

    public static UserInfoVO getCurrentUser() {
        return (UserInfoVO) StpUtil.getSession().get(StpConstant.CURRENT_USER);
    }

    /**
     * 返回用户信息，如果未登录则抛出异常
     * @return
     */
    public static UserInfoVO getCurrentUserIfLogin() {
        UserInfoVO currentUser = PassportUtil.getCurrentUser();
        if (currentUser == null) {
            throw new BusinessException(ResultEnum.NOT_LOGIN, "未登录，请先登录");
        }
        return currentUser;
    }
}
