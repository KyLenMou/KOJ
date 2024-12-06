package fun.kylen.koj.sandbox;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import fun.kylen.koj.constant.JudgeVerdictConstant;
import fun.kylen.koj.exception.SystemError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: KyLen
 * @Date: 2024/9/14 16:06
 * @Description:
 */
@Component
@Slf4j
public class SandboxRunner {
    @Value("${sandbox.url}")
    private String SANDBOX_URL;
    @Autowired
    private RestTemplate sandboxRequester;

    /**
     * 调用go-judge执行各种命令
     * todo 查看gRPC如何调用
     *
     * @param cmd
     * @param extraUrl
     * @return
     */
    private JSONArray goJudge(JSONObject cmd, String extraUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(JSONUtil.toJsonStr(cmd), headers);
        ResponseEntity<String> postForEntity;
        try {
            postForEntity = sandboxRequester.postForEntity(SANDBOX_URL + extraUrl, request, String.class);
            return JSONUtil.parseArray(postForEntity.getBody());
        } catch (RestClientResponseException ex) {
            if (ex.getRawStatusCode() != 200) {
                // 无法连接
                throw new SystemError("无法连接到代码沙箱");
            }
            throw new SystemError("连接代码沙箱出错");
        } catch (Exception e) {
            throw new SystemError("连接代码沙箱出错");
        }
    }

    /**
     * 只进行编译代码的操作，无论什么结果，只返回，不做任何处理
     *
     * @param maxCpuTime
     * @param maxRealTime
     * @param maxMemory
     * @param maxStack
     * @param srcName
     * @param exeName
     * @param args
     * @param envs
     * @param code
     * @param extraFiles
     * @param needCopyOutCached
     * @param needCopyOutExe
     * @param copyOutDir
     * @return
     */
    public JSONObject compile(Long maxCpuTime,
                              Long maxRealTime,
                              Long maxMemory,
                              Long maxStack,
                              String srcName,
                              String exeName,
                              List<String> args,
                              List<String> envs,
                              String code,
                              HashMap<String, String> extraFiles,
                              Boolean needCopyOutCached,
                              Boolean needCopyOutExe,
                              String copyOutDir) {
        JSONObject cmd = new JSONObject();
        cmd.set("args", args);
        cmd.set("env", envs);
        cmd.set("files", COMPILE_FILES);
        // ms-->ns
        cmd.set("cpuLimit", maxCpuTime * 1000 * 1000L);
        cmd.set("clockLimit", maxRealTime * 1000 * 1000L);
        // byte
        cmd.set("memoryLimit", maxMemory);
        cmd.set("procLimit", maxProcessNumber);
        cmd.set("stackLimit", maxStack);

        JSONObject fileContent = new JSONObject();
        fileContent.set("content", code);

        JSONObject copyIn = new JSONObject();
        copyIn.set(srcName, fileContent);

        if (extraFiles != null) {
            for (Map.Entry<String, String> entry : extraFiles.entrySet()) {
                if (!StrUtil.isEmpty(entry.getKey()) && !StrUtil.isEmpty(entry.getValue())) {
                    JSONObject content = new JSONObject();
                    content.set("content", entry.getValue());
                    copyIn.set(entry.getKey(), content);
                }
            }
        }

        cmd.set("copyIn", copyIn);
        cmd.set("copyOut", new JSONArray().put("stdout").put("stderr"));

        if (needCopyOutCached) {
            cmd.set("copyOutCached", new JSONArray().put(exeName));
        }

        if (needCopyOutExe) {
            cmd.set("copyOutDir", copyOutDir);
        }

        JSONObject param = new JSONObject();
        param.set("cmd", new JSONArray().put(cmd));

        JSONArray result = this.goJudge(param, "/run");
        JSONObject compileRes = (JSONObject) result.get(0);
        compileRes.set("originalStatus", compileRes.getStr("status"));
        compileRes.set("status", RESULT_MAP_STATUS.get(compileRes.getStr("status")));
        return compileRes;
    }

    /**
     * 根据参数调用go-judge
     * 只运行代码，返回结果，不做评测
     *
     * @param args
     * @param envs
     * @param input
     * @param maxTime   单位 ms
     * @param maxMemory 单位 mb
     * @param maxStack
     * @param exeName
     * @param fileId
     * @return 返回的时间单位为 ms，内存单位为 kb
     */
    public JSONArray run(String input,
                         List<String> args,
                         List<String> envs,
                         Integer maxTime,
                         Integer maxMemory,
                         Integer maxStack,
                         String exeName,
                         String fileId) {

        JSONObject cmd = new JSONObject();
        cmd.set("args", args);
        cmd.set("env", envs);

        JSONArray files = new JSONArray();

        JSONObject testCaseInput = new JSONObject();
        testCaseInput.set("content", input);
        files.put(testCaseInput);

        JSONObject stdout = new JSONObject();
        stdout.set("name", "stdout");
        stdout.set("max", MAX_OUTPUT_SIZE);
        files.put(stdout);

        JSONObject stderr = new JSONObject();
        stderr.set("name", "stderr");
        stderr.set("max", 1024);
        files.put(stderr);

        cmd.set("files", files);

        // ms --> ns
        cmd.set("cpuLimit", maxTime * 1000 * 1000L);
        cmd.set("clockLimit", maxTime * 1000 * 1000L * 3);
        // mb --> byte
        cmd.set("memoryLimit", (maxMemory + 100) * 1024 * 1024L);
        cmd.set("procLimit", maxProcessNumber);
        cmd.set("stackLimit", maxStack * 1024 * 1024L);

        JSONObject exeFile = new JSONObject();
        exeFile.set("fileId", fileId);
        JSONObject copyIn = new JSONObject();
        copyIn.set(exeName, exeFile);

        // JSONArray copyOut = new JSONArray();
        // copyOut.put("stderr");
        // if (BooleanUtils.isFalse(isFileIO)){
        //     copyOut.put("stdout");
        // }else{
        //     copyIn.set(ioReadFileName, testCaseInput);
        //     // 在文件名之后加入 '?' 来使文件变为可选，可选文件不存在的情况不会触发 FileError
        //     copyOut.put(ioWriteFileName + "?");
        // }
        // 0
        cmd.set("copyIn", copyIn);
        // cmd.set("copyOut", copyOut);

        JSONObject param = new JSONObject();
        param.set("cmd", new JSONArray().put(cmd));

        // 调用判题安全沙箱
        JSONArray result = this.goJudge(param, "/run");

        JSONObject testcaseRes = (JSONObject) result.get(0);
        testcaseRes.set("originalStatus", testcaseRes.getStr("status"));
        testcaseRes.set("status", RESULT_MAP_STATUS.get(testcaseRes.getStr("status")));
        // 转化为ms和kb
        testcaseRes.set("time", testcaseRes.getLong("time") / 1000000L);
        testcaseRes.set("memory", testcaseRes.getLong("memory") / 1024L);
        return result;

    }

    /**
     * 根据fileId删除已经编译好的代码
     *
     * @param fileId
     */
    public void delete(String fileId) {
        try {
            sandboxRequester.delete(SANDBOX_URL + "/file/{0}", fileId);
        } catch (RestClientResponseException ex) {
            if (ex.getRawStatusCode() != 200) {
                log.error("安全沙箱判题的删除内存中的文件缓存操作异常：{}", ex.getResponseBodyAsString());
            }
        }
    }


    private static final int maxProcessNumber = 128;

    private static final int TIME_LIMIT_MS = 10000;

    private static final int MEMORY_LIMIT_MB = 512;

    private static final int MAX_OUTPUT_SIZE = 32 * 1024 * 1024;

    public static final HashMap<String, Integer> RESULT_MAP_STATUS = new HashMap<>();

    static {
        RESULT_MAP_STATUS.put("Time Limit Exceeded", JudgeVerdictConstant.TIME_LIMIT_EXCEEDED);
        RESULT_MAP_STATUS.put("Memory Limit Exceeded", JudgeVerdictConstant.MEMORY_LIMIT_EXCEEDED);
        RESULT_MAP_STATUS.put("Output Limit Exceeded", JudgeVerdictConstant.OUTPUT_LIMIT_EXCEEDED);
        RESULT_MAP_STATUS.put("Accepted", JudgeVerdictConstant.ACCEPTED);
        RESULT_MAP_STATUS.put("Nonzero Exit Status", JudgeVerdictConstant.RUNTIME_ERROR);
        RESULT_MAP_STATUS.put("Internal Error", JudgeVerdictConstant.SYSTEM_ERROR);
        RESULT_MAP_STATUS.put("File Error", JudgeVerdictConstant.SYSTEM_ERROR);
        RESULT_MAP_STATUS.put("Signalled", JudgeVerdictConstant.RUNTIME_ERROR);
    }


    private static final JSONArray COMPILE_FILES = new JSONArray();

    static {
        JSONObject content = new JSONObject();
        content.set("content", "");

        JSONObject stdout = new JSONObject();
        stdout.set("name", "stdout");
        stdout.set("max", MAX_OUTPUT_SIZE);

        JSONObject stderr = new JSONObject();
        stderr.set("name", "stderr");
        stderr.set("max", 1024);
        COMPILE_FILES.put(content);
        COMPILE_FILES.put(stdout);
        COMPILE_FILES.put(stderr);
    }
}
