package fun.kylen.koj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: KyLen
 * @Date: 2024/10/16 14:38
 * @Description:
 */
@Configuration
public class RestTemplateConfig {

    @Bean(name = "oAuthRestTemplate")
    public RestTemplate oAuthRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(30000);
        factory.setReadTimeout(30000);
        return new RestTemplate(factory);
    }
}
