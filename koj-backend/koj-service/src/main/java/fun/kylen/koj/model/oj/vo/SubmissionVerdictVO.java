package fun.kylen.koj.model.oj.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: KyLen
 * @Date: 2024/9/30 22:20
 * @Description:
 */
@Data
public class SubmissionVerdictVO {
    private Long submissionId;
    private Integer verdict;
    private Integer runTime;
    private Integer runMemory;
}
