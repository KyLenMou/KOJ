package fun.kylen.koj.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
    private String userPassword;

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
     * 0可用，1不可用
     */
    private Integer state;

    /**
     * 用户角色 root超级管理员、admin普通管理员、default_user普通用户
     */
    private String userRole;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 0未删除，1已删除
     */
    @TableLogic
    private Integer deleted;

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
        UserInfo other = (UserInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getUserPassword() == null ? other.getUserPassword() == null : this.getUserPassword().equals(other.getUserPassword()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getQq() == null ? other.getQq() == null : this.getQq().equals(other.getQq()))
            && (this.getGithubUsername() == null ? other.getGithubUsername() == null : this.getGithubUsername().equals(other.getGithubUsername()))
            && (this.getGithubId() == null ? other.getGithubId() == null : this.getGithubId().equals(other.getGithubId()))
            && (this.getRating() == null ? other.getRating() == null : this.getRating().equals(other.getRating()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getSchool() == null ? other.getSchool() == null : this.getSchool().equals(other.getSchool()))
            && (this.getRealname() == null ? other.getRealname() == null : this.getRealname().equals(other.getRealname()))
            && (this.getBlogUrl() == null ? other.getBlogUrl() == null : this.getBlogUrl().equals(other.getBlogUrl()))
            && (this.getCfUsername() == null ? other.getCfUsername() == null : this.getCfUsername().equals(other.getCfUsername()))
            && (this.getLeetcodeUsername() == null ? other.getLeetcodeUsername() == null : this.getLeetcodeUsername().equals(other.getLeetcodeUsername()))
            && (this.getNowcoderId() == null ? other.getNowcoderId() == null : this.getNowcoderId().equals(other.getNowcoderId()))
            && (this.getSignature() == null ? other.getSignature() == null : this.getSignature().equals(other.getSignature()))
            && (this.getTitleName() == null ? other.getTitleName() == null : this.getTitleName().equals(other.getTitleName()))
            && (this.getTitleColor() == null ? other.getTitleColor() == null : this.getTitleColor().equals(other.getTitleColor()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getUserRole() == null ? other.getUserRole() == null : this.getUserRole().equals(other.getUserRole()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getUserPassword() == null) ? 0 : getUserPassword().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getQq() == null) ? 0 : getQq().hashCode());
        result = prime * result + ((getGithubUsername() == null) ? 0 : getGithubUsername().hashCode());
        result = prime * result + ((getGithubId() == null) ? 0 : getGithubId().hashCode());
        result = prime * result + ((getRating() == null) ? 0 : getRating().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getSchool() == null) ? 0 : getSchool().hashCode());
        result = prime * result + ((getRealname() == null) ? 0 : getRealname().hashCode());
        result = prime * result + ((getBlogUrl() == null) ? 0 : getBlogUrl().hashCode());
        result = prime * result + ((getCfUsername() == null) ? 0 : getCfUsername().hashCode());
        result = prime * result + ((getLeetcodeUsername() == null) ? 0 : getLeetcodeUsername().hashCode());
        result = prime * result + ((getNowcoderId() == null) ? 0 : getNowcoderId().hashCode());
        result = prime * result + ((getSignature() == null) ? 0 : getSignature().hashCode());
        result = prime * result + ((getTitleName() == null) ? 0 : getTitleName().hashCode());
        result = prime * result + ((getTitleColor() == null) ? 0 : getTitleColor().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getUserRole() == null) ? 0 : getUserRole().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", userPassword=").append(userPassword);
        sb.append(", email=").append(email);
        sb.append(", avatar=").append(avatar);
        sb.append(", qq=").append(qq);
        sb.append(", githubUsername=").append(githubUsername);
        sb.append(", githubId=").append(githubId);
        sb.append(", rating=").append(rating);
        sb.append(", gender=").append(gender);
        sb.append(", school=").append(school);
        sb.append(", realname=").append(realname);
        sb.append(", blogUrl=").append(blogUrl);
        sb.append(", cfUsername=").append(cfUsername);
        sb.append(", leetcodeUsername=").append(leetcodeUsername);
        sb.append(", nowcoderId=").append(nowcoderId);
        sb.append(", signature=").append(signature);
        sb.append(", titleName=").append(titleName);
        sb.append(", titleColor=").append(titleColor);
        sb.append(", state=").append(state);
        sb.append(", userRole=").append(userRole);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}