package fun.kylen.koj.mq;

import fun.kylen.koj.constant.MqConstant;
import fun.kylen.koj.service.JudgeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: KyLen
 * @Date: 2024/9/12 18:04
 * @Description:
 */
@Component
@Slf4j
public class JudgeMessageConsumer {
    @Autowired
    private JudgeService judgeService;

    @SneakyThrows
    @RabbitListener(queues = MqConstant.JUDGE_QUEUE, concurrency = "2")
    public void consumeJudgeMessage(String message) {
        // 成功，则自动确认；报错则重试三次，失败则进入死信队列
        Long submitId = Long.parseLong(message);
        judgeService.judge(submitId);
    }

    @RabbitListener(queues = MqConstant.TEST_QUEUE, concurrency = "2")
    public void consumeTestMessage(String data) {

    }

    /**
     * 死信队列
     */
}
