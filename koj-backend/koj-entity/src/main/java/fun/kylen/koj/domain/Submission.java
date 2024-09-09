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
 * @TableName submission
 */
@TableName(value ="submission")
@Data
public class Submission implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 题目id
     */
    private Long problemId;

    /**
     * 题目展示id
     */
    private String problemDisplayId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 提交的时间
     */
    private Date submitTime;

    /**
     * 代码语言
     */
    private String language;

    /**
     * 代码
     */
    private String code;

    /**
     * 结果码具体参考文档
     */
    private Integer verdict;

    /**
     * 错误提醒（编译错误，或者vj提醒）
     */
    private String errorMessage;

    /**
     * 运行时间(ms)
     */
    private Integer time;

    /**
     * 运行内存（kb）
     */
    private Integer memory;

    /**
     * OI判题则不为空
     */
    private Integer oiScore;

    /**
     * 代码长度
     */
    private Integer length;

    /**
     * 团队id，不为团队内提交则为null
     */
    private Long groupId;

    /**
     * 比赛id，非比赛题目默认为0
     */
    private Long contestId;

    /**
     * 提交者所在ip
     */
    private String ip;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 是否为人工评测
     */
    private Integer isManual;

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
        Submission other = (Submission) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProblemId() == null ? other.getProblemId() == null : this.getProblemId().equals(other.getProblemId()))
            && (this.getProblemDisplayId() == null ? other.getProblemDisplayId() == null : this.getProblemDisplayId().equals(other.getProblemDisplayId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getSubmitTime() == null ? other.getSubmitTime() == null : this.getSubmitTime().equals(other.getSubmitTime()))
            && (this.getLanguage() == null ? other.getLanguage() == null : this.getLanguage().equals(other.getLanguage()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getVerdict() == null ? other.getVerdict() == null : this.getVerdict().equals(other.getVerdict()))
            && (this.getErrorMessage() == null ? other.getErrorMessage() == null : this.getErrorMessage().equals(other.getErrorMessage()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getMemory() == null ? other.getMemory() == null : this.getMemory().equals(other.getMemory()))
            && (this.getOiScore() == null ? other.getOiScore() == null : this.getOiScore().equals(other.getOiScore()))
            && (this.getLength() == null ? other.getLength() == null : this.getLength().equals(other.getLength()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getContestId() == null ? other.getContestId() == null : this.getContestId().equals(other.getContestId()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getIsManual() == null ? other.getIsManual() == null : this.getIsManual().equals(other.getIsManual()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProblemId() == null) ? 0 : getProblemId().hashCode());
        result = prime * result + ((getProblemDisplayId() == null) ? 0 : getProblemDisplayId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getSubmitTime() == null) ? 0 : getSubmitTime().hashCode());
        result = prime * result + ((getLanguage() == null) ? 0 : getLanguage().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getVerdict() == null) ? 0 : getVerdict().hashCode());
        result = prime * result + ((getErrorMessage() == null) ? 0 : getErrorMessage().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getMemory() == null) ? 0 : getMemory().hashCode());
        result = prime * result + ((getOiScore() == null) ? 0 : getOiScore().hashCode());
        result = prime * result + ((getLength() == null) ? 0 : getLength().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getContestId() == null) ? 0 : getContestId().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getIsManual() == null) ? 0 : getIsManual().hashCode());
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
        sb.append(", problemId=").append(problemId);
        sb.append(", problemDisplayId=").append(problemDisplayId);
        sb.append(", userId=").append(userId);
        sb.append(", username=").append(username);
        sb.append(", submitTime=").append(submitTime);
        sb.append(", language=").append(language);
        sb.append(", code=").append(code);
        sb.append(", verdict=").append(verdict);
        sb.append(", errorMessage=").append(errorMessage);
        sb.append(", time=").append(time);
        sb.append(", memory=").append(memory);
        sb.append(", oiScore=").append(oiScore);
        sb.append(", length=").append(length);
        sb.append(", groupId=").append(groupId);
        sb.append(", contestId=").append(contestId);
        sb.append(", ip=").append(ip);
        sb.append(", version=").append(version);
        sb.append(", isManual=").append(isManual);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}