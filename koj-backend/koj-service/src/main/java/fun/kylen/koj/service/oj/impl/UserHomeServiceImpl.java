package fun.kylen.koj.service.oj.impl;

import fun.kylen.koj.manager.oj.UserHomeManager;
import fun.kylen.koj.model.oj.vo.UserHomeInfoVO;
import fun.kylen.koj.service.oj.UserHomeService;
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



