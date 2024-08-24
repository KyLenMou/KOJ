package fun.kylen.koj;

import fun.kylen.koj.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KojServiceApplicationTests {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() throws InterruptedException {
        // redisUtil.set("f","f2",21300);
        String f = redisUtil.getStr("fffff");
        System.out.println(f);
        // Thread.sleep(3000);
        // String f1 = redisUtil.getString("f");
        // System.out.println(f1);
    }

}
