package fun.kylen.koj.service.admin.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.manager.admin.AdminProblemManager;
import fun.kylen.koj.model.oj.dto.ProblemDTO;
import fun.kylen.koj.model.admin.vo.AdminProblem;
import fun.kylen.koj.service.admin.AdminProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminProblemServiceImpl implements AdminProblemService {
    @Autowired
    private AdminProblemManager adminProblemManager;

    @Override
    public void saveProblem(ProblemDTO problemDTO) {
        adminProblemManager.saveProblem(problemDTO);
    }

    @Override
    public Page<AdminProblem> listProblemByPage(Integer current, Integer size) {
        return adminProblemManager.listProblemByPage(current, size);
    }

}




