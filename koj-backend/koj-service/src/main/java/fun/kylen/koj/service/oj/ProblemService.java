package fun.kylen.koj.service.oj;

import fun.kylen.koj.domain.Problem;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.kylen.koj.model.dto.ProblemAddDTO;

public interface ProblemService {

    void addProblem(ProblemAddDTO problemAddDTO);

}
