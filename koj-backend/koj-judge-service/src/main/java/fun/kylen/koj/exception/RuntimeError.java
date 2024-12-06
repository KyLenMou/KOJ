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
public class RuntimeError extends RuntimeException {
    private Long submitId;
    private String errorMessage;

    public RuntimeError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public RuntimeError(String errorMessage, Long submitId) {
        this.errorMessage = errorMessage;
        this.submitId = submitId;
    }
}
