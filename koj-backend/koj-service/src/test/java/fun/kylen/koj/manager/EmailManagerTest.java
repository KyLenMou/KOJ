package fun.kylen.koj.manager;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

/**
 * @Author: KyLen
 * @Date: 2024/8/4 11:36
 * @Description:
 */
@SpringBootTest
class EmailManagerTest {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Test
    public void f() {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost(host);
        mailAccount.setAuth(true);
        mailAccount.setUser(username);
        mailAccount.setFrom(username);
        mailAccount.setPass(password);
        mailAccount.setCharset(StandardCharsets.UTF_8);
        MailUtil.send(mailAccount,"2729269812@qq.com","我是标题","<h1>我是内容</h1>",true);
    }
}