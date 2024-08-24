package fun.kylen.koj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("fun.kylen.koj.mapper")
@EnableConfigurationProperties
public class KojServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KojServiceApplication.class, args);
    }

}
