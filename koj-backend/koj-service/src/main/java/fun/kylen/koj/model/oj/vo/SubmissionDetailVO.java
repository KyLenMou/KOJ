package fun.kylen.koj.model.oj.vo;

import fun.kylen.koj.domain.SubmissionCase;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/9/30 22:20
 * @Description:
 */
@Data
public class SubmissionDetailVO {
    private Long submissionId;
    private Long problemId;
    private String problemDisplayId;
    private String problemTitle;
    private String userId;
    private String username;
    private String language;
    private String code;
    private Date date;
    private Integer runTime;
    private Integer runMemory;
    private Integer verdict;
    private List<SubmissionCase> submissionCaseList;
}
