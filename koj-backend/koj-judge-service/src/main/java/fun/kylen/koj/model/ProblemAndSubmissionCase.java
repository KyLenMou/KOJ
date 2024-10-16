package fun.kylen.koj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目测试用例和用户在该题目提交下的测试用例组合
 */
@Data
public class ProblemAndSubmissionCase implements Serializable {
    private Long problemId;
    private Long problemCaseId;
    private Long submissionCaseId;
    private String input;
    private String output;
    private Integer score;
}