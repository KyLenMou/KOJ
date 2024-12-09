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
import fun.kylen.koj.mq.JudgeMessageDispatcher;
import fun.kylen.koj.utils.PassportUtil;
import fun.kylen.koj.utils.RedisUtil;
import fun.kylen.koj.validator.JudgeValidator;
import fun.kylen.koj.vo.DebugVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private JudgeMessageDispatcher judgeMessageDispatcher;
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

    public void submit(SubmissionDTO submissionDTO) {
        UserInfoVO currentUser = PassportUtil.getCurrentUser();
        // controller层已经做过校验
        if (currentUser == null) {
            throw new BusinessException(ResultEnum.NOT_LOGIN, "请先登录");
        }
        Submission submission = new Submission();
        BeanUtil.copyProperties(submissionDTO, submission);
        // 设置用户id
        submission.setUserId(currentUser.getId());
        // 设置用户名
        submission.setUsername(currentUser.getUsername());
        // 设置展示id
        Long problemId = submission.getProblemId();
        Problem problem = problemEntityService.lambdaQuery()
                .eq(Problem::getId, problemId)
                .one();
        submission.setProblemDisplayId(problem.getDisplayId());
        // 设置提交时间
        submission.setSubmitTime(new Date());
        // todo validator校验 (是否存在题目，题目是否可见等)
        // todo 校验代码语言是否合法
        // todo 限制5秒内提交次数
        // 设置 in queue
        submission.setVerdict(JudgeVerdictConstant.IN_QUEUE);
        // 设置乐观锁
        submission.setVersion(0);
        // 保存提交
        boolean save = submissionEntityService.save(submission);
        if (!save) {
            throw new BusinessException(ResultEnum.FAIL, "提交失败，请稍后重试");
        }
        // 为每个提交生成对应的所有测试用例评测情况
        String userId = submission.getUserId();
        Long submissionId = submission.getId();
        // 查询该题目的测试用例
        List<ProblemCase> problemCaseList = problemCaseEntityService.lambdaQuery()
                .eq(ProblemCase::getProblemId, problemId)
                .list();
        // 构造 用户 题目 提交 测试用例
        List<SubmissionCase> submissionCaseList = new ArrayList<>(problemCaseList.size());
        problemCaseList.forEach(p -> {
            SubmissionCase submissionCase = new SubmissionCase();
            submissionCase.setSubmissionId(submissionId);
            submissionCase.setUserId(userId);
            submissionCase.setProblemId(problemId);
            // 其中一个测试用例
            submissionCase.setProblemCaseId(p.getId());
            // 默认未评测
            submissionCase.setVerdict(JudgeVerdictConstant.NULL);
            submissionCaseList.add(submissionCase);
        });
        // 将该用户对于该题目的提交的所有测试点添加到数据库 todo 一个一个插入保证顺序
        submissionCaseEntityService.saveBatch(submissionCaseList);
        try {
            // 发送判题任务到消息队列
            judgeMessageDispatcher.dispatch(submissionId);
        } catch (Exception e) {
            // 发送到消息队列失败，用户需要重新对该提交进行判题 todo 新增重新判题接口，仅需把submitId发送到消息队列中即可
            submissionEntityService.lambdaUpdate()
                    .set(Submission::getVerdict, JudgeVerdictConstant.SUBMIT_FAIL)
                    .eq(Submission::getId, submissionId)
                    .update();
        }
    }

    @RateLimit
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

            judgeMessageDispatcher.debug(debugId);
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

