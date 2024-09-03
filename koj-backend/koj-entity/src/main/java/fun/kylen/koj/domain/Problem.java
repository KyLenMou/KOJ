package fun.kylen.koj.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName problem
 */
@TableName(value ="problem")
@Data
public class Problem implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 问题的自定义ID
     */
    @NotEmpty(message = "问题的自定义ID不能为空")
    private String problemId;

    /**
     * 题目
     */
    @NotEmpty(message = "题目标题不能为空")
    private String title;

    /**
     * 作者uid
     */
    private String authorUserId;

    /**
     * ACM,OI
     */
    @NotEmpty(message = "问题类型不能为空")
    private String problemType;

    /**
     * 单位ms
     */
    @NotNull(message = "时间限制不能为空")
    private Integer timeLimit;

    /**
     * 单位kb
     */
    @NotNull(message = "内存限制不能为空")
    private Integer memoryLimit;

    /**
     * 单位mb
     */
    @NotNull(message = "栈限制不能为空")
    private Integer stackLimit;

    /**
     * 题面描述文本
     */
    @NotEmpty(message = "题面描述不能为空")
    private String descriptionText;

    /**
     * 输入描述
     */
    @NotEmpty(message = "输入描述不能为空")
    private String input;

    /**
     * 输出描述
     */
    @NotEmpty(message = "输出描述不能为空")
    private String output;

    /**
     * 题面样例，json形式
     */
    private String examples;

    /**
     * 样例解释文本
     */
    private String noteText;

    /**
     * 题目来源
     */
    private String problemSource;

    /**
     * 是否为vj判题
     */
    private Boolean isRemote;

    /**
     * 题目难度对标codeforces
     */
    @NotNull(message = "题目难度不能为空")
    private Integer difficulty;

    /**
     * 默认为1公开，2为私有，3为比赛题目
     */
    @NotNull(message = "题目是否公开不能为空")
    private Integer auth;

    /**
     * 当该题目为io题目时的分数
     */
    private Integer ioScore;

    /**
     * 题目所在比赛为CF赛制时的分数
     */
    private Integer cfScore;

    /**
     * 该题目对应的相关提交代码，用户是否可用分享
     */
    @NotNull(message = "是否允许分享代码不能为空")
    private Boolean codeShare;

    /**
     * 题目评测模式：default、spj、interactive
     */
    @NotEmpty(message = "评测模式不能为空")
    private String judgeMode;

    /**
     * 特判程序或交互程序代码
     */
    private String spjCode;

    /**
     * 特判程序或交互程序代码的语言
     */
    private String spjLanguage;

    /**
     * 是否默认去除用户代码的文末空格
     */
    @NotNull(message = "是否去除文末空格不能为空")
    private Boolean isRemoveEndBlank;

    /**
     * 是否默认开启该题目的测试样例结果查看
     */
    @NotNull(message = "是否开启测试样例结果查看不能为空")
    private Boolean openCaseResult;

    /**
     * 题目测试数据是否是上传文件的
     */
    @NotNull(message = "是否上传测试数据不能为空")
    private Boolean isUploadCase;

    /**
     * 题目测试数据的版本号
     */
    private String caseVersion;

    /**
     * 最近一次修改题目的管理员id
     */
    private String modifiedUserId;

    /**
     * 是否是团队题目
     */
    @NotNull(message = "是否是团队题目不能为空")
    private Boolean isGroup;

    /**
     * 团队题目的团队id
     */
    private Long groupId;

    /**
     * 公开题目为1，团队题目为0，审批中为2，申请拒绝为-1
     */
    @NotNull(message = "是公开题目还是团队题目不能为空")
    private Integer isPublic;

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
        Problem other = (Problem) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProblemId() == null ? other.getProblemId() == null : this.getProblemId().equals(other.getProblemId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getAuthorUserId() == null ? other.getAuthorUserId() == null : this.getAuthorUserId().equals(other.getAuthorUserId()))
            && (this.getProblemType() == null ? other.getProblemType() == null : this.getProblemType().equals(other.getProblemType()))
            && (this.getTimeLimit() == null ? other.getTimeLimit() == null : this.getTimeLimit().equals(other.getTimeLimit()))
            && (this.getMemoryLimit() == null ? other.getMemoryLimit() == null : this.getMemoryLimit().equals(other.getMemoryLimit()))
            && (this.getStackLimit() == null ? other.getStackLimit() == null : this.getStackLimit().equals(other.getStackLimit()))
            && (this.getDescriptionText() == null ? other.getDescriptionText() == null : this.getDescriptionText().equals(other.getDescriptionText()))
            && (this.getInput() == null ? other.getInput() == null : this.getInput().equals(other.getInput()))
            && (this.getOutput() == null ? other.getOutput() == null : this.getOutput().equals(other.getOutput()))
            && (this.getExamples() == null ? other.getExamples() == null : this.getExamples().equals(other.getExamples()))
            && (this.getNoteText() == null ? other.getNoteText() == null : this.getNoteText().equals(other.getNoteText()))
            && (this.getProblemSource() == null ? other.getProblemSource() == null : this.getProblemSource().equals(other.getProblemSource()))
            && (this.getIsRemote() == null ? other.getIsRemote() == null : this.getIsRemote().equals(other.getIsRemote()))
            && (this.getDifficulty() == null ? other.getDifficulty() == null : this.getDifficulty().equals(other.getDifficulty()))
            && (this.getAuth() == null ? other.getAuth() == null : this.getAuth().equals(other.getAuth()))
            && (this.getIoScore() == null ? other.getIoScore() == null : this.getIoScore().equals(other.getIoScore()))
            && (this.getCfScore() == null ? other.getCfScore() == null : this.getCfScore().equals(other.getCfScore()))
            && (this.getCodeShare() == null ? other.getCodeShare() == null : this.getCodeShare().equals(other.getCodeShare()))
            && (this.getJudgeMode() == null ? other.getJudgeMode() == null : this.getJudgeMode().equals(other.getJudgeMode()))
            && (this.getSpjCode() == null ? other.getSpjCode() == null : this.getSpjCode().equals(other.getSpjCode()))
            && (this.getSpjLanguage() == null ? other.getSpjLanguage() == null : this.getSpjLanguage().equals(other.getSpjLanguage()))
            && (this.getIsRemoveEndBlank() == null ? other.getIsRemoveEndBlank() == null : this.getIsRemoveEndBlank().equals(other.getIsRemoveEndBlank()))
            && (this.getOpenCaseResult() == null ? other.getOpenCaseResult() == null : this.getOpenCaseResult().equals(other.getOpenCaseResult()))
            && (this.getIsUploadCase() == null ? other.getIsUploadCase() == null : this.getIsUploadCase().equals(other.getIsUploadCase()))
            && (this.getCaseVersion() == null ? other.getCaseVersion() == null : this.getCaseVersion().equals(other.getCaseVersion()))
            && (this.getModifiedUserId() == null ? other.getModifiedUserId() == null : this.getModifiedUserId().equals(other.getModifiedUserId()))
            && (this.getIsGroup() == null ? other.getIsGroup() == null : this.getIsGroup().equals(other.getIsGroup()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getIsPublic() == null ? other.getIsPublic() == null : this.getIsPublic().equals(other.getIsPublic()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProblemId() == null) ? 0 : getProblemId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getAuthorUserId() == null) ? 0 : getAuthorUserId().hashCode());
        result = prime * result + ((getProblemType() == null) ? 0 : getProblemType().hashCode());
        result = prime * result + ((getTimeLimit() == null) ? 0 : getTimeLimit().hashCode());
        result = prime * result + ((getMemoryLimit() == null) ? 0 : getMemoryLimit().hashCode());
        result = prime * result + ((getStackLimit() == null) ? 0 : getStackLimit().hashCode());
        result = prime * result + ((getDescriptionText() == null) ? 0 : getDescriptionText().hashCode());
        result = prime * result + ((getInput() == null) ? 0 : getInput().hashCode());
        result = prime * result + ((getOutput() == null) ? 0 : getOutput().hashCode());
        result = prime * result + ((getExamples() == null) ? 0 : getExamples().hashCode());
        result = prime * result + ((getNoteText() == null) ? 0 : getNoteText().hashCode());
        result = prime * result + ((getProblemSource() == null) ? 0 : getProblemSource().hashCode());
        result = prime * result + ((getIsRemote() == null) ? 0 : getIsRemote().hashCode());
        result = prime * result + ((getDifficulty() == null) ? 0 : getDifficulty().hashCode());
        result = prime * result + ((getAuth() == null) ? 0 : getAuth().hashCode());
        result = prime * result + ((getIoScore() == null) ? 0 : getIoScore().hashCode());
        result = prime * result + ((getCfScore() == null) ? 0 : getCfScore().hashCode());
        result = prime * result + ((getCodeShare() == null) ? 0 : getCodeShare().hashCode());
        result = prime * result + ((getJudgeMode() == null) ? 0 : getJudgeMode().hashCode());
        result = prime * result + ((getSpjCode() == null) ? 0 : getSpjCode().hashCode());
        result = prime * result + ((getSpjLanguage() == null) ? 0 : getSpjLanguage().hashCode());
        result = prime * result + ((getIsRemoveEndBlank() == null) ? 0 : getIsRemoveEndBlank().hashCode());
        result = prime * result + ((getOpenCaseResult() == null) ? 0 : getOpenCaseResult().hashCode());
        result = prime * result + ((getIsUploadCase() == null) ? 0 : getIsUploadCase().hashCode());
        result = prime * result + ((getCaseVersion() == null) ? 0 : getCaseVersion().hashCode());
        result = prime * result + ((getModifiedUserId() == null) ? 0 : getModifiedUserId().hashCode());
        result = prime * result + ((getIsGroup() == null) ? 0 : getIsGroup().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getIsPublic() == null) ? 0 : getIsPublic().hashCode());
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
        sb.append(", title=").append(title);
        sb.append(", authorUserId=").append(authorUserId);
        sb.append(", problemType=").append(problemType);
        sb.append(", timeLimit=").append(timeLimit);
        sb.append(", memoryLimit=").append(memoryLimit);
        sb.append(", stackLimit=").append(stackLimit);
        sb.append(", descriptionText=").append(descriptionText);
        sb.append(", input=").append(input);
        sb.append(", output=").append(output);
        sb.append(", examples=").append(examples);
        sb.append(", noteText=").append(noteText);
        sb.append(", problemSource=").append(problemSource);
        sb.append(", isRemote=").append(isRemote);
        sb.append(", difficulty=").append(difficulty);
        sb.append(", auth=").append(auth);
        sb.append(", ioScore=").append(ioScore);
        sb.append(", cfScore=").append(cfScore);
        sb.append(", codeShare=").append(codeShare);
        sb.append(", judgeMode=").append(judgeMode);
        sb.append(", spjCode=").append(spjCode);
        sb.append(", spjLanguage=").append(spjLanguage);
        sb.append(", isRemoveEndBlank=").append(isRemoveEndBlank);
        sb.append(", openCaseResult=").append(openCaseResult);
        sb.append(", isUploadCase=").append(isUploadCase);
        sb.append(", caseVersion=").append(caseVersion);
        sb.append(", modifiedUserId=").append(modifiedUserId);
        sb.append(", isGroup=").append(isGroup);
        sb.append(", groupId=").append(groupId);
        sb.append(", isPublic=").append(isPublic);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}