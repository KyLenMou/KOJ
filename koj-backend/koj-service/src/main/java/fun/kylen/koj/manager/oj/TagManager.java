package fun.kylen.koj.manager.oj;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.dao.TagEntityService;
import fun.kylen.koj.domain.Tag;
import fun.kylen.koj.model.dto.PageDTO;
import fun.kylen.koj.model.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


}
