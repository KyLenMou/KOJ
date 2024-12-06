package fun.kylen.koj.utils;

import cn.hutool.core.util.StrUtil;
import fun.kylen.koj.vo.DebugVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: KyLen
 * @Date: 2024/12/5 11:15
 * @Description:
 */
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String,Object> r;

    public DebugVO getDebugVO(String debugId) {
        try {
            return StrUtil.isBlank(debugId) ? null : (DebugVO) r.opsForValue().get("debug:" + debugId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean setDebugVO(String debugId, DebugVO debugVO) {
        try {
            r.opsForValue().set("debug:" + debugId, debugVO);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
