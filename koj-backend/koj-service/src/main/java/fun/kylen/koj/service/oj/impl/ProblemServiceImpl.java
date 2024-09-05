package fun.kylen.koj.service.oj.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.manager.oj.ProblemManager;
import fun.kylen.koj.model.dto.PageDTO;
import fun.kylen.koj.model.vo.ProblemInfoVO;
import fun.kylen.koj.model.vo.ProblemsetVO;
import fun.kylen.koj.service.oj.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImpl implements ProblemService{
    @Autowired
    private ProblemManager problemManager;
    @Override
    public Page<ProblemsetVO> listProblemsetVOByPage(PageDTO pageDTO) {
        return problemManager.listProblemsetVOByPage(pageDTO);
    }

    @Override
    public ProblemInfoVO getProblemDetail(String problemId) {
        return problemManager.getProblemDetail(problemId);
    }
}




