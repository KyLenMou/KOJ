package fun.kylen.koj.manager.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.dao.TagEntityService;
import fun.kylen.koj.domain.Tag;
import fun.kylen.koj.model.dto.PageDTO;
import fun.kylen.koj.model.vo.TagVO;
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
public class AdminTagManager {
    @Autowired
    private TagEntityService tagEntityService;

    public Page<TagVO> listTagVOByPage(PageDTO pageDTO) {
        Page<Tag> tagPage = listTagByPage(pageDTO);
        Page<TagVO> tagVOPage = new Page<>();
        BeanUtil.copyProperties(tagPage, tagVOPage);
        tagVOPage.setRecords(tagPage.getRecords().stream().map(tag -> {
            TagVO tagVO = new TagVO();
            BeanUtil.copyProperties(tag, tagVO);
            return tagVO;
        }).collect(Collectors.toList()));
        return tagVOPage;
    }

    public Page<Tag> listTagByPage(PageDTO pageDTO) {
        return tagEntityService.page(new Page<>(pageDTO.getCurrent(), pageDTO.getPageSize()));
    }

    public Tag getTagById(Long id) {
        return tagEntityService.getById(id);
    }

    public Boolean updateTag(Tag tag) {
        String tagName = tag.getTagName();
        Long count = tagEntityService.lambdaQuery().eq(Tag::getTagName, tagName).count();
        if (count > 0) {
            throw new BusinessException(ResultEnum.FAIL, "标签名字已存在");
        }
        return tagEntityService.updateById(tag);
    }

    public Boolean deleteTag(Long id) {
        return tagEntityService.removeById(id);
    }

    public Long addTag(Tag tag) {
        String tagName = tag.getTagName();
        if (StrUtil.isNotBlank(tagName)) {
            Long count = tagEntityService.lambdaQuery().eq(Tag::getTagName, tagName).count();
            if (count > 0) {
                throw new BusinessException(ResultEnum.FAIL, "标签名字已存在");
            }
            tagEntityService.save(tag);
            return tag.getId();
        } else {
            throw new BusinessException(ResultEnum.FAIL, "标签名字不能为空");
        }
    }

    public List<Tag> listAllTag() {
        return tagEntityService.list();
    }
}
