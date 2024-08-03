package fun.kylen.kojservice.common;

/**
 * @Author: KyLen
 * @Date: 2024/7/19 下午3:57
 * @Description:
 */
public class R<T> {
    public Integer code;
    public String message;
    public T data;

    public static <T> R<T> ok() {
        return new R<>(ResultEnum.SUCCESS.getCode(), "", null);
    }
    public static <T> R<T> ok(String message) {
        return new R<>(ResultEnum.SUCCESS.getCode(), message, null);
    }
    public static <T> R<T> ok(T data) {
        return new R<>(ResultEnum.SUCCESS.getCode(), "", data);
    }
    public static <T> R<T> ok(T data, String message) {
        return new R<>(ResultEnum.SUCCESS.getCode(), message, data);
    }
    public static <T> R<T> fail(String message) {
        return new R<>(ResultEnum.FAIL.getCode(), message, null);
    }
    public static <T> R<T> fail(ResultEnum resultEnum) {
        return new R<>(resultEnum.getCode(), resultEnum.getDefaultMessage(), null);
    }
    public static <T> R<T> fail(Integer code, String message) {
        return new R<>(code, message, null);
    }

    public R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
