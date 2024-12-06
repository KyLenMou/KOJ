package fun.kylen.koj.service.admin.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.manager.admin.AdminProblemManager;
import fun.kylen.koj.model.admin.dto.AdminEditProblemDTO;
import fun.kylen.koj.model.admin.vo.AdminProblemVO;
import fun.kylen.koj.service.admin.AdminProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminProblemServiceImpl implements AdminProblemService {
    @Autowired
    private AdminProblemManager adminProblemManager;

    @Override
    public void editProblem(AdminEditProblemDTO adminEditProblemDTO,Boolean isUpdate) {
        adminProblemManager.editProblem(adminEditProblemDTO,isUpdate);
    }

    @Override
    public Page<AdminProblemVO> listProblemByPage(Integer current, Integer size) {
        return adminProblemManager.listProblemByPage(current, size);
    }

    @Override
    public AdminEditProblemDTO getEditProblem(Long problemId) {
        return adminProblemManager.getEditProblem(problemId);
    }

    @Override
    public void deleteProblem(Long problemId) {
        adminProblemManager.deleteProblem(problemId);
    }

}




