package fun.kylen.koj.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user_info
 */
@TableName(value ="user_info")
@Data
public class UserInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * qq号码
     */
    private String qq;

    /**
     * github用户名
     */
    private String githubUsername;

    /**
     * githubId
     */
    private String githubId;

    /**
     * oj分数
     */
    private Integer rating;

    /**
     * 性别
     */
    private String gender;

    /**
     * 学校
     */
    private String school;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 博客地址
     */
    private String blogUrl;

    /**
     * cf的username
     */
    private String cfUsername;

    /**
     * leetcode的username
     */
    private String leetcodeUsername;

    /**
     * 牛客id
     */
    private String nowcoderId;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 头衔、称号
     */
    private String titleName;

    /**
     * 头衔、称号的颜色
     */
    private String titleColor;

    /**
     * 是否被封禁
     */
    private Integer isBanned;

    /**
     * 用户角色 root超级管理员、admin普通管理员、default_user普通用户
     */
    private String role;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}