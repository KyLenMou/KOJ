package fun.kylen.koj.mq;

import fun.kylen.koj.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @Author: KyLen
 * @Date: 2024/9/10 14:00
 * @Description:
 */
@Component
@Slf4j
public class JudgeQueueManager {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpAdmin amqpAdmin;

    public void dispatch(Long submissionId) {
        // todo 提交状态更新（提交中->判题中）
        if (true) {
            // 普通提交
            rabbitTemplate.convertAndSend(MqConstant.KOJ_EXCHANGE,
                                          MqConstant.JUDGE_ROUTE_KEY,
                                          submissionId,
                                          message -> {
                                              message.getMessageProperties().setPriority(1);
                                              return message;
                                          });
        } else {
            // 比赛提交
            rabbitTemplate.convertAndSend(MqConstant.KOJ_EXCHANGE,
                                          MqConstant.JUDGE_ROUTE_KEY,
                                          submissionId,
                                          message -> {
                                              message.getMessageProperties().setPriority(2);
                                              return message;
                                          });
        }
    }

    public void debug(String debugId) {
        rabbitTemplate.convertAndSend(MqConstant.KOJ_EXCHANGE, MqConstant.DEBUG_ROUTE_KEY, debugId);
    }

    /**
     * 每秒查询一次队列大小并缓存
     * @return
     */
    public Integer getQueueSize() {
        try {
            Properties queueProperties = amqpAdmin.getQueueProperties(MqConstant.JUDGE_QUEUE);
            if (queueProperties != null &&
                    queueProperties.containsKey("QUEUE_MESSAGE_COUNT") &&
                    queueProperties.get("QUEUE_MESSAGE_COUNT") != null) {
                return (Integer) queueProperties.get("QUEUE_MESSAGE_COUNT");
            }
        } catch (Exception e) {
            log.error("获取队列大小失败", e);
            return 0;
        }
        return 0;
    }
}
