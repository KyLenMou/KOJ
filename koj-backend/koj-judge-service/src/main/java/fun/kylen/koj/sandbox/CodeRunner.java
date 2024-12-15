package fun.kylen.koj.sandbox;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import fun.kylen.koj.constant.JudgeVerdictConstant;
import fun.kylen.koj.model.RunResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: KyLen
 * @Date: 2024/12/15 22:08
 * @Description:
 */
@Component
public class CodeRunner {
    @Autowired
    private SandboxRunner sandboxRunner;

    /**
     * 运行单次代码，设置评测状态、时间、空间限制，不进行判题，返回结果
     *
     * @param userInput
     * @param runArgs
     * @param runEnvs
     * @param timeLimit
     * @param memoryLimit
     * @param stackLimit
     * @param exeName
     * @param fileId
     */
    @Async("judgeAsyncExecutor")
    public CompletableFuture<RunResult> run(String userInput,
                                            List<String> runArgs,
                                            List<String> runEnvs,
                                            Integer timeLimit,
                                            Integer memoryLimit,
                                            Integer stackLimit,
                                            Integer maxOutputSize,
                                            String exeName,
                                            String fileId) {

        String threadName = Thread.currentThread().getName(); // 获取线程名称
        long threadId = Thread.currentThread().getId();       // 获取线程ID

        System.out.println("当前线程名称：" + threadName);
        System.out.println("当前线程ID：" + threadId);


        JSONArray result = sandboxRunner.run(userInput,
                                             runArgs,
                                             runEnvs,
                                             timeLimit,
                                             memoryLimit,
                                             stackLimit,
                                             maxOutputSize,
                                             exeName,
                                             fileId);
        // 获取result的评测状态，判断是否出现error
        JSONObject resultJson = (JSONObject) result.get(0);
        Integer exitCode = resultJson.getInt("exitStatus");
        Integer status = resultJson.getInt("status");
        // 单位ms
        Integer runTime = resultJson.getInt("time");
        // 单位kb
        Integer runMemory = resultJson.getInt("memory");
        String output = (String) result.getByPath("0.files.stdout");

        RunResult runResult = new RunResult(JudgeVerdictConstant.ACCEPTED,
                                            exitCode,
                                            runTime,
                                            runMemory,
                                            output,
                                            null);
        // 如果出现了非WA错误
        if (status != JudgeVerdictConstant.ACCEPTED) {
            runResult.setVerdict(status);
        }
        // 没有出现非WA错误，仍然判断时空是否超限
        else if (runTime >= timeLimit) {
            runResult.setVerdict(JudgeVerdictConstant.TIME_LIMIT_EXCEEDED);
        } else if (runMemory >= memoryLimit * 1024L) {
            runResult.setVerdict(JudgeVerdictConstant.MEMORY_LIMIT_EXCEEDED);
        }
        return CompletableFuture.completedFuture(runResult);
    }
}
