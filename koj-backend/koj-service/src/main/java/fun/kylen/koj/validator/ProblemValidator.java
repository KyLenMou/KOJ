package fun.kylen.koj.validator;

import cn.hutool.core.util.StrUtil;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.domain.Problem;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class ProblemValidator {

    @Resource
    private CommonValidator commonValidator;

    public void validateProblem(Problem problem) {
        if (problem == null) {
            throw new BusinessException(ResultEnum.FAIL,"题目的配置项不能为空！");
        }

        if (StrUtil.isBlank(problem.getProblemId())) {
            throw new BusinessException(ResultEnum.FAIL,"题目的展示ID不能为空！");
        }

        defaultValidate(problem);

        if (problem.getTimeLimit() <= 0) {
            throw new BusinessException(ResultEnum.FAIL,"题目的时间限制不能小于等于0！");
        }
        if (problem.getMemoryLimit() <= 0) {
            throw new BusinessException(ResultEnum.FAIL, "题目的内存限制不能小于等于0！");
        }
        if (problem.getStackLimit() <= 0) {
            throw new BusinessException(ResultEnum.FAIL, "题目的栈限制不能小于等于0！");
        }
    }

    private void defaultValidate(Problem problem) {
        Long id = problem.getId();
        String problemId = problem.getProblemId();
        String title = problem.getTitle();
        String authorUserId = problem.getAuthorUserId();
        String problemType = problem.getProblemType();
        Integer timeLimit = problem.getTimeLimit();
        Integer memoryLimit = problem.getMemoryLimit();
        Integer stackLimit = problem.getStackLimit();
        String descriptionText = problem.getDescriptionText();
        String input = problem.getInput();
        String output = problem.getOutput();
        String examples = problem.getExamples();
        String noteText = problem.getNoteText();
        String problemSource = problem.getProblemSource();
        Integer isRemote = problem.getIsRemote();
        Integer difficulty = problem.getDifficulty();
        Integer auth = problem.getAuth();
        Integer ioScore = problem.getIoScore();
        Integer cfScore = problem.getCfScore();
        Integer codeShare = problem.getCodeShare();
        String judgeMode = problem.getJudgeMode();
        String spjCode = problem.getSpjCode();
        String spjLanguage = problem.getSpjLanguage();
        Integer isRemoveEndBlank = problem.getIsRemoveEndBlank();
        Integer openCaseResult = problem.getOpenCaseResult();
        Integer isUploadCase = problem.getIsUploadCase();
        String caseVersion = problem.getCaseVersion();
        String modifiedUserId = problem.getModifiedUserId();
        Integer isGroup = problem.getIsGroup();
        Long groupId = problem.getGroupId();
        Integer isPublic = problem.getIsPublic();
        String inFileName = problem.getInFileName();
        String outFileName = problem.getOutFileName();
        Date createTime = problem.getCreateTime();
        Date updateTime = problem.getUpdateTime();

        if (problemType == null) {
            throw new BusinessException(ResultEnum.FAIL, "题目的类型必须为ACM或OI");
        }
        // todo 问题类型的枚举
    }

    // public void validateProblemUpdate(Problem problem) throws BusinessException {
    //     if (problem == null) {
    //         throw new BusinessException(ResultEnum.FAIL, "题目的配置项不能为空！");
    //     }
    //     if (problem.getId() == null) {
    //         throw new BusinessException(ResultEnum.FAIL,"题目的id不能为空！");
    //     }
    //     validateProblem(problem);
    // }
    //
    // public void validateGroupProblem(Problem problem) throws BusinessException {
    //
    //     if (problem == null) {
    //         throw new BusinessException(ResultEnum.FAIL,"题目的配置项不能为空！");
    //     }
    //
    //     if (StrUtil.isBlank(problem.getProblemId())) {
    //         throw new BusinessException(ResultEnum.FAIL,"题目的展示ID不能为空！");
    //     }
    //
    //     defaultValidate(problem);
    //
    //     commonValidator.validateContent(problem.getProblemId(), "题目的展示ID", 50);
    //     if (problem.getTimeLimit() == null
    //             || problem.getTimeLimit() <= 0
    //             || problem.getTimeLimit() > 1000 * 30) {
    //         throw new BusinessException(ResultEnum.FAIL,"题目的时间限制范围请合理填写！(1~30000ms)");
    //     }
    //
    //     if (problem.getMemoryLimit() == null
    //             || problem.getMemoryLimit() <= 0
    //             || problem.getMemoryLimit() > 1024) {
    //         throw new BusinessException(ResultEnum.FAIL,"题目的内存限制范围请合理填写！(1~1024mb)");
    //     }
    //
    //     if (problem.getStackLimit() == null
    //             || problem.getStackLimit() <= 0
    //             || problem.getStackLimit() > 1024) {
    //         throw new BusinessException(ResultEnum.FAIL,"题目的栈限制范围请合理填写！(1~1024mb)");
    //     }
    //
    //     commonValidator.validateContent(problem.getTitle(), "题目标题", 255);
    //     commonValidator.validateContentLength(problem.getDescription(), "题目描述", 65535);
    //     commonValidator.validateContentLength(problem.getInput(), "输入描述", 65535);
    //     commonValidator.validateContentLength(problem.getOutput(), "输出描述", 65535);
    //     commonValidator.validateContentLength(problem.getHint(), "题目提示", 65535);
    // }
    //
    // public void validateGroupProblemUpdate(Problem problem) throws BusinessException {
    //
    //     if (problem == null) {
    //         throw new BusinessException(ResultEnum.FAIL,"题目的配置项不能为空！");
    //     }
    //
    //     if (problem.getId() == null) {
    //         throw new BusinessException(ResultEnum.FAIL,"题目的id不能为空！");
    //     }
    //     validateGroupProblem(problem);
    // }
}
