package fun.kylen.koj.schedule;

import fun.kylen.koj.constant.RedisKeyConstant;
import fun.kylen.koj.mq.JudgeQueueManager;
import fun.kylen.koj.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: KyLen
 * @Date: 2024/12/14 19:16
 * @Description:
 */
@Component
public class CommonSchedule {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JudgeQueueManager judgeQueueManager;

    /**
     * 每秒查询一次队列大小并缓存
     */
    @Scheduled(fixedRate = 1000)
    public void getQueueSize() {
        redisUtil.set(RedisKeyConstant.JUDGE_QUEUE_SIZE, judgeQueueManager.getQueueSize());
    }
}