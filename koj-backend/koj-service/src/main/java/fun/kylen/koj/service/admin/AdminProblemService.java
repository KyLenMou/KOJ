package fun.kylen.koj.service.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.model.oj.dto.ProblemDTO;
import fun.kylen.koj.model.admin.vo.AdminProblem;

public interface AdminProblemService {

    void saveProblem(ProblemDTO problemDTO);

    Page<AdminProblem> listProblemByPage(Integer current, Integer size);
}
