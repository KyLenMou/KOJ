package fun.kylen.koj.sandbox;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import fun.kylen.koj.constant.JudgeStatusConstant;
import fun.kylen.koj.model.LanguageCmdArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: KyLen
 * @Date: 2024/9/14 16:06
 * @Description:
 */
@Component
public class SandboxRunner {
    @Value("${sandbox.url}")
    private String SANDBOX_URL;
    @Autowired
    private RestTemplate sandboxRequester;

    @Resource(name = "languageCmdArgsMap")
    private Map<String, LanguageCmdArgs> languageCmdArgsMap;

    private static final int maxProcessNumber = 128;

    private static final int TIME_LIMIT_MS = 16000;

    private static final int MEMORY_LIMIT_MB = 512;

    private static final int STACK_LIMIT_MB = 128;

    private static final int STDIO_SIZE_MB = 32;

    public static final HashMap<String, Integer> RESULT_MAP_STATUS = new HashMap<>();

    static {
        RESULT_MAP_STATUS.put("Time Limit Exceeded", JudgeStatusConstant.TIME_LIMIT_EXCEEDED);
        RESULT_MAP_STATUS.put("Memory Limit Exceeded", JudgeStatusConstant.MEMORY_LIMIT_EXCEEDED);
        RESULT_MAP_STATUS.put("Output Limit Exceeded", JudgeStatusConstant.RUNTIME_ERROR);
        RESULT_MAP_STATUS.put("Accepted", JudgeStatusConstant.ACCEPTED);
        RESULT_MAP_STATUS.put("Nonzero Exit Status", JudgeStatusConstant.RUNTIME_ERROR);
        RESULT_MAP_STATUS.put("Internal Error", JudgeStatusConstant.SYSTEM_ERROR);
        RESULT_MAP_STATUS.put("File Error", JudgeStatusConstant.SYSTEM_ERROR);
        RESULT_MAP_STATUS.put("Signalled", JudgeStatusConstant.RUNTIME_ERROR);
    }


    private static final JSONArray COMPILE_FILES = new JSONArray();

    static {
        JSONObject content = new JSONObject();
        content.set("content", "");

        JSONObject stdout = new JSONObject();
        stdout.set("name", "stdout");
        stdout.set("max", 1024 * 1024 * STDIO_SIZE_MB);

        JSONObject stderr = new JSONObject();
        stderr.set("name", "stderr");
        stderr.set("max", 1024 * 1024 * STDIO_SIZE_MB);
        COMPILE_FILES.put(content);
        COMPILE_FILES.put(stdout);
        COMPILE_FILES.put(stderr);
    }

    /**
     * go-judge核心方法run
     * 调用go-judge执行各种命令
     * @param cmd
     * @param extraUrl
     * @return
     */
    private JSONArray run(JSONObject cmd, String extraUrl) {
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
                throw new RuntimeException("Cannot connect to sandbox service.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Call SandBox Error.");
        }
        return null;
    }

    // 参数language改成一个命令集合，
    // 用Spring个的工具读取yml文件里的内容并转化成map
    public JSONArray compile(Long maxCpuTime,
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
                if (!StringUtils.isEmpty(entry.getKey()) && !StringUtils.isEmpty(entry.getValue())) {
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

        JSONArray result = this.run(param,"/run");
        JSONObject compileRes = (JSONObject) result.get(0);
        compileRes.set("originalStatus", compileRes.getStr("status"));
        compileRes.set("status", RESULT_MAP_STATUS.get(compileRes.getStr("status")));
        return result;
    }
}
