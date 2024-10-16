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
    private String problemDisplayId;

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

}