package fun.kylen.koj.mq;

import fun.kylen.koj.constant.MqConstant;
import fun.kylen.koj.judge.JudgeManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
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
    private JudgeManager judgeManager;

    @RabbitListener(queues = MqConstant.JUDGE_QUEUE)
    public void consumeJudgeMessage(Long submissionId) {
        try {
            judgeManager.judge(submissionId);
        } catch (RuntimeException e) {
            // 进入死信队列
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

    @RabbitListener(queues = MqConstant.DEBUG_QUEUE)
    public void consumeDebugMessage(String debugId) {
        try {
            judgeManager.debug(debugId);
        } catch (Exception e) {
            // 直接消失
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

    /**
     * 死信队列
     */
}
