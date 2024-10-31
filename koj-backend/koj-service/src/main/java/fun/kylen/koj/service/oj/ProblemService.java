package fun.kylen.koj.service.oj;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.model.dto.PageDTO;
import fun.kylen.koj.model.vo.ProblemInfoVO;
import fun.kylen.koj.model.vo.ProblemsetVO;

public interface ProblemService {

    ProblemInfoVO getProblemDetail(String problemDisplayId);

    Page<ProblemsetVO> listProblemsetVOByPage(Integer current, Integer pageSize);

    Page<ProblemsetVO> listProblemsetVOFromEs(Integer current, Integer pageSize, String searchText);

}
