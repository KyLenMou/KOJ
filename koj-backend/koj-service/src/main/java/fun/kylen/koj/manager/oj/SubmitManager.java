package fun.kylen.koj.manager.oj;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import fun.kylen.koj.annotation.RateLimit;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.constant.JudgeVerdictConstant;
import fun.kylen.koj.constant.RedisKeyConstant;
import fun.kylen.koj.dao.*;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.ProblemCase;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.domain.SubmissionCase;
import fun.kylen.koj.model.oj.dto.DebugDTO;
import fun.kylen.koj.model.oj.dto.SubmissionDTO;
import fun.kylen.koj.model.oj.vo.UserInfoVO;
import fun.kylen.koj.mq.JudgeQueueManager;
import fun.kylen.koj.utils.PassportUtil;
import fun.kylen.koj.utils.RedisUtil;
import fun.kylen.koj.validator.JudgeValidator;
import fun.kylen.koj.vo.DebugVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author: KyLen
 * @Date: 2024/9/9 11:45
 * @Description:
 */
@Component
public class SubmitManager {
    @Autowired
    private SubmissionEntityService submissionEntityService;
    @Autowired
    private UserInfoEntityService userInfoEntityService;
    @Autowired
    private JudgeQueueManager judgeQueueManager;
    @Autowired
    private SubmissionCaseEntityService submissionCaseEntityService;
    @Autowired
    private ProblemCaseEntityService problemCaseEntityService;
    @Autowired
    private ProblemEntityService problemEntityService;
    @Autowired
    private JudgeValidator judgeValidator;
    @Autowired
    private RedisUtil redisUtil;

    @Transactional
    @RateLimit(RateLimit.Type.SUBMIT)
    public Long submit(SubmissionDTO submissionDTO) {
        UserInfoVO currentUser = PassportUtil.getCurrentUserIfLogin();
        Long problemId = submissionDTO.getProblemId();
        // 先获取题目
        Problem problem = problemEntityService.lambdaQuery()
                .eq(Problem::getId, problemId)
                .one();
        if (problem == null) {
            throw new BusinessException(ResultEnum.NOT_FOUND, "题目不存在");
        }
        // 判断语言
        String language = submissionDTO.getLanguage();
        judgeValidator.validateCodeLanguage(language);
        // 构造提交
        Submission submission = new Submission();
        // 设置题目id，code，language
        BeanUtil.copyProperties(submissionDTO, submission);
        // 设置展示id
        submission.setProblemDisplayId(problem.getDisplayId());
        // 设置用户id
        submission.setUserId(currentUser.getId());
        // 设置用户名
        submission.setUsername(currentUser.getUsername());
        // 设置提交时间
        submission.setSubmitTime(new Date());
        // 设置运行时间和内存
        submission.setRunTime(0);
        submission.setRunMemory(0);
        // 事务包括了发送评测消息，对于用户而言提交完之后直接显示排队中，故这里的NULL没有用
        submission.setVerdict(JudgeVerdictConstant.NULL);
        // 设置乐观锁
        submission.setVersion(0);
        // 保存提交
        boolean save = submissionEntityService.save(submission);
        if (!save) {
            throw new BusinessException(ResultEnum.FAIL, "提交失败，请稍后重试");
        }
        // 为每个提交生成对应的所有测试用例评测情况
        String userId = currentUser.getId();
        Long submissionId = submission.getId();
        // 查询该题目的测试用例
        List<ProblemCase> problemCaseList = problemCaseEntityService.lambdaQuery()
                .eq(ProblemCase::getProblemId, problemId)
                .list();
        // 构造该题目下，该用户提交对应的所有测试用例，一一创建并按照顺序插入
        problemCaseList.forEach(p -> {
            SubmissionCase submissionCase = new SubmissionCase();
            submissionCase.setSubmissionId(submissionId);
            submissionCase.setUserId(userId);
            submissionCase.setProblemId(problemId);
            // 其中一个测试用例
            submissionCase.setProblemCaseId(p.getId());
            // 默认未评测
            submissionCase.setVerdict(JudgeVerdictConstant.NULL);
            submissionCaseEntityService.save(submissionCase);
        });
        try {
            // 发送判题任务到消息队列
            judgeQueueManager.dispatch(submissionId);
            // 设置提交状态为正在排队
            submissionEntityService.lambdaUpdate()
                    .set(Submission::getVerdict, JudgeVerdictConstant.IN_QUEUE)
                    .eq(Submission::getId, submissionId)
                    .update();
        } catch (Exception e) {
            // 发送到消息队列失败，设置提交失败
            submissionEntityService.lambdaUpdate()
                    .set(Submission::getVerdict, JudgeVerdictConstant.SUBMIT_FAILED)
                    .eq(Submission::getId, submissionId)
                    .update();
        }
        return submissionId;
    }

    @RateLimit(RateLimit.Type.DEBUG)
    public String debug(DebugDTO debugDTO) {
        UserInfoVO currentUser = PassportUtil.getCurrentUserIfLogin();

        judgeValidator.validateDebugDTO(debugDTO);

        try {
            DebugVO debugVO = getDebugVO(debugDTO);

            // 设置当前调试任务的用户id，只能由当前用户访问
            debugVO.setUserId(currentUser.getId());

            String debugId = UUID.fastUUID().toString();

            debugVO.setDebugId(debugId);
            // 60s之内调试
            redisUtil.set(RedisKeyConstant.DEBUG + debugId, debugVO, 60);

            judgeQueueManager.debug(debugId);
            return debugId;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.FAIL, "调试失败，请稍后重试");
        }
    }

    private static DebugVO getDebugVO(DebugDTO debugDTO) {
        int size = debugDTO.getUserInputList().size();
        DebugVO debugVO = new DebugVO();
        debugVO.setLanguage(debugDTO.getLanguage());
        debugVO.setCode(debugDTO.getCode());
        debugVO.setTimeLimit(debugDTO.getTimeLimit());
        debugVO.setMemoryLimit(debugDTO.getMemoryLimit());
        debugVO.setStackLimit(debugDTO.getStackLimit());
        debugVO.setJudgeMode(debugDTO.getJudgeMode());
        debugVO.setVerdict(new ArrayList<>(Collections.nCopies(size, JudgeVerdictConstant.NULL)));
        debugVO.setRunTime(new ArrayList<>(Collections.nCopies(size, 0)));
        debugVO.setRunMemory(new ArrayList<>(Collections.nCopies(size, 0)));
        debugVO.setMessage(new ArrayList<>(Collections.nCopies(size, "")));
        debugVO.setUserInputList(debugDTO.getUserInputList());
        debugVO.setUserOutputList(new ArrayList<>(Collections.nCopies(size, "")));
        debugVO.setExpectedOutputList(debugDTO.getExpectedOutputList());
        return debugVO;
    }

    public DebugVO getDebugResult(String debugId) {
        UserInfoVO currentUser = PassportUtil.getCurrentUserIfLogin();
        DebugVO debugVO = redisUtil.get(RedisKeyConstant.DEBUG + debugId, DebugVO.class);
        // 如果尝试获取他人已存在的调试任务，返回不存在以混淆调用者
        if (debugVO == null || !debugVO.getUserId().equals(currentUser.getId())) {
            throw new BusinessException(ResultEnum.NOT_FOUND, "该调试任务不存在");
        }
        // 如果未评测，理论上所有的都应该为NULL，所以只要出现NULL就返回
        for (int i = 0; i < debugVO.getVerdict().size(); i++) {
            if (debugVO.getVerdict().get(i) == JudgeVerdictConstant.NULL) {
                return debugVO;
            }
        }
        // 否则全部都已经评测了，删除redis中的数据
        redisUtil.del(RedisKeyConstant.DEBUG + debugId);
        // 调试任务已经完成，设置debugId为空，方便前端判断
        debugVO.setDebugId("");
        return debugVO;
    }
}

