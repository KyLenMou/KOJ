package fun.kylen.koj.service.oj;

import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.model.dto.SubmissionDTO;

/**
* @author KyLen
* @description 针对表【submission】的数据库操作Service
* @createDate 2024-09-05 21:51:51
*/
public interface SubmitService {

    void submit(SubmissionDTO submission);

}
