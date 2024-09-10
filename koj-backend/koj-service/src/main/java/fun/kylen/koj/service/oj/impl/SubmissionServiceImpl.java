package fun.kylen.koj.service.oj.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.manager.oj.SubmissionManager;
import fun.kylen.koj.manager.oj.SubmitManager;
import fun.kylen.koj.model.dto.SubmissionDTO;
import fun.kylen.koj.model.dto.SubmissionPageDTO;
import fun.kylen.koj.model.vo.SubmissionListVO;
import fun.kylen.koj.service.oj.SubmissionService;
import fun.kylen.koj.service.oj.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Page<SubmissionListVO> listSubmissionByPage(Long current, Long size, Long problemId, String problemDisplayId, String userId, String username, String language) {
        return submissionManager.listSubmissionByPage(current, size, problemId, problemDisplayId, userId,username, language);
    }
}




