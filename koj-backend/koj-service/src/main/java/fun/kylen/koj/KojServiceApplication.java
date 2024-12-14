package fun.kylen.koj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties
@MapperScan("fun.kylen.koj.mapper")
@EnableScheduling
public class KojServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KojServiceApplication.class, args);
    }

}
