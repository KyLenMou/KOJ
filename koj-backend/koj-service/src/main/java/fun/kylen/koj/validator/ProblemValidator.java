package fun.kylen.koj.validator;

import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.UserInfoEntityService;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.ProblemCase;
import fun.kylen.koj.model.admin.dto.AdminEditProblemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ProblemValidator {

    @Autowired
    private CommonValidator commonValidator;

    @Autowired
    private UserInfoEntityService userInfoEntityService;

    @Autowired
    private ProblemEntityService problemEntityService;

    public void validateDTO(AdminEditProblemDTO adminEditProblemDTO, Boolean isUpdate) {
        Problem problem = adminEditProblemDTO.getProblem();
        List<ProblemCase> testCases = adminEditProblemDTO.getTestCases();
        String uploadTestcaseDir = adminEditProblemDTO.getUploadTestcaseDir();
        // todo spj和interactive判断
        if (problem.getIsUploadCase()) {
            commonValidator.validateNotEmpty(uploadTestcaseDir, "测试用例");
        } else {
            commonValidator.validateNotEmpty(testCases, "测试用例");
            testCases.forEach(t -> {
                commonValidator.validateNotEmpty(t.getInput(), "测试用例输入");
                commonValidator.validateNotEmpty(t.getOutput(), "测试用例输出");
            });
        }
        validate(problem,isUpdate);
    }

    public void validate(Problem problem, Boolean isUpdate) {
        // 长度为1到32
        String problemId = problem.getDisplayId();
        commonValidator.validateContent(problemId, "题目的展示ID", 1, 32);
        if (!isUpdate) commonValidator.validateNull(problemEntityService.lambdaQuery().eq(Problem::getDisplayId, problemId).one(), "题目的展示ID");
        // 长度为1到256
        String title = problem.getTitle();
        commonValidator.validateContent(title, "题目标题", 1, 256);
        // 不能没有作者
        String authorUserId = problem.getAuthorUserId();
        commonValidator.validateNotNull(authorUserId, "作者");
        commonValidator.validateNotNull(userInfoEntityService.getById(authorUserId), "作者");
        // 时间限制范围为1到5000
        Integer timeLimit = problem.getTimeLimit();
        commonValidator.validateNumber(timeLimit, "题目的时间限制", 1, 5000);
        // 内存限制范围为1到1024
        Integer memoryLimit = problem.getMemoryLimit();
        commonValidator.validateNumber(memoryLimit, "题目的内存限制", 1, 1024);
        // 栈限制范围为1到1024
        Integer stackLimit = problem.getStackLimit();
        commonValidator.validateNumber(stackLimit, "题目的栈限制", 1, 1024);
        // 长度为1到65535
        String description = problem.getDescription();
        commonValidator.validateContent(description, "题目描述", 1, 65535);
        // 长度为1到65535
        String input = problem.getInput();
        commonValidator.validateContent(input, "输入描述", 1, 65535);
        // 长度为1到65535
        String output = problem.getOutput();
        commonValidator.validateContent(output, "输出描述", 1, 65535);
        // 长度为0到65535
        String examples = problem.getExamples();
        commonValidator.validateContent(examples, "题面样例", 0, 65535);
        // 长度为0到65535
        String note = problem.getNote();
        commonValidator.validateContent(note, "题目提示", 0, 65535);
        // 难度范围为100到4000
        Integer difficulty = problem.getDifficulty();
        commonValidator.validateNumber(difficulty, "题目难度", 100, 4000);
        // 只能为default、spj、interactive
        String judgeMode = problem.getJudgeMode();
        commonValidator.validateStrings(judgeMode, "判题模式", "default", "spj", "interactive");
        // 长度为0到65535
        String spjCode = problem.getSpjCode();
        commonValidator.validateContent(spjCode, "特判程序或交互程序的代码", 0, 65535);
        // 长度为0到32
        String spjLanguage = problem.getSpjLanguage();
        commonValidator.validateContent(spjLanguage, "特判程序或交互程序的语言", 0, 32);
        Boolean isRemoveEndBlank = problem.getIsRemoveEndBlank();
        commonValidator.validateNotNull(isRemoveEndBlank, "是否去除末尾空格");
        Integer openCaseResult = problem.getOpenCaseResult();
        commonValidator.validateNumber(openCaseResult, "是否开启测试样例结果查看", 0, 1);
        Boolean isUploadCase = problem.getIsUploadCase();
        commonValidator.validateNotNull(isUploadCase, "是否上传测试用例");
        Integer visible = problem.getVisible();
        commonValidator.validateNotNull(visible, "是否公开");
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
