package fun.kylen.koj.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
public class SubmissionListVO implements Serializable {
    private Long id;

    /**
     * 题目
     */
    private String title;

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
     * 是否为人工评测
     */
    private Integer isManual;
}