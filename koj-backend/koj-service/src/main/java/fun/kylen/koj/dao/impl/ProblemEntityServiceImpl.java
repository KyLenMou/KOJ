package fun.kylen.koj.dao.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.mapper.ProblemMapper;
import fun.kylen.koj.model.vo.ProblemVO;
import fun.kylen.koj.model.vo.ProblemsetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author KyLen
* @description 针对表【problem】的数据库操作Service实现
* @createDate 2024-08-25 21:31:54
*/
@Service
public class ProblemEntityServiceImpl extends ServiceImpl<ProblemMapper, Problem>
    implements ProblemEntityService {

    @Autowired
    private ProblemMapper problemMapper;

    @Override
    public Page<ProblemsetVO> listProblemsetVOByPage(Page<ProblemsetVO> page) {
        return problemMapper.listProblemsetVOByPage(page);
    }

    @Override
    public Page<ProblemVO> listProblemVOByPage(Page<ProblemVO> page) {
        return problemMapper.listProblemVOByPage(page);
    }
}




