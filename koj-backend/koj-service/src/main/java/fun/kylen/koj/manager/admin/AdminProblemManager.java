package fun.kylen.koj.manager.admin;

import cn.dev33.satoken.stp.StpUtil;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.constant.StpConstant;
import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.ProblemTagEntityService;
import fun.kylen.koj.domain.*;
import fun.kylen.koj.model.dto.ProblemDTO;
import fun.kylen.koj.model.vo.UserInfoVO;
import fun.kylen.koj.validator.ProblemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: KyLen
 * @Date: 2024/8/27 23:12
 * @Description:
 */
@Component
public class AdminProblemManager {
    @Autowired
    private ProblemEntityService problemEntityService;
    @Autowired
    private ProblemCaseEntityService problemCaseEntityService;
    @Autowired
    private ProblemTagEntityService problemTagEntityService;
    @Autowired
    private ProblemValidator problemValidator;
    public void saveProblem(ProblemDTO problemDTO) {
        // 设置作者
        UserInfoVO currentUser = (UserInfoVO) StpUtil.getSession().get(StpConstant.CURRENT_USER);
        if (currentUser == null) {
            throw new BusinessException(ResultEnum.NOT_LOGIN);
        }
        problemDTO.getProblem().setAuthorUserId(currentUser.getId());
        // 设置非远程判题
        problemDTO.getProblem().setIsRemote(false);

        // 校验
        problemValidator.validateDTO(problemDTO);

        Problem problem = problemDTO.getProblem();
        List<ProblemCase> testCases = problemDTO.getTestCases();
        String uploadTestcaseDir = problemDTO.getUploadTestcaseDir();
        List<Tag> tags = problemDTO.getTags();

        // 设置测试数据
        if (problem.getIsUploadCase()) {
            // todo 设置文件路径
        } else {
            // 保存题目
            problemEntityService.save(problem);
            // 测试数据为手动输入，保存到数据库
            testCases.forEach(testCase -> testCase.setProblemId(problem.getId()));
            problemCaseEntityService.saveBatch(testCases);
        }

        // 设置题目标签
        List<ProblemTag> problemTagList = tags.stream().map(tag -> {
            ProblemTag problemTag = new ProblemTag();
            problemTag.setProblemId(problem.getId());
            problemTag.setTagId(tag.getId());
            return problemTag;
        }).collect(Collectors.toList());
        // 保存
        problemTagEntityService.saveBatch(problemTagList);
    }
}
