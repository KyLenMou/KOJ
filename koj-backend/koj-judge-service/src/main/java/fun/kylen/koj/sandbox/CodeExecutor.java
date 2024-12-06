package fun.kylen.koj.sandbox;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import fun.kylen.koj.constant.JudgeVerdictConstant;
import fun.kylen.koj.exception.CompileError;
import fun.kylen.koj.exception.SystemError;
import fun.kylen.koj.model.LanguageCmdArgs;
import fun.kylen.koj.model.RunResult;
import fun.kylen.koj.utils.JudgeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @Author: KyLen
 * @Date: 2024/11/30 15:57
 * @Description:
 */
@Component
@Slf4j
public class CodeExecutor {
    @Autowired
    private SandboxRunner sandboxRunner;

    /**
     * 编译代码，返回编译完成的文件在沙箱内的fileId
     *
     * @param args
     * @return
     */
    public String compile(LanguageCmdArgs args, String code) {
        JSONObject compileResult = sandboxRunner.compile(args.getMaxCpuTime(),
                                                         args.getMaxRealTime(),
                                                         args.getMaxMemory(),
                                                         256 * 1024 * 1024L,
                                                         args.getSrcName(),
                                                         args.getExeName(),
                                                         JudgeUtil.getArgs(args.getCompileCommand()),
                                                         args.getCompileEnvs(),
                                                         code,
                                                         null,
                                                         true,
                                                         false,
                                                         null
        );

        if (compileResult.getInt("status") != JudgeVerdictConstant.ACCEPTED) {
            throw new CompileError((String) compileResult.getByPath("files.stderr"));
        }

        String fileId = ((JSONObject) compileResult.get("fileIds")).getStr(args.getExeName());
        if (StrUtil.isEmpty(fileId)) {
            throw new SystemError("编译时出现了错误，请联系管理员");
        }
        return fileId;
    }


    /**
     * 对输入列表一一运行
     *
     * @param languageCmdArgs
     * @param userInputList
     * @param timeLimit
     * @param memoryLimit
     * @param stackLimit
     * @param fileId
     */
    public List<RunResult> runAll(LanguageCmdArgs languageCmdArgs,
                                  List<String> userInputList,
                                  Integer timeLimit,
                                  Integer memoryLimit,
                                  Integer stackLimit,
                                  String fileId) {
        List<String> runEnvs = languageCmdArgs.getRunEnvs();
        List<String> runArgs = JudgeUtil.getArgs(languageCmdArgs.getRunCommand());
        String exeName = languageCmdArgs.getExeName();

        List<CompletableFuture<RunResult>> completableFutureList = userInputList.stream().map(
                userInput -> run(userInput,
                                 runArgs,
                                 runEnvs,
                                 timeLimit,
                                 memoryLimit,
                                 stackLimit,
                                 exeName,
                                 fileId)
        ).collect(Collectors.toList());

        return completableFutureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

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
    @Async
    public CompletableFuture<RunResult> run(String userInput,
                                            List<String> runArgs,
                                            List<String> runEnvs,
                                            Integer timeLimit,
                                            Integer memoryLimit,
                                            Integer stackLimit,
                                            String exeName,
                                            String fileId) {

        JSONArray result = sandboxRunner.run(userInput,
                                             runArgs,
                                             runEnvs,
                                             timeLimit,
                                             memoryLimit,
                                             stackLimit,
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

    /**
     * 根据fileId删除文件
     * @param fileId
     */
    public void delete(String fileId) {
        if (StrUtil.isEmpty(fileId)) return;
        sandboxRunner.delete(fileId);
    }
}
