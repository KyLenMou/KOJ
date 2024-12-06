package fun.kylen.koj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author: KyLen
 * @Date: 2024/9/12 21:25
 * @Description: 2024-11-30 判题机支持调试（一次性调试多组样例），评测模式为全部评测（非遇错止评）
 */
// 所有@Async方法均为异步判题
@EnableAsync
@SpringBootApplication
@MapperScan("fun.kylen.koj.mapper")
@EnableConfigurationProperties
public class KojJudgeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(KojJudgeServiceApplication.class, args);
    }
}
