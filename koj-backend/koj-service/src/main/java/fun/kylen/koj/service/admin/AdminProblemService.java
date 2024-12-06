package fun.kylen.koj.service.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.model.admin.dto.AdminEditProblemDTO;
import fun.kylen.koj.model.admin.vo.AdminProblemVO;

public interface AdminProblemService {

    void editProblem(AdminEditProblemDTO adminEditProblemDTO,Boolean isUpdate);

    Page<AdminProblemVO> listProblemByPage(Integer current, Integer size);

    AdminEditProblemDTO getEditProblem(Long problemId);

    void deleteProblem(Long problemId);

}
