package fun.kylen.koj.service.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import fun.kylen.koj.domain.Tag;
import fun.kylen.koj.model.dto.PageDTO;
import fun.kylen.koj.model.vo.TagVO;

import java.util.List;

/**
* @author KyLen
* @description 针对表【tag】的数据库操作Service
* @createDate 2024-08-25 21:29:47
*/
public interface AdminTagService extends IService<Tag> {

    Page<TagVO> listTagVOByPage(PageDTO pageDTO);

    Page<Tag> listTagByPage(PageDTO pageDTO);

    Tag getTagById(Long id);

    Boolean updateTag(Tag tag);

    Boolean deleteTag(Long id);

    Long addTag(Tag tag);

    List<Tag> listAllTag();

}
