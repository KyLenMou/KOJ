package fun.kylen.koj.manager.admin;

import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.model.dto.ProblemAddDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: KyLen
 * @Date: 2024/8/27 23:12
 * @Description:
 */
@Component
public class AdminProblemManager {
    @Autowired
    private ProblemEntityService problemEntityService;
    public void addProblem(ProblemAddDTO problemAddDTO) {
        throw new BusinessException(ResultEnum.FAIL, "执行到了Manager");
    }
}
