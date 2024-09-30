package fun.kylen.koj.model;

import fun.kylen.koj.domain.ProblemCase;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/9/30 15:48
 * @Description:
 */
@Data
@Builder
public class JudgeDTO {
    private Long submitId;
    private String language;
    private String code;
    private String judgeMode;
    private Long timeLimit;
    private Long memoryLimit;
    private Long stackLimit;
    private List<ProblemCase> problemCaseList;
}
