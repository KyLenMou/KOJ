package fun.kylen.koj.utils;

import cn.dev33.satoken.stp.StpUtil;
import fun.kylen.koj.constant.StpConstant;
import fun.kylen.koj.model.oj.vo.UserInfoVO;
import org.springframework.stereotype.Component;

/**
 * @Author: KyLen
 * @Date: 2024/11/24 08:59
 * @Description:
 */
public class PassportUtil {
    public static UserInfoVO getCurrentUser() {
        return (UserInfoVO) StpUtil.getSession().get(StpConstant.CURRENT_USER);
    }
}
