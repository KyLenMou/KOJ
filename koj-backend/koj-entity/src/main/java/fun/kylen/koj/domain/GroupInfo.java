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
 * @TableName group_info
 */
@TableName(value ="group_info")
@Data
public class GroupInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 团队名称
     */
    private String groupName;

    /**
     * 团队简称，创建题目时题号自动添加的前缀
     */
    private String shortName;

    /**
     * 团队简介
     */
    private String shortDescription;

    /**
     * 团队介绍
     */
    private String longDescription;

    /**
     * 团队拥有者用户名
     */
    private String ownerUserId;

    /**
     * 0为Public，1为Protected，2为Private
     */
    private Integer auth;

    /**
     * 是否可见
     */
    private Integer isVisible;

    /**
     * 是否封禁
     */
    private Integer isBanned;

    /**
     * 邀请码
     */
    private String invitationCode;

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