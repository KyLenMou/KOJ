package fun.kylen.koj.manager.oj;

import cn.hutool.core.bean.BeanUtil;
import fun.kylen.koj.dao.TagEntityService;
import fun.kylen.koj.model.oj.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: KyLen
 * @Date: 2024/8/25 22:16
 * @Description:
 */
@Component
public class TagManager {
    @Autowired
    private TagEntityService tagEntityService;

    public List<TagVO> getTagList() {
        return tagEntityService.list().stream().map(tagEntity -> {
            TagVO tagVO = new TagVO();
            tagVO.setId(tagEntity.getId());
            tagVO.setTagName(tagEntity.getTagName());
            return tagVO;
        }).collect(Collectors.toList());
    }
}
