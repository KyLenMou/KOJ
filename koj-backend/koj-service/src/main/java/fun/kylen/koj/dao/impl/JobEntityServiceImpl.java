package fun.kylen.koj.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.kylen.koj.job.Job;
import fun.kylen.koj.dao.JobEntityService;
import fun.kylen.koj.mapper.JobMapper;
import org.springframework.stereotype.Service;

/**
* @author KyLen
* @description 针对表【job】的数据库操作Service实现
* @createDate 2024-10-31 15:16:47
*/
@Service
public class JobEntityServiceImpl extends ServiceImpl<JobMapper, Job>
    implements JobEntityService {

}




