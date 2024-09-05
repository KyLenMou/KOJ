package fun.kylen.koj.manager.oj;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.ProblemTagEntityService;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.Tag;
import fun.kylen.koj.model.dto.PageDTO;
import fun.kylen.koj.model.vo.ProblemInfoVO;
import fun.kylen.koj.model.vo.ProblemsetVO;
import fun.kylen.koj.validator.ProblemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/8/27 23:12
 * @Description:
 */
@Component
public class ProblemManager {
    @Autowired
    private ProblemEntityService problemEntityService;
    @Autowired
    private ProblemCaseEntityService problemCaseEntityService;
    @Autowired
    private ProblemTagEntityService problemTagEntityService;
    @Autowired
    private ProblemValidator problemValidator;
    public Page<ProblemsetVO> listProblemsetVOByPage(PageDTO pageDTO) {
        Integer current = pageDTO.getCurrent();
        Integer pageSize = pageDTO.getPageSize();
        if (pageSize >= 100) {
            throw new BusinessException(ResultEnum.FAIL, "请不要爬取数据哦 :) ");
        }
        Page<ProblemsetVO> problemsetVOPage = problemEntityService.listProblemsetVOByPage(new Page<>(current, pageSize));
        problemsetVOPage.getRecords().forEach(r -> {
            r.setId(null);
        });
        return problemsetVOPage;
    }

    public ProblemInfoVO getProblemDetail(String problemId) {
        ProblemInfoVO problem = problemEntityService.getProblemDetail(problemId);
        if (problem == null) {
            throw new BusinessException(ResultEnum.NOT_FOUND);
        }
        if (problem.getAuth() != 1) {
            throw new BusinessException(ResultEnum.FAIL, "该题目暂时无法查看");
        }
        return problem;
    }
}
