package fun.kylen.koj.service.oj.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.manager.oj.ProblemManager;
import fun.kylen.koj.model.oj.vo.ProblemDetailVO;
import fun.kylen.koj.model.oj.vo.ProblemsetVO;
import fun.kylen.koj.service.oj.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImpl implements ProblemService{
    @Autowired
    private ProblemManager problemManager;

    @Override
    public ProblemDetailVO getProblemDetailVO(String problemDisplayId) {
        return problemManager.getProblemDetailVO(problemDisplayId);
    }

    @Override
    public Page<ProblemsetVO> listProblemsetVOByPage(Integer current, Integer pageSize) {
        return problemManager.listProblemsetVOByPage(current, pageSize);
    }

    @Override
    public Page<ProblemsetVO> listProblemsetVOFromEs(Integer current, Integer pageSize, String searchText) {
        return problemManager.listProblemsetVOFromEs(current, pageSize, searchText);
    }
}




