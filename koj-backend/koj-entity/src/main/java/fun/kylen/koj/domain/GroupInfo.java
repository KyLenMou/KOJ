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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        GroupInfo other = (GroupInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getGroupName() == null ? other.getGroupName() == null : this.getGroupName().equals(other.getGroupName()))
            && (this.getShortName() == null ? other.getShortName() == null : this.getShortName().equals(other.getShortName()))
            && (this.getShortDescription() == null ? other.getShortDescription() == null : this.getShortDescription().equals(other.getShortDescription()))
            && (this.getLongDescription() == null ? other.getLongDescription() == null : this.getLongDescription().equals(other.getLongDescription()))
            && (this.getOwnerUserId() == null ? other.getOwnerUserId() == null : this.getOwnerUserId().equals(other.getOwnerUserId()))
            && (this.getAuth() == null ? other.getAuth() == null : this.getAuth().equals(other.getAuth()))
            && (this.getIsVisible() == null ? other.getIsVisible() == null : this.getIsVisible().equals(other.getIsVisible()))
            && (this.getIsBanned() == null ? other.getIsBanned() == null : this.getIsBanned().equals(other.getIsBanned()))
            && (this.getInvitationCode() == null ? other.getInvitationCode() == null : this.getInvitationCode().equals(other.getInvitationCode()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getGroupName() == null) ? 0 : getGroupName().hashCode());
        result = prime * result + ((getShortName() == null) ? 0 : getShortName().hashCode());
        result = prime * result + ((getShortDescription() == null) ? 0 : getShortDescription().hashCode());
        result = prime * result + ((getLongDescription() == null) ? 0 : getLongDescription().hashCode());
        result = prime * result + ((getOwnerUserId() == null) ? 0 : getOwnerUserId().hashCode());
        result = prime * result + ((getAuth() == null) ? 0 : getAuth().hashCode());
        result = prime * result + ((getIsVisible() == null) ? 0 : getIsVisible().hashCode());
        result = prime * result + ((getIsBanned() == null) ? 0 : getIsBanned().hashCode());
        result = prime * result + ((getInvitationCode() == null) ? 0 : getInvitationCode().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", avatar=").append(avatar);
        sb.append(", groupName=").append(groupName);
        sb.append(", shortName=").append(shortName);
        sb.append(", shortDescription=").append(shortDescription);
        sb.append(", longDescription=").append(longDescription);
        sb.append(", ownerUserId=").append(ownerUserId);
        sb.append(", auth=").append(auth);
        sb.append(", isVisible=").append(isVisible);
        sb.append(", isBanned=").append(isBanned);
        sb.append(", invitationCode=").append(invitationCode);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}