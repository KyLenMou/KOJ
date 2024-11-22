package fun.kylen.koj.model.oj.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserHomeInfoVO implements Serializable {

    private Integer cfRating;
    private Integer nowcoderRating;
    private Integer leetcodeRating;

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 学校
     */
    private String school;

    /**
     * 性别
     */
    private String gender;

    /**
     * github地址
     */
    private String githubUrl;

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
     * 头像地址
     */
    private String avatar;

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

}