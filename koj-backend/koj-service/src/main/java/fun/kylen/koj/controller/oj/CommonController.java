package fun.kylen.koj.controller.oj;

import fun.kylen.koj.common.R;
import fun.kylen.koj.manager.oj.CommonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: KyLen
 * @Date: 2024/12/14 19:34
 * @Description:
 */
@RestController
public class CommonController {
    @Autowired
    private CommonManager commonManager;

    @GetMapping("/queue-size")
    public R<Integer> getQueueSize() {
        return R.ok(commonManager.getQueueSize());
    }
}
