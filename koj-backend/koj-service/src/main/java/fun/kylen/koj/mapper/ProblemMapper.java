package fun.kylen.koj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.domain.Problem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.kylen.koj.model.vo.ProblemInfoVO;
import fun.kylen.koj.model.vo.ProblemVO;
import fun.kylen.koj.model.vo.ProblemsetVO;

/**
* @author KyLen
* @description 针对表【problem】的数据库操作Mapper
* @createDate 2024-08-29 16:47:36
* @Entity fun.kylen.koj.domain.Problem
*/
public interface ProblemMapper extends BaseMapper<Problem> {

    Page<ProblemsetVO> listProblemsetVOByPage(Page<ProblemsetVO> page);

    Page<ProblemVO> listProblemVOByPage(Page<ProblemVO> page);

    ProblemInfoVO getProblemDetail(String problemDisplayId);

}




