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

import java.util.ArrayList;
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
    @Autowired
    private CodeRunner codeRunner;

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
                                  List<Integer> maxOutputSizeList,
                                  String fileId) {
        List<String> runEnvs = languageCmdArgs.getRunEnvs();
        List<String> runArgs = JudgeUtil.getArgs(languageCmdArgs.getRunCommand());
        String exeName = languageCmdArgs.getExeName();

        List<CompletableFuture<RunResult>> completableFutureList = new ArrayList<>(userInputList.size());
        for (int i = 0; i < userInputList.size(); i++) {
            completableFutureList.add(
                    codeRunner.run(userInputList.get(i),
                        runArgs,
                        runEnvs,
                        timeLimit,
                        memoryLimit,
                        stackLimit,
                        maxOutputSizeList.get(i),
                        exeName,
                        fileId));
        }

        return completableFutureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    /**
     * 根据fileId删除文件
     *
     * @param fileId
     */
    public void delete(String fileId) {
        if (StrUtil.isEmpty(fileId)) return;
        sandboxRunner.delete(fileId);
    }
}
