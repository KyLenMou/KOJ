package fun.kylen.koj.service.oj;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.model.oj.vo.ProblemDetailVO;
import fun.kylen.koj.model.oj.vo.ProblemsetVO;

public interface ProblemService {

    ProblemDetailVO getProblemDetailVO(String problemDisplayId);

    Page<ProblemsetVO> listProblemsetVOByPage(Integer current, Integer pageSize);

    Page<ProblemsetVO> listProblemsetVOFromEs(Integer current, Integer pageSize, String searchText);

}
