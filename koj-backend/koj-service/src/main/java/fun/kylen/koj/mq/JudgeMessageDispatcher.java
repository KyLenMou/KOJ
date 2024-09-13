package fun.kylen.koj.mq;

import fun.kylen.koj.constant.MqConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: KyLen
 * @Date: 2024/9/10 14:00
 * @Description:
 */
@Component
public class JudgeMessageDispatcher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void dispatch(Long id) {
        // 普通提交
        // todo 提交状态更新（提交中->判题中）
        if (true) {
            rabbitTemplate.convertAndSend(MqConstant.KOJ_EXCHANGE, MqConstant.JUDGE_ROUTE_KEY, id, message -> {
                message.getMessageProperties().setPriority(2);
                return message;
            });
        } else {
            // 比赛提交
        }
    }
}
