package fun.kylen.koj.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.kylen.koj.dao.GroupInfoEntityService;
import fun.kylen.koj.domain.GroupInfo;
import fun.kylen.koj.mapper.GroupInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author KyLen
* @description 针对表【group_info】的数据库操作Service实现
* @createDate 2024-08-25 21:31:54
*/
@Service
public class GroupInfoEntityServiceImpl extends ServiceImpl<GroupInfoMapper, GroupInfo>
    implements GroupInfoEntityService {

}




