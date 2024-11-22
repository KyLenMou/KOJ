package fun.kylen.koj.model.oj.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProblemDetailVO {
    private Long id;
    private String displayId;
    private String authorUserId;
    private String authorUsername;
    private String title;
    private Integer timeLimit;
    private Integer memoryLimit;
    private Integer stackLimit;
    private String description;
    private String input;
    private String output;
    private String examples;
    private String note;
    private Integer difficulty;
    private String judgeMode;
    private List<TagVO> tags;
}