package fun.kylen.koj.service.oj.impl;

import fun.kylen.koj.manager.oj.SubmitManager;
import fun.kylen.koj.model.oj.dto.DebugDTO;
import fun.kylen.koj.model.oj.dto.SubmissionDTO;
import fun.kylen.koj.service.oj.SubmitService;
import fun.kylen.koj.vo.DebugVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author KyLen
* @description 针对表【submission】的数据库操作Service实现
* @createDate 2024-09-05 21:51:51
*/
@Service
public class SubmitServiceImpl implements SubmitService {
    @Autowired
    private SubmitManager submitManager;

    @Override
    public void submit(SubmissionDTO submission) {
        submitManager.submit(submission);
    }

    @Override
    public String debug(DebugDTO debugDTO) {
        return submitManager.debug(debugDTO);
    }

    @Override
    public DebugVO getDebugResult(String debugId) {
        return submitManager.getDebugResult(debugId);
    }
}




