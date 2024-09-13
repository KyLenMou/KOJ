package fun.kylen.koj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author: KyLen
 * @Date: 2024/9/12 21:25
 * @Description:
 */
@SpringBootApplication
@MapperScan("fun.kylen.koj.mapper")
@EnableConfigurationProperties
public class KojJudgeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(KojJudgeServiceApplication.class, args);
    }
}
