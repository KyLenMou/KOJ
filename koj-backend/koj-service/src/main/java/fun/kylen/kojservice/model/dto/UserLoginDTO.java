package fun.kylen.kojservice.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: KyLen
 * @Date: 2024/7/19 下午4:28
 * @Description:
 */
@Data
public class UserLoginDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotNull(message = "记住我参数不能为空")
    private Boolean remember;
}
