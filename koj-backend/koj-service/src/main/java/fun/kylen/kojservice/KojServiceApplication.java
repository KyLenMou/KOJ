package fun.kylen.kojservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("fun.kylen.kojservice.mapper")
public class KojServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KojServiceApplication.class, args);
    }

}
