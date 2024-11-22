package fun.kylen.koj.model.admin.vo;

import fun.kylen.koj.model.oj.vo.TagVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @TableName problem
 */
@Data
public class AdminProblem {
    private Long id;
    private String displayId;
    private String authorUserId;
    private String authorUsername;
    private String title;
    private Integer difficulty;
    private String judgeMode;
    private Integer openCaseResult;
    private Boolean isUploadCase;
    private String modifiedUserId;
    private String modifiedUsername;
    private Integer visible;
    private Date createTime;
    private Date updateTime;
    private List<TagVO> tags;
}