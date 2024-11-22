package fun.kylen.koj.model.oj.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName problem
 */
@Data
public class ProblemsetVO implements Serializable {
    private Long problemId;

    private String displayId;

    private String title;

    private Integer difficulty;

    private List<TagVO> tags;
}