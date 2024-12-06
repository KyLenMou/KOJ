package fun.kylen.koj.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: KyLen
 * @Date: 2024/9/21 10:40
 * @Description:
 */
@Data
@AllArgsConstructor
public class RunResult {
    private Integer verdict;
    private Integer exitCode;
    private Integer runTime;
    private Integer runMemory;
    private String userOutput;
    private String message;
}
