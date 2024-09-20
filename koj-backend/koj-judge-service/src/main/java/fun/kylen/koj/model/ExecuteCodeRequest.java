package fun.kylen.koj.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/9/13 16:11
 * @Description:
 */
@Data
@Builder
public class ExecuteCodeRequest {
    private String code;
    private String language;
    private List<String> inputList;

    private Integer timeLimit;
    private Integer memoryLimit;
    private Integer stackLimit;
}
