package fun.kylen.koj.manager.admin;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.constant.StpConstant;
import fun.kylen.koj.dao.ProblemCaseEntityService;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.ProblemTagEntityService;
import fun.kylen.koj.dao.TagEntityService;
import fun.kylen.koj.domain.*;
import fun.kylen.koj.model.admin.dto.AdminEditProblemDTO;
import fun.kylen.koj.model.admin.vo.AdminProblemVO;
import fun.kylen.koj.model.oj.vo.UserInfoVO;
import fun.kylen.koj.utils.PassportUtil;
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
    public void editProblem(AdminEditProblemDTO adminEditProblemDTO, Boolean isUpdate) {
        UserInfoVO currentUser = PassportUtil.getCurrentUser();
        if (currentUser == null) {
            throw new BusinessException(ResultEnum.NOT_LOGIN);
        }
        Problem problem = adminEditProblemDTO.getProblem();
        Long problemId = problem.getId();
        // 如果题目不存在，设置作者
        if (problemEntityService.getById(problemId) == null) {
            problem.setAuthorUserId(currentUser.getId());
            problem.setAuthorUsername(currentUser.getUsername());
            // 题目不存在，不应该有更新者，防止管理员恶意上传
            problem.setModifiedUserId(null);
            problem.setModifiedUsername(null);
        } else {
            // 否则设置更新者
            problem.setModifiedUserId(currentUser.getId());
            problem.setModifiedUsername(currentUser.getUsername());
        }
        // 校验
        problemValidator.validateDTO(adminEditProblemDTO,isUpdate);

        List<ProblemCase> testCases = adminEditProblemDTO.getTestCases();
        String uploadTestcaseDir = adminEditProblemDTO.getUploadTestcaseDir();
        List<Long> tagIds = adminEditProblemDTO.getTagIds();


        // 保存或更新题目
        problemEntityService.saveOrUpdate(problem);

        // 设置测试数据
        if (problem.getIsUploadCase()) {
            // todo 设置文件路径
        } else {
            // 测试数据为手动输入，保存到数据库
            // 清空
            LambdaQueryWrapper<ProblemCase> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ProblemCase::getProblemId, problem.getId());
            problemCaseEntityService.remove(lambdaQueryWrapper);
            // 重建
            testCases.forEach(testCase -> {
                testCase.setProblemId(problem.getId());
                // 完全清空id，保证新增而不是原来的重复
                testCase.setId(null);
                // 一条一条插入，保证插入顺序
                problemCaseEntityService.save(testCase);
            });
        }
        // 标签清空重建
        // 删除所有标签
        LambdaQueryWrapper<ProblemTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ProblemTag::getProblemId, problem.getId());
        problemTagEntityService.remove(lambdaQueryWrapper);
        // 设置题目标签
        List<ProblemTag> problemTagList = tagIds.stream().map(tagId -> {
            ProblemTag problemTag = new ProblemTag();
            problemTag.setProblemId(problem.getId());
            problemTag.setTagId(tagId);
            return problemTag;
        }).collect(Collectors.toList());
        // 保存
        problemTagEntityService.saveBatch(problemTagList);
    }

    public Page<AdminProblemVO> listProblemByPage(Integer current, Integer size) {
        return problemEntityService.listProblemByPage(new Page<>(current, size));
    }

    public AdminEditProblemDTO getEditProblem(Long problemId) {
        Problem problem = problemEntityService.getById(problemId);
        if (problem == null) {
            throw new BusinessException(ResultEnum.FAIL, "不存在该题目");
        }

        List<ProblemTag> problemTagList = problemTagEntityService.lambdaQuery()
                .eq(ProblemTag::getProblemId, problemId)
                .select(ProblemTag::getTagId)
                .list();

        List<Long> tagIds = problemTagList.stream().map(ProblemTag::getTagId).collect(Collectors.toList());

        List<ProblemCase> problemCaseList = problemCaseEntityService.lambdaQuery()
                .eq(ProblemCase::getProblemId, problemId)
                .list();

        AdminEditProblemDTO adminEditProblemDTO = new AdminEditProblemDTO();
        adminEditProblemDTO.setProblem(problem);
        adminEditProblemDTO.setTestCases(problemCaseList);
        adminEditProblemDTO.setTagIds(tagIds);

        return adminEditProblemDTO;
    }

    public void deleteProblem(Long problemId) {
        boolean flag = problemEntityService.removeById(problemId);
        if (!flag) {
            throw new BusinessException(ResultEnum.FAIL, "删除失败");
        }
    }
}