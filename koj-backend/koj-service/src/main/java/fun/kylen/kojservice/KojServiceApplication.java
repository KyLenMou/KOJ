package fun.kylen.kojservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("fun.kylen.kojservice.mapper")
@EnableConfigurationProperties
public class KojServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KojServiceApplication.class, args);
    }

}
