package fun.kylen.koj.config;

import cn.hutool.json.JSONUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: KyLen
 * @Date: 2024/9/14 16:46
 * @Description:
 */
@Configuration
public class RestTemplateConfig {
    // todo 永远只请求一个地址，请求打到网关上做复杂均衡
    // todo excel导出，pdf导出题目
    @Bean
    public RestTemplate sandboxRequester() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(20000);
        requestFactory.setReadTimeout(180000);
        return new RestTemplate(requestFactory);
    }
}