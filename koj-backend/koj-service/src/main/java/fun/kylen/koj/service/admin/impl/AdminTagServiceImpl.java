package fun.kylen.koj.service.admin.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.kylen.koj.domain.Tag;
import fun.kylen.koj.manager.admin.AdminTagManager;
import fun.kylen.koj.mapper.TagMapper;
import fun.kylen.koj.model.oj.dto.PageDTO;
import fun.kylen.koj.model.oj.vo.TagVO;
import fun.kylen.koj.service.admin.AdminTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author KyLen
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2024-08-25 21:29:47
*/
@Service
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements AdminTagService {

    @Autowired
    private AdminTagManager adminTagManager;

    @Override
    public Tag getTagById(Long id) {
        return adminTagManager.getTagById(id);
    }

    @Override
    public void updateTag(Tag tag) {
        adminTagManager.updateTag(tag);
    }

    @Override
    public void deleteTag(Long id) {
        adminTagManager.deleteTag(id);
    }

    @Override
    public Long addTag(String tagName) {
        return adminTagManager.addTag(tagName);
    }

    @Override
    public List<Tag> listAllTag() {
        return adminTagManager.listAllTag();
    }
}




