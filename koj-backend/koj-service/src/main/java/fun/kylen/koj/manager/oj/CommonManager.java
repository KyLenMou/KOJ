package fun.kylen.koj.manager.oj;

import fun.kylen.koj.constant.RedisKeyConstant;
import fun.kylen.koj.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: KyLen
 * @Date: 2024/12/14 19:36
 * @Description:
 */
@Component
public class CommonManager {
    @Autowired
    private RedisUtil redisUtil;

    public Integer getQueueSize() {
        return redisUtil.get(RedisKeyConstant.JUDGE_QUEUE_SIZE, Integer.class);
    }
}
