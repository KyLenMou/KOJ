package fun.kylen.koj.service.impl;

import fun.kylen.koj.manager.UserHomeManager;
import fun.kylen.koj.model.vo.UserHomeInfoVO;
import fun.kylen.koj.service.UserHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHomeServiceImpl implements UserHomeService {
    @Autowired
    private UserHomeManager userHomeManager;

    @Override
    public UserHomeInfoVO getUserHomeInfo(String userId, String username) {
        return userHomeManager.getUserHomeInfo(userId,username);
    }
}



