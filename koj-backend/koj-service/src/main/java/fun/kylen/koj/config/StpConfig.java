package fun.kylen.koj.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import fun.kylen.koj.constant.StpConstant;
import fun.kylen.koj.model.vo.UserInfoVO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/7/20 15:45
 * @Description:
 */
@Component
public class StpConfig implements StpInterface {
    @Override
    public List<String> getPermissionList(Object loginId, String s) {
        return Collections.emptyList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String s) {
        UserInfoVO currentUser = (UserInfoVO) StpUtil.getSession().get(StpConstant.CURRENT_USER);
        if (currentUser != null) {
            return Collections.singletonList(currentUser.getUserRole());
        }
        return Collections.emptyList();
    }
}
