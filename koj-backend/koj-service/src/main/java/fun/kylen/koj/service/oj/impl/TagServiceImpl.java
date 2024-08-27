package fun.kylen.koj.service.oj.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.kylen.koj.domain.Tag;
import fun.kylen.koj.manager.oj.TagManager;
import fun.kylen.koj.model.dto.PageDTO;
import fun.kylen.koj.model.vo.TagVO;
import fun.kylen.koj.service.oj.TagService;
import fun.kylen.koj.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author KyLen
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2024-08-25 21:29:47
*/
@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagManager tagManager;


}




