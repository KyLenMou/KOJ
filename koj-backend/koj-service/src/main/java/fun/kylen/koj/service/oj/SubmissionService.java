package fun.kylen.koj.service.oj;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.model.oj.vo.SubmissionDetailVO;
import fun.kylen.koj.model.oj.vo.SubmissionListVO;
import fun.kylen.koj.model.oj.vo.SubmissionVerdictVO;

/**
* @author KyLen
* @description 针对表【submission】的数据库操作Service
* @createDate 2024-09-05 21:51:51
*/
public interface SubmissionService {
    Page<SubmissionListVO> listSubmissionByPage(Long current, Long size, Long problemId, String problemDisplayId, String userId, String username, String language);

    SubmissionVerdictVO getSubmissionVerdict(Long submissionId);

    SubmissionDetailVO getSubmissionDetail(Long submissionId);
}
