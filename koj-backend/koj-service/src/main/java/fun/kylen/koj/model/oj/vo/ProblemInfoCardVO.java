package fun.kylen.koj.model.oj.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProblemInfoCardVO {
    private String title;
    private String displayId;
    private Long id;
    private String judgeMode;
    private Integer difficulty;
    private String description;
    private List<Long> tags;
    private Integer acceptCount;
    private Integer submitCount;
    private String acceptRate;
}