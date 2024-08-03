package fun.kylen.kojservice.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: KyLen
 * @Date: 2024/7/19 下午9:35
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    private int code;
    private String businessMessage;

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getDefaultMessage());
        this.code = resultEnum.getCode();
        this.businessMessage = resultEnum.getDefaultMessage();
    }

    public BusinessException(ResultEnum resultEnum, String message) {
        super(message);
        this.code = resultEnum.getCode();
        this.businessMessage = message;
    }
}
