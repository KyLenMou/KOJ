package fun.kylen.koj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: KyLen
 * @Date: 2024/12/8 14:36
 * @Description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    enum Type {
        DEBUG,
        SUBMIT
    }
    Type value();
}
