package fun.kylen.koj.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: KyLen
 * @Date: 2024/9/30 22:20
 * @Description:
 */
@Data
public class SubmissionVerdictVO {
    private Integer verdict;
    private Integer verdictIndex;
    private Integer time;
    private Integer memory;
}
