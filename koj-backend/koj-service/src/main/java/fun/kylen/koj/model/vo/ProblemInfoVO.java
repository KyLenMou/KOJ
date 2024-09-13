package fun.kylen.koj.model.vo;

import fun.kylen.koj.domain.Problem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProblemInfoVO extends Problem implements Serializable{
    private static final long serialVersionUID = 1L;

    private List<TagVO> tags;
}