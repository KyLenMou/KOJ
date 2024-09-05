package fun.kylen.koj.service.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.model.dto.PageDTO;
import fun.kylen.koj.model.dto.ProblemDTO;
import fun.kylen.koj.model.vo.ProblemVO;

public interface AdminProblemService {

    void saveProblem(ProblemDTO problemDTO);

    Page<ProblemVO> listProblemVOByPage(PageDTO pageDTO);
}
