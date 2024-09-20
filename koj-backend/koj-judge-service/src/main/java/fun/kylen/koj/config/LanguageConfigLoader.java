package fun.kylen.koj.config;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import fun.kylen.koj.model.LanguageCmdArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: KyLen
 * @Date: 2024/9/18 21:22
 * @Description:
 */
@Configuration
public class LanguageConfigLoader {

    private final static List<String> defaultEnv = Arrays.asList(
            "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin",
            "LANG=en_US.UTF-8",
            "LC_ALL=en_US.UTF-8",
            "LANGUAGE=en_US:en",
            "HOME=/w");

    private final static List<String> python3Env = Arrays.asList("LANG=en_US.UTF-8",
            "LANGUAGE=en_US:en", "LC_ALL=en_US.UTF-8", "PYTHONIOENCODING=utf-8");

    private final static List<String> golangCompileEnv = Arrays.asList(
            "GOCACHE=/w", "GOPATH=/w/go", "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin",
            "LANG=en_US.UTF-8", "LANGUAGE=en_US:en", "LC_ALL=en_US.UTF-8");

    private final static List<String> golangRunEnv = Arrays.asList(
            "GOCACHE=off", "GODEBUG=madvdontneed=1",
            "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin",
            "LANG=en_US.UTF-8", "LANGUAGE=en_US:en", "LC_ALL=en_US.UTF-8");

    @Bean
    public Map<String, LanguageCmdArgs> languageCmdArgsMap() {
        Map<String, LanguageCmdArgs> languageCmdArgsMap = new HashMap<>();
        Yaml yaml = new Yaml();
        String ymlStr = ResourceUtil.readUtf8Str("language.yml");
        yaml.loadAll(ymlStr).forEach(obj -> {
            JSONObject jsonObject = JSONUtil.parseObj(obj);
            LanguageCmdArgs languageCmdArgs = buildLanguageConfig(jsonObject);
            languageCmdArgsMap.put(languageCmdArgs.getLanguage(), languageCmdArgs);
        });
        return languageCmdArgsMap;
    }
    private LanguageCmdArgs buildLanguageConfig(JSONObject configJson) {
        LanguageCmdArgs languageCmdArgs = new LanguageCmdArgs();
        languageCmdArgs.setLanguage(configJson.getStr("language"));
        languageCmdArgs.setSrcName(configJson.getStr("src_path"));
        languageCmdArgs.setExeName(configJson.getStr("exe_path"));

        JSONObject compileJson = configJson.getJSONObject("compile");
        if (compileJson != null) {
            String command = compileJson.getStr("command");
            command = command.replace("{src_path}", languageCmdArgs.getSrcName())
                    .replace("{exe_path}", languageCmdArgs.getExeName());
            languageCmdArgs.setCompileCommand(command);
            String env = compileJson.getStr("env");
            env = env.toLowerCase();
            switch (env) {
                case "python3":
                    languageCmdArgs.setCompileEnvs(python3Env);
                    break;
                case "golang_compile":
                    languageCmdArgs.setCompileEnvs(golangCompileEnv);
                    break;
                default:
                    languageCmdArgs.setCompileEnvs(defaultEnv);
            }
            languageCmdArgs.setMaxCpuTime(parseTimeStr(compileJson.getStr("maxCpuTime")));
            languageCmdArgs.setMaxRealTime(parseTimeStr(compileJson.getStr("maxRealTime")));
            languageCmdArgs.setMaxMemory(parseMemoryStr(compileJson.getStr("maxMemory")));
        }

        JSONObject runJson = configJson.getJSONObject("run");
        if (runJson != null) {
            String command = runJson.getStr("command");
            command = command.replace("{exe_path}", languageCmdArgs.getExeName());
            languageCmdArgs.setRunCommand(command);
            String env = runJson.getStr("env");
            env = env.toLowerCase();
            switch (env) {
                case "python3":
                    languageCmdArgs.setRunEnvs(python3Env);
                    break;
                case "golang_run":
                    languageCmdArgs.setRunEnvs(golangRunEnv);
                    break;
                default:
                    languageCmdArgs.setRunEnvs(defaultEnv);
            }
        }
        return languageCmdArgs;
    }

    private Long parseTimeStr(String timeStr) {
        if (StrUtil.isBlank(timeStr)) {
            return 5000L;
        }
        timeStr = timeStr.toLowerCase();
        if (timeStr.endsWith("s")) {
            return Long.parseLong(timeStr.replace("s", "")) * 1000;
        } else if (timeStr.endsWith("ms")) {
            return Long.parseLong(timeStr.replace("s", ""));
        } else {
            return Long.parseLong(timeStr);
        }
    }

    private Long parseMemoryStr(String memoryStr) {
        if (StrUtil.isBlank(memoryStr)) {
            return 256 * 1024 * 1024L;
        }
        memoryStr = memoryStr.toLowerCase();
        if (memoryStr.endsWith("mb")) {
            return Long.parseLong(memoryStr.replace("mb", "")) * 1024 * 1024;
        } else if (memoryStr.endsWith("kb")) {
            return Long.parseLong(memoryStr.replace("kb", "")) * 1024;
        } else if (memoryStr.endsWith("b")) {
            return Long.parseLong(memoryStr.replace("b", ""));
        } else {
            return Long.parseLong(memoryStr) * 1024 * 1024;
        }
    }
}
