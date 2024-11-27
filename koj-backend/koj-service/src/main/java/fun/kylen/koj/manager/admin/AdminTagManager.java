package fun.kylen.koj.manager.admin;

import cn.hutool.core.util.StrUtil;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.dao.TagEntityService;
import fun.kylen.koj.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/8/25 22:16
 * @Description:
 */
@Component
public class AdminTagManager {
    @Autowired
    private TagEntityService tagEntityService;

    public Tag getTagById(Long id) {
        Tag tag = tagEntityService.getById(id);
        if (tag != null) return tag;
        throw new BusinessException(ResultEnum.FAIL, "标签不存在");
    }

    public void updateTag(Tag tag) {
        String tagName = tag.getTagName();
        if (tagEntityService.lambdaQuery().eq(Tag::getTagName, tagName).one() != null) {
            throw new BusinessException(ResultEnum.FAIL, "标签名字已存在");
        }
        tag.setCreateTime(null);
        tag.setUpdateTime(null);
        tagEntityService.updateById(tag);
    }

    public void deleteTag(Long id) {
        if (!tagEntityService.removeById(id)) {
            throw new BusinessException(ResultEnum.FAIL, "删除失败");
        }
    }

    public Long addTag(String tagName) {
        if (StrUtil.isNotBlank(tagName)) {
            if (tagEntityService.lambdaQuery().eq(Tag::getTagName, tagName).one() != null) {
                throw new BusinessException(ResultEnum.FAIL, "标签名字已存在");
            }
            Tag tag = new Tag();
            tag.setTagName(tagName);
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
