package fun.kylen.koj.validator;

import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.UserInfoEntityService;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.ProblemCase;
import fun.kylen.koj.model.dto.ProblemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProblemValidator {

    @Autowired
    private CommonValidator commonValidator;

    @Autowired
    private UserInfoEntityService userInfoEntityService;

    @Autowired
    private ProblemEntityService problemEntityService;

    public void validateDTO(ProblemDTO problemDTO) {
        Problem problem = problemDTO.getProblem();
        List<ProblemCase> testCases = problemDTO.getTestCases();
        String uploadTestcaseDir = problemDTO.getUploadTestcaseDir();
        if (problem.getIsUploadCase()) {
            commonValidator.validateNotNull(uploadTestcaseDir, "测试用例");
        } else {
            commonValidator.validateNotEmpty(testCases, "测试用例");
            testCases.forEach(t -> {
                commonValidator.validateNotEmpty(t.getInput(), "测试用例输入");
                commonValidator.validateNotEmpty(t.getOutput(), "测试用例输出");
                commonValidator.validateNumber(t.getScore(), "测试用例分数", 1, 10000);
            });
        }
        validate(problem);
    }

    public void validate(Problem problem) {
        String problemId = problem.getProblemId();
        // 长度为1到32
        commonValidator.validateContent(problemId, "题目的展示ID", 1, 32);
        commonValidator.validateNull(problemEntityService.lambdaQuery().eq(Problem::getProblemId, problemId).one(), "题目的展示ID");
        String title = problem.getTitle();
        // 长度为1到256
        commonValidator.validateContent(title, "题目标题", 1, 256);
        String authorUserId = problem.getAuthorUserId();
        // 不能没有作者
        commonValidator.validateNotNull(authorUserId, "作者");
        commonValidator.validateNotNull(userInfoEntityService.getById(authorUserId), "作者");
        String problemType = problem.getProblemType();
        // 题目类型只能是ACM或OI
        commonValidator.validateStrings(problemType, "题目的类型", "ACM", "OI");
        Integer timeLimit = problem.getTimeLimit();
        // 时间限制范围为1到5000
        commonValidator.validateNumber(timeLimit, "题目的时间限制", 1, 5000);
        Integer memoryLimit = problem.getMemoryLimit();
        // 内存限制范围为1到1024
        commonValidator.validateNumber(memoryLimit, "题目的内存限制", 1, 1024);
        Integer stackLimit = problem.getStackLimit();
        // 栈限制范围为1到1024
        commonValidator.validateNumber(stackLimit, "题目的栈限制", 1, 1024);
        String descriptionText = problem.getDescriptionText();
        // 长度为1到65535
        commonValidator.validateContent(descriptionText, "题目描述", 1, 65535);
        String input = problem.getInput();
        // 长度为1到65535
        commonValidator.validateContent(input, "输入描述", 1, 65535);
        String output = problem.getOutput();
        // 长度为1到65535
        commonValidator.validateContent(output, "输出描述", 1, 65535);
        String examples = problem.getExamples();
        // 长度为0到65535
        commonValidator.validateContent(examples, "题面样例", 0, 65535);
        String noteText = problem.getNoteText();
        // 长度为0到65535
        commonValidator.validateContent(noteText, "题目提示", 0, 65535);
        String problemSource = problem.getProblemSource();
        // 长度为1到255
        commonValidator.validateContent(problemSource, "题目来源", 0, 65535);
        Boolean isRemote = problem.getIsRemote();
        commonValidator.validateNotNull(isRemote, "是否为远程判题");
        Integer difficulty = problem.getDifficulty();
        // 难度范围为100到4000
        commonValidator.validateNumber(difficulty, "题目难度", 100, 4000);
        Integer auth = problem.getAuth();
        // 权限范围为1到3
        commonValidator.validateNumber(auth, "题目的权限", 1, 3);
        Integer ioScore = problem.getIoScore();
        // ioScore范围为1到10000
        commonValidator.validateNumber(ioScore, "oi题目的分数", 1, 10000);
        Integer cfScore = problem.getCfScore();
        // cfScore范围为1到10000
        commonValidator.validateNumber(cfScore, "cf题目的分数", 1, 10000);
        Boolean codeShare = problem.getCodeShare();
        commonValidator.validateNotNull(codeShare, "用户是否可用分享");
        String judgeMode = problem.getJudgeMode();
        // 只能为default、spj、interactive
        commonValidator.validateStrings(judgeMode, "判题模式", "default", "spj", "interactive");
        String spjCode = problem.getSpjCode();
        // 长度为0到65535
        commonValidator.validateContent(spjCode, "特判程序或交互程序的代码", 0, 65535);
        String spjLanguage = problem.getSpjLanguage();
        // 长度为0到32
        commonValidator.validateContent(spjLanguage, "特判程序或交互程序的语言", 0, 32);
        Boolean isRemoveEndBlank = problem.getIsRemoveEndBlank();
        commonValidator.validateNotNull(isRemoveEndBlank, "是否去除末尾空格");
        Boolean openCaseResult = problem.getOpenCaseResult();
        commonValidator.validateNotNull(openCaseResult, "是否开放测试用例结果");
        Boolean isUploadCase = problem.getIsUploadCase();
        commonValidator.validateNotNull(isUploadCase, "是否上传测试用例");
        Boolean isGroup = problem.getIsGroup();
        commonValidator.validateNotNull(isGroup, "是否为团队题目");
        Integer isPublic = problem.getIsPublic();
        commonValidator.validateNotNull(isPublic, "是否公开");
        String caseVersion = problem.getCaseVersion();
        String modifiedUserId = problem.getModifiedUserId();
        Long groupId = problem.getGroupId();
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
