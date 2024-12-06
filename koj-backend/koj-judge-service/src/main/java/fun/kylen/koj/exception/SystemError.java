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
public class SystemError extends RuntimeException {
    private String errorMessage;

    public SystemError(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
