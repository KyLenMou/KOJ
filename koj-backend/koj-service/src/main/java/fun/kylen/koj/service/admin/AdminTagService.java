package fun.kylen.koj.service.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.kylen.koj.domain.Tag;
import fun.kylen.koj.model.oj.dto.PageDTO;
import fun.kylen.koj.model.oj.vo.TagVO;

import java.util.List;

/**
* @author KyLen
* @description 针对表【tag】的数据库操作Service
* @createDate 2024-08-25 21:29:47
*/
public interface AdminTagService extends IService<Tag> {
    Tag getTagById(Long id);

    void updateTag(Tag tag);

    void deleteTag(Long id);

    Long addTag(String tagName);

    List<Tag> listAllTag();

}
