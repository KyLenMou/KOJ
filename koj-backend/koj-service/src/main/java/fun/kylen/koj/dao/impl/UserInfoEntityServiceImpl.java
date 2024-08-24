package fun.kylen.koj.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.kylen.koj.dao.UserInfoEntityService;
import fun.kylen.koj.domain.UserInfo;
import fun.kylen.koj.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author KyLen
* @description 针对表【user_info】的数据库操作Service实现
* @createDate 2024-08-24 17:07:46
*/
@Service
public class UserInfoEntityServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoEntityService {

}




