package fun.kylen.koj.manager;

import fun.kylen.koj.sandbox.SandboxRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/9/21 10:27
 * @Description:
 */
@Component
public class JudgeManager {
    @Autowired
    private SandboxRunner sandboxRunner;
    public void judgeAllCases(List<String> inputList,
                              List<String> outputList,
                              Integer timeLimit,
                              Integer memoryLimit,
                              Integer stackLimit) {

    }
}
