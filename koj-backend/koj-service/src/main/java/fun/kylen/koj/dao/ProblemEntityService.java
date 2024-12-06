package fun.kylen.koj.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.domain.Problem;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.kylen.koj.model.oj.vo.ProblemDetailVO;
import fun.kylen.koj.model.admin.vo.AdminProblemVO;
import fun.kylen.koj.model.oj.vo.ProblemsetVO;
import fun.kylen.koj.model.oj.vo.TagVO;

/**
* @author KyLen
* @description 针对表【problem】的数据库操作Service
* @createDate 2024-08-25 21:31:54
*/
public interface ProblemEntityService extends IService<Problem> {

    Page<AdminProblemVO> listProblemByPage(Page<AdminProblemVO> page);

    ProblemDetailVO getProblemDetailVO(String problemDisplayId);
}
