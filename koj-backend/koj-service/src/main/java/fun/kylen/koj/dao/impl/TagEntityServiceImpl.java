package fun.kylen.koj.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.kylen.koj.dao.TagEntityService;
import fun.kylen.koj.domain.Tag;
import fun.kylen.koj.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
* @author KyLen
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2024-08-25 21:31:54
*/
@Service
public class TagEntityServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagEntityService {

}




