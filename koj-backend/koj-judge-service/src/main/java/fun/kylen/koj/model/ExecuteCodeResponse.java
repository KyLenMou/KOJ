package fun.kylen.koj.model;

import lombok.Data;

import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/9/13 16:11
 * @Description:
 */
@Data
public class ExecuteCodeResponse {
    private Integer status;
    private List<String> outputList;
    private String message;

    private Integer time;
    private Integer memory;
    private Integer stack;
}
