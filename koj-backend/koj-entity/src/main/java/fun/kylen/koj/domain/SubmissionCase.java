package fun.kylen.koj.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName submission_case
 */
@TableName(value ="submission_case")
@Data
public class SubmissionCase implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 提交id
     */
    private Long submissionId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 题目id
     */
    private Long problemId;

    /**
     * 题目测试用例id
     */
    private Long problemCaseId;

    /**
     * 具体看结果码
     */
    private Integer verdict;

    /**
     * 测试该样例所用时间ms
     */
    private Integer time;

    /**
     * 测试该样例所用空间KB
     */
    private Integer memory;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 用例输入，比赛不可看
     */
    private String inputData;

    /**
     * 用例输出，比赛不可看
     */
    private String outputData;

    /**
     * 用户输出，比赛不可看
     */
    private String userOutput;

    /**
     * 评测机输出
     */
    private String judgeOutput;

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