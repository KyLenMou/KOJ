package fun.kylen.koj.model.oj.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class SubmissionPageDTO extends PageDTO implements Serializable {
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
     * 代码语言
     */
    private String language;
}