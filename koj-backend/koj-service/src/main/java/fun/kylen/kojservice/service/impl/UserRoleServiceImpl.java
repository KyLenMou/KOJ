package fun.kylen.kojservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.kylen.kojservice.model.domain.UserRole;
import fun.kylen.kojservice.service.UserRoleService;
import fun.kylen.kojservice.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author KyLen
* @description 针对表【user_role】的数据库操作Service实现
* @createDate 2024-08-03 21:54:02
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService {

}




