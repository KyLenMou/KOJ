package fun.kylen.koj.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.domain.ProblemCase;
import fun.kylen.koj.mapper.ProblemCaseMapper;
import org.springframework.stereotype.Service;

/**
* @author KyLen
* @description 针对表【problem_case】的数据库操作Service实现
* @createDate 2024-08-27 22:50:39
*/
@Service
public class ProblemCaseEntityServiceImpl extends ServiceImpl<ProblemCaseMapper, ProblemCase>
    implements ProblemCaseEntityService {

}




