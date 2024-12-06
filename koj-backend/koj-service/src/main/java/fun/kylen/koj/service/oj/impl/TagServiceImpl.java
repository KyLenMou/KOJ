package fun.kylen.koj.service.oj.impl;

import fun.kylen.koj.manager.oj.TagManager;
import fun.kylen.koj.model.oj.vo.TagVO;
import fun.kylen.koj.service.oj.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author KyLen
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2024-08-25 21:29:47
*/
@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagManager tagManager;


    @Override
    public List<TagVO> getTagList() {
        return tagManager.getTagList();
    }
}




