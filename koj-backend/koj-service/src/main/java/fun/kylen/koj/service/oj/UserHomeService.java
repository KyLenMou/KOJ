package fun.kylen.koj.service.oj;

import fun.kylen.koj.model.vo.UserHomeInfoVO;

public interface UserHomeService {

    UserHomeInfoVO getUserHomeInfo(String userId, String username);
}
