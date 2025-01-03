package fun.kylen.koj.manager.oj;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @Author: KyLen
 * @Date: 2024/8/4 11:25
 * @Description:
 */
@Component
public class EmailManager {
    @Value("${mail.host}")
    private String host;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;

    public void sendRegisterCode(String to, String registerCode) {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost(host);
        mailAccount.setAuth(true);
        mailAccount.setUser(username);
        mailAccount.setFrom(username);
        mailAccount.setPass(password);
        mailAccount.setCharset(StandardCharsets.UTF_8);
        mailAccount.setSslEnable(true);
        String content = "<h3>你的注册验证码是:</h3><h1>" + registerCode + "</h1>";
        MailUtil.send(mailAccount, to, "KOJ注册邮件", content, true);
    }
}
