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
    private String problemId;
    private String problemDisplayId;
    private String problemTitle;
    private String userId;
    private String username;
    private String language;
    private String code;
    private Date date;
    private Integer time;
    private Integer memory;
    private Integer verdict;
    private Integer verdictIndex;
    /**
     * 遇错止评：只返回到不为0的测试点
     * 全部评测：全部返回
     * 每个测试点有样例输入和用户输出，以及程序返回的输出
     */
    private List<SubmissionCase> submissionCaseList;
}
