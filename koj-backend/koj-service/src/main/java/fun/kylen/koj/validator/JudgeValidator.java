package fun.kylen.koj.validator;

import fun.kylen.koj.model.oj.dto.DebugDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/12/8 00:15
 * @Description:
 */
@Component
public class JudgeValidator {
    @Autowired
    private CommonValidator commonValidator;

    public void validateDebugDTO(DebugDTO debugDTO) {
        String language = debugDTO.getLanguage();
        this.validateCodeLanguage(language);
        String code = debugDTO.getCode();
        commonValidator.between(code, "程序代码",1, 65536);
        String judgeMode = debugDTO.getJudgeMode();
        commonValidator.mustIn(judgeMode, "评测模式", "default", "special", "interact");
        Integer timeLimit = debugDTO.getTimeLimit();
        commonValidator.between(timeLimit, "时间限制", 1000, 10000);
        Integer memoryLimit = debugDTO.getMemoryLimit();
        commonValidator.between(memoryLimit, "内存限制", 64, 1024);
        Integer stackLimit = debugDTO.getStackLimit();
        commonValidator.between(stackLimit, "栈限制", 64, 1024);
        List<String> userInputList = debugDTO.getUserInputList();
        // 输入可以为空字符串""，但是不能为null，否则在构造沙箱参数时会出现问题
        userInputList.forEach(u -> commonValidator.between(u, "输入", 0, 1024));
        List<String> expectedOutputList = debugDTO.getExpectedOutputList();
        expectedOutputList.forEach(e -> commonValidator.between(e, "输出", 0, 1024));
        // 两者大小必须相等
        commonValidator.mustBeSame(userInputList.size(), expectedOutputList.size(), "输入与输出数据大小");
        // 最多调试五次
        commonValidator.between(userInputList.size(), "测试用例数量", 1, 5);
    }

    public void validateCodeLanguage(String language) {
        commonValidator.mustIn(language, "编程语言", "c", "cpp", "java", "python", "go");
    }
}
