package fun.kylen.kojservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: KyLen
 * @Date: 2024/8/3 23:05
 * @Description:
 */
@AllArgsConstructor
@Getter
public enum StpEnum {
    CURRENT_USER("currentUser");

    private final String str;
}
