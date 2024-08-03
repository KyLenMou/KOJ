package fun.kylen.kojservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: KyLen
 * @Date: 2024/8/3 21:55
 * @Description:
 */
@AllArgsConstructor
@Getter
public enum UserRoleCodeEnum {
    ROOT(1001L),
    DEFAULT_USER(1002L);

    private final Long id;
}
