package fun.kylen.koj.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: KyLen
 * @Date: 2024/11/30 15:45
 * @Description:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理业务逻辑
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public void handleBusinessException(SystemError e, HttpServletResponse response) {
        // todo 之后再根据业务需要创建不同的处理机制和返回状态码
        // response.setStatus();
    }

}
