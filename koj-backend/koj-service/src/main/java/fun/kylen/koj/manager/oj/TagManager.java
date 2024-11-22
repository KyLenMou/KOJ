package fun.kylen.koj.manager.oj;

import fun.kylen.koj.dao.TagEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
