package fun.kylen.koj.manager.oj;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.model.dto.SubmissionDTO;
import fun.kylen.koj.model.dto.SubmissionPageDTO;
import fun.kylen.koj.model.vo.SubmissionListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: KyLen
 * @Date: 2024/9/9 11:45
 * @Description:
 */
@Component
public class SubmissionManager {
    @Autowired
    private SubmissionEntityService submissionEntityService;

    public Page<SubmissionListVO> listSubmissionByPage(Long current, Long size, Long problemId, String problemDisplayId, String userId, String username, String language) {
        return submissionEntityService.listSubmissionByPage(new Page<>(current, size), problemId, problemDisplayId, userId,username, language);
    }
}
