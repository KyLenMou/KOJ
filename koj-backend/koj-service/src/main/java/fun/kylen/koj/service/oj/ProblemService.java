package fun.kylen.koj.service.oj;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.model.dto.PageDTO;
import fun.kylen.koj.model.vo.ProblemInfoVO;
import fun.kylen.koj.model.vo.ProblemsetVO;

public interface ProblemService {

    Page<ProblemsetVO> listProblemsetVOByPage(PageDTO pageDTO);

    ProblemInfoVO getProblemDetail(String problemId);
}
