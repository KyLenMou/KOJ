package fun.kylen.koj.service.oj;

import fun.kylen.koj.model.oj.dto.DebugDTO;
import fun.kylen.koj.model.oj.dto.SubmissionDTO;
import fun.kylen.koj.vo.DebugVO;

/**
* @author KyLen
* @description 针对表【submission】的数据库操作Service
* @createDate 2024-09-05 21:51:51
*/
public interface SubmitService {

    Long submit(SubmissionDTO submission);

    String debug(DebugDTO debugDTO);

    DebugVO getDebugResult(String debugId);
}
