package fun.kylen.koj.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.model.dto.SubmissionPageDTO;
import fun.kylen.koj.model.vo.SubmissionListVO;

/**
* @author KyLen
* @description 针对表【submission】的数据库操作Service
* @createDate 2024-09-05 21:51:51
*/
public interface SubmissionEntityService extends IService<Submission> {
    Page<SubmissionListVO> listSubmissionByPage(Page<SubmissionListVO> page, Long problemId, String problemDisplayId, String userId,String username, String language);
}
