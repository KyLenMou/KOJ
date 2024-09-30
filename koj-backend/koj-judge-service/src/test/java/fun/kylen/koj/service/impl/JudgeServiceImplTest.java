package fun.kylen.koj.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.yaml.snakeyaml.Yaml;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @Author: KyLen
 * @Date: 2024/9/18 16:09
 * @Description:
 */
@SpringBootTest
public class JudgeServiceImplTest {

    @Test
    public void f() {
        Yaml yaml = new Yaml();
        String config = ResourceUtil.readUtf8Str("language.yml");
        Iterable<Object> objects = yaml.loadAll(config);
        for (Object object : objects) {
            System.out.println(object);
        }
    }

    @Test
    public void f1() {
        String a = "1 2 3   \n     \n   ";
        System.out.println(StrUtil.trimEnd(a));
        System.out.println(StrUtil.trimEnd(a));
    }
  
}