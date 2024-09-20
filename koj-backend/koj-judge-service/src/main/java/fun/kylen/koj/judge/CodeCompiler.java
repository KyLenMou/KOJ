package fun.kylen.koj.judge;

import cn.hutool.json.JSONArray;
import fun.kylen.koj.model.LanguageCmdArgs;
import fun.kylen.koj.sandbox.SandboxRunner;
import fun.kylen.koj.utils.JudgeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: KyLen
 * @Date: 2024/9/14 16:34
 * @Description:
 */
@Component
public class CodeCompiler {
    @Autowired
    private SandboxRunner sandboxRunner;
    @Resource
    private Map<String, LanguageCmdArgs> languageMap;

    /**
     * 返回编译完成的相关信息
     * @param language
     * @param code
     * @return
     */
    public String compile(String language, String code) {
        LanguageCmdArgs languageCmdArgs = languageMap.get(language);

        // todo 检查语言是否存在

        JSONArray compileResult = sandboxRunner.compile(languageCmdArgs.getMaxCpuTime(),
                languageCmdArgs.getMaxRealTime(),
                languageCmdArgs.getMaxMemory(),
                256 * 1024 * 1024L,
                languageCmdArgs.getSrcName(),
                languageCmdArgs.getExeName(),
                JudgeUtil.getArgs(languageCmdArgs.getCompileCommand()),
                languageCmdArgs.getCompileEnvs(),
                code,
                null,
                true,
                false,
                null
        );

        return compileResult.getByPath("0.fileIds.main").toString();
    }

}
