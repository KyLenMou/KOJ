package fun.kylen.koj.service.oj.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.manager.oj.SubmissionManager;
import fun.kylen.koj.model.oj.vo.SubmissionDetailVO;
import fun.kylen.koj.model.oj.vo.SubmissionListVO;
import fun.kylen.koj.model.oj.vo.SubmissionVerdictVO;
import fun.kylen.koj.service.oj.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author KyLen
 * @description 针对表【submission】的数据库操作Service实现
 * @createDate 2024-09-05 21:51:51
 */
@Service
public class SubmissionServiceImpl implements SubmissionService {
    @Autowired
    private SubmissionManager submissionManager;

    @Override
    public Page<SubmissionListVO> listSubmissionByPage(Long current,
                                                       Long size,
                                                       Long problemId,
                                                       String problemDisplayId,
                                                       String userId,
                                                       String username,
                                                       Integer verdict,
                                                       Boolean onlyMine,
                                                       String language) {
        return submissionManager.listSubmissionByPage(current,
                                                      size,
                                                      problemId,
                                                      problemDisplayId,
                                                      userId,
                                                      username,
                                                      verdict,
                                                      onlyMine,
                                                      language);
    }

    @Override
    public List<SubmissionVerdictVO> getSubmissionVerdict(List<Long> submissionIds) {
        return submissionManager.getSubmissionVerdict(submissionIds);
    }

    @Override
    public SubmissionDetailVO getSubmissionDetail(Long submissionId) {
        return submissionManager.getSubmissionDetail(submissionId);
    }

}




