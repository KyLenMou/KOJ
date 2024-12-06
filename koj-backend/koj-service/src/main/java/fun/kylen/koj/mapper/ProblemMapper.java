package fun.kylen.koj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.domain.Problem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.kylen.koj.model.admin.vo.AdminProblemVO;
import fun.kylen.koj.model.oj.vo.ProblemDetailVO;
import fun.kylen.koj.model.oj.vo.ProblemsetVO;
import fun.kylen.koj.model.oj.vo.TagVO;

/**
* @author KyLen
* @description 针对表【problem】的数据库操作Mapper
* @createDate 2024-11-22 11:47:46
* @Entity fun.kylen.koj.domain.Problem
*/
public interface ProblemMapper extends BaseMapper<Problem> {

    Page<AdminProblemVO> listProblemByPage(Page<AdminProblemVO> page);

    ProblemDetailVO getProblemDetailVO(String problemDisplayId);

}




