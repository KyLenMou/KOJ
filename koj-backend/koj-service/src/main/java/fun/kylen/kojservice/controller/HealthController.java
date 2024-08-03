package fun.kylen.kojservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: KyLen
 * @Date: 2024/8/3 19:13
 * @Description:
 */
@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping
    public String getHealth() {
        return "ok\ntime: " + System.currentTimeMillis();
    }
}
