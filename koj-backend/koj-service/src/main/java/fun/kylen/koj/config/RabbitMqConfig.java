package fun.kylen.koj.config;

import fun.kylen.koj.constant.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: KyLen
 * @Date: 2024/9/10 17:13
 * @Description:
 */
@Configuration
public class RabbitMqConfig {
    @Bean(MqConstant.KOJ_EXCHANGE)
    public TopicExchange kojExchange() {
        return new TopicExchange(MqConstant.KOJ_EXCHANGE,true,false);
    }
    @Bean(MqConstant.KOJ_DEAD_EXCHANGE)
    public TopicExchange kojDeadExchange() {
        return new TopicExchange(MqConstant.KOJ_DEAD_EXCHANGE,true,false);
    }

    @Bean(MqConstant.JUDGE_QUEUE)
    public Queue kojJudgeQueue() {
        Map<String, Object> args = new HashMap<>();
        // 比赛2 > 普通1
        args.put("x-max-priority", 2);
        // 死信交换机
        args.put("x-dead-letter-exchange", MqConstant.KOJ_DEAD_EXCHANGE);
        // 死信路由键
        args.put("x-dead-letter-routing-key", MqConstant.DEAD_JUDGE_ROUTE_KEY);
        return new Queue(MqConstant.JUDGE_QUEUE,true,false,false, args);
    }
    @Bean(MqConstant.DEAD_JUDGE_QUEUE)
    public Queue kojDeadLetterQueue() {
        return new Queue(MqConstant.DEAD_JUDGE_QUEUE, true);
    }
    @Bean(MqConstant.DEBUG_QUEUE)
    public Queue kojDebugQueue() {
        return new Queue(MqConstant.DEBUG_QUEUE, true, false, false);
    }

    @Bean(MqConstant.JUDGE_BINDING)
    public Binding kojJudgeBinding(@Qualifier(MqConstant.JUDGE_QUEUE) Queue kojJudgeQueue,
                                   @Qualifier(MqConstant.KOJ_EXCHANGE) TopicExchange kojExchange) {
        return BindingBuilder.bind(kojJudgeQueue).to(kojExchange).with(MqConstant.JUDGE_ROUTE_KEY);
    }
    @Bean(MqConstant.DEAD_JUDGE_BINDING)
    public Binding kojDeadJudgeBinding(@Qualifier(MqConstant.DEAD_JUDGE_QUEUE) Queue kojDeadJudgeQueue,
                                   @Qualifier(MqConstant.KOJ_DEAD_EXCHANGE) TopicExchange kojDeadExchange) {
        return BindingBuilder.bind(kojDeadJudgeQueue).to(kojDeadExchange).with(MqConstant.DEAD_JUDGE_ROUTE_KEY);
    }
    @Bean(MqConstant.DEBUG_BINDING)
    public Binding kojDebugBinding(@Qualifier(MqConstant.DEBUG_QUEUE) Queue kojDebugQueue,
                                @Qualifier(MqConstant.KOJ_EXCHANGE) TopicExchange kojExchange) {
        return BindingBuilder.bind(kojDebugQueue).to(kojExchange).with(MqConstant.DEBUG_ROUTE_KEY);
    }
}
