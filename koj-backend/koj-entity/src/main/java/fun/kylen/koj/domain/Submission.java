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
    private String runMessage;

    /**
     * 运行时间(ms)
     */
    private Integer runTime;

    /**
     * 运行内存（kb）
     */
    private Integer runMemory;

    /**
     * 代码长度
     */
    private Integer length;

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
     * 提交的时间
     */
    private Date submitTime;

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