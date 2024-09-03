package fun.kylen.koj.service.admin.impl;

import fun.kylen.koj.manager.admin.AdminProblemManager;
import fun.kylen.koj.model.dto.ProblemDTO;
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
}




