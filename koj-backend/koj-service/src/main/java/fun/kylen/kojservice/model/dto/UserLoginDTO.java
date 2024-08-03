package fun.kylen.kojservice.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

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
}
