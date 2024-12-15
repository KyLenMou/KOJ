package fun.kylen.koj.utils;

import cn.hutool.core.util.StrUtil;
import fun.kylen.koj.vo.DebugVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: KyLen
 * @Date: 2024/12/5 11:15
 * @Description:
 */
@Component
@Slf4j
public class RedisUtil {
    @Resource
    private RedisTemplate<String,Object> r;

    public DebugVO getDebugVO(String debugId) {
        try {
            return StrUtil.isBlank(debugId) ? null : (DebugVO) r.opsForValue().get("debug:" + debugId);
        } catch (Exception e) {
            log.error("Redis getDebugVO error:{}", e.getMessage());
            return null;
        }
    }

    public boolean setDebugVO(String debugId, DebugVO debugVO) {
        try {
            r.opsForValue().set("debug:" + debugId, debugVO);
            return true;
        } catch (Exception e) {
            log.error("Redis setDebugVO error:{}", e.getMessage());
            return false;
        }
    }

    public boolean setQueueFront(Long submissionId) {
        try {
            r.opsForValue().set("judge:queue:front", submissionId, 60, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error("Redis setQueueFront error:{}", e.getMessage());
            return false;
        }
    }

}
