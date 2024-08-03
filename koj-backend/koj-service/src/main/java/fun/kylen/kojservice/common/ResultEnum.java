package fun.kylen.kojservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: KyLen
 * @Date: 2024/7/19 下午4:02
 * @Description:
 */
@AllArgsConstructor
@Getter
public enum ResultEnum {

    SUCCESS(0,"成功"),

    FAIL(-1,"失败"),

    PARAMS_ERROR(400,"请求参数错误"),

    ACCESS_DENIED(401,"暂无权限"),

    NOT_LOGIN(402,"未登录"),

    FORBIDDEN(403,"拒绝访问"),

    NOT_FOUND(404,"数据不存在"),

    SYSTEM_ERROR(500,"系统错误");

    private final int code;
    private final String defaultMessage;
}
