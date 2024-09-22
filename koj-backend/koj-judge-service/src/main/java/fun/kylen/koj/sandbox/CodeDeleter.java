package fun.kylen.koj.sandbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: KyLen
 * @Date: 2024/9/20 22:29
 * @Description:
 */
@Component
public class CodeDeleter {
    @Autowired
    private SandboxRunner sandboxRunner;

    public void delete(String fileId) {
        sandboxRunner.delete(fileId);
    }
}
