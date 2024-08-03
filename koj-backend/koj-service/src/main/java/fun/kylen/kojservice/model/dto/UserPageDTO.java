package fun.kylen.kojservice.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: KyLen
 * @Date: 2024/7/20 15:07
 * @Description:
 */
@Data
public class UserPageDTO extends PageDTO {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户角色 0管理员 1用户
     */
    private Integer role;

    /**
     * 用户头像url
     */
    private String avatar;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户qq
     */
    private String qq;

    /**
     * 用户简介
     */
    private String profile;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
