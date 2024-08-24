package fun.kylen.koj.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @Author: KyLen
 * @Date: 2024/7/19 下午4:28
 * @Description:
 */
@Data
public class UserRegisterDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
    @Email
    private String email;
    @NotBlank(message = "验证码不能为空")
    private String code;
}
