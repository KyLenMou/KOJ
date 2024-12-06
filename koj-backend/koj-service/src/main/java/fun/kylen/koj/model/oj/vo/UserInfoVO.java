package fun.kylen.koj.model.oj.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String role;

    private String username;

    private String avatar;

    private String titleName;

    private String titleColor;
}