package fun.kylen.koj.model.oj.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class DebugDTO {
    /**
     * 代码语言
     */
    @NotBlank(message = "代码语言不能为空")
    private String language;

    /**
     * 代码
     */
    @NotBlank(message = "代码不能为空")
    private String code;

    /**
     * 评测模式
     */
    @NotNull(message = "评测模式不能为空")
    private String judgeMode;

    /**
     * 时间限制
     */
    @NotNull(message = "时间限制不能为空")
    private Integer timeLimit;

    /**
     * 内存限制
     */
    @NotNull(message = "内存限制不能为空")
    private Integer memoryLimit;

    /**
     * 内存限制
     */
    @NotNull(message = "栈限制不能为空")
    private Integer stackLimit;

    /**
     * 用户输入
     */
    private List<String> userInputList;

    /**
     * 期望输出
     */
    private List<String> expectedOutput;
}