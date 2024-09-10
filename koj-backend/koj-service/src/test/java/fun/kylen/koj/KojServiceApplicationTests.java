package fun.kylen.koj;

import fun.kylen.koj.constant.MqConstant;
import fun.kylen.koj.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
class KojServiceApplicationTests {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void mqTest() throws InterruptedException {
        String[] strings = {"=","==","===","====","====="};
        CompletableFuture.runAsync(() -> {
            for (int i = 1; ; i = (i + 1) % 5) {
                rabbitTemplate.convertAndSend(MqConstant.KOJ_EXCHANGE,MqConstant.JUDGE_ROUTE_KEY, "message1: " + strings[i]);
            }
        });
        while (true) {

        }
    }
}
