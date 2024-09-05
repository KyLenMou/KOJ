package fun.kylen.koj.manager;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.UserInfoEntityService;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.UserInfo;
import fun.kylen.koj.mapper.ProblemMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ProblemMapper problemEntityService;
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

    @Test
    public void f1() {
        System.out.println(problemEntityService.listProblemsetVOByPage( new Page<>(1, 10)).getRecords().toString());
    }
}