package fun.kylen.koj.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: KyLen
 * @Date: 2024/7/19 下午9:35
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CompileError extends RuntimeException {
    private String errorMessage;

    public CompileError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
