package fun.kylen.koj.model.oj.dto;

import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.ProblemCase;
import fun.kylen.koj.domain.Tag;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProblemDTO {

    @Valid
    private Problem problem;

    private List<ProblemCase> testCases;

    private String uploadTestcaseDir;

    private List<Tag> tags;
}