package fun.kylen.koj.model.admin.dto;

import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.ProblemCase;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class AdminEditProblemDTO {

    @Valid
    private Problem problem;

    private List<ProblemCase> testCases;

    private String uploadTestcaseDir;

    private List<Long> tagIds;
}