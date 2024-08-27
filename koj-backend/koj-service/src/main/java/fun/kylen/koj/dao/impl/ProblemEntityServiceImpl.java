package fun.kylen.koj.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.mapper.ProblemMapper;
import org.springframework.stereotype.Service;

/**
* @author KyLen
* @description 针对表【problem】的数据库操作Service实现
* @createDate 2024-08-25 21:31:54
*/
@Service
public class ProblemEntityServiceImpl extends ServiceImpl<ProblemMapper, Problem>
    implements ProblemEntityService {

}




