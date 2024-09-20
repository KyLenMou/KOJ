package fun.kylen.koj.dao.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.mapper.SubmissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author KyLen
* @description 针对表【submission】的数据库操作Service实现
* @createDate 2024-09-05 21:51:51
*/
@Service
public class SubmissionEntityServiceImpl extends ServiceImpl<SubmissionMapper, Submission>
    implements SubmissionEntityService {

}




