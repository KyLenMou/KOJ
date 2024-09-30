package fun.kylen.koj.common;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

/**
 * @Author: KyLen
 * @Date: 2024/7/19 下午9:56
 * @Description:
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理业务逻辑
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler(value = {BusinessException.class})
    public R<Void> handleBusinessException(BusinessException e, HttpServletResponse response) {
        // todo 之后再根据业务需要创建不同的处理机制和返回状态码
        // response.setStatus();
        return R.fail(e.getCode(), e.getBusinessMessage());
    }

    /**
     * sa-token 未登录
     * @param e
     * @return
     */
    @ExceptionHandler(value = {NotLoginException.class})
    public R<Void> handleNotLoginException(NotLoginException e) {
        log.error(e.getMessage());
        return R.fail(ResultEnum.NOT_LOGIN);
    }

    /**
     * sa-token 无权限
     * @param e
     * @return
     */
    @ExceptionHandler(value = {NotRoleException.class})
    public R<Void> handleNotRoleException(NotRoleException e) {
        log.error(e.getMessage());
        return R.fail(ResultEnum.ACCESS_DENIED);
    }

    /**
     * validation 请求体校验
     * @param e
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public R<Void> handleArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        if (result.hasErrors()) {
            return R.fail(ResultEnum.PARAMS_ERROR.getCode(), result.getAllErrors().get(0).getDefaultMessage());
        }
        return R.fail(ResultEnum.PARAMS_ERROR);
    }

    /**
     * validation 请求参数校验
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public R<Void> handleArgumentNotValidException(ConstraintViolationException e) {
        // todo 这里不要使用get(0)的方式，不优雅，使用findfirst
        String message = new ArrayList<>(e.getConstraintViolations()).get(0).getMessage();
        return R.fail(ResultEnum.PARAMS_ERROR.getCode(), message);
    }

    /**
     * 其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public R<Void> handleException(Exception e) {
        log.error(e.getMessage());
        return R.fail(ResultEnum.FAIL);
    }
}
