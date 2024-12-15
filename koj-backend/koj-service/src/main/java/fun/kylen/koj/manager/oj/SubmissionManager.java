package fun.kylen.koj.manager.oj;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.constant.JudgeVerdictConstant;
import fun.kylen.koj.constant.RedisKeyConstant;
import fun.kylen.koj.dao.ProblemEntityService;
import fun.kylen.koj.dao.SubmissionCaseEntityService;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.domain.Problem;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.domain.SubmissionCase;
import fun.kylen.koj.model.oj.vo.SubmissionDetailVO;
import fun.kylen.koj.model.oj.vo.SubmissionListVO;
import fun.kylen.koj.model.oj.vo.SubmissionVerdictVO;
import fun.kylen.koj.utils.PassportUtil;
import fun.kylen.koj.utils.RedisUtil;
import fun.kylen.koj.validator.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: KyLen
 * @Date: 2024/9/9 11:45
 * @Description:
 */
@Component
public class SubmissionManager {
    @Autowired
    private SubmissionEntityService submissionEntityService;
    @Autowired
    private SubmissionCaseEntityService submissionCaseEntityService;
    @Autowired
    private ProblemEntityService problemEntityService;
    @Autowired
    private CommonValidator commonValidator;
    @Autowired
    private RedisUtil redisUtil;

    public Page<SubmissionListVO> listSubmissionByPage(Long current,
                                                       Long size,
                                                       Long problemId,
                                                       String problemDisplayId,
                                                       String userId,
                                                       String username,
                                                       Integer verdict,
                                                       Boolean onlyMine,
                                                       String language) {
        commonValidator.between(size, "页大小", 1, 100);
        if (onlyMine != null && onlyMine) {
            userId = PassportUtil.getCurrentUserIfLogin().getId();
        }
        Page<Submission> submissionPage = submissionEntityService.lambdaQuery()
                .eq(problemId != null, Submission::getProblemId, problemId)
                .eq(StrUtil.isNotBlank(problemDisplayId), Submission::getProblemDisplayId, problemDisplayId)
                .eq(StrUtil.isNotBlank(problemDisplayId), Submission::getUserId, userId)
                .eq(StrUtil.isNotBlank(problemDisplayId), Submission::getUsername, username)
                .eq(verdict != null, Submission::getVerdict, verdict)
                .eq(StrUtil.isNotBlank(language), Submission::getLanguage, language)
                .orderByDesc(Submission::getSubmitTime)
                .page(new Page<>(current, size));
        Page<SubmissionListVO> submissionListVOPage = new Page<>();
        submissionListVOPage.setCurrent(submissionPage.getCurrent());
        submissionListVOPage.setTotal(submissionPage.getTotal());
        submissionListVOPage.setSize(submissionPage.getSize());
        submissionListVOPage.setRecords(submissionPage.getRecords().stream().map(s -> {
            SubmissionListVO submissionListVO = new SubmissionListVO();
            BeanUtil.copyProperties(s, submissionListVO);
            return submissionListVO;
        }).collect(Collectors.toList()));
        return submissionListVOPage;
    }

    public List<SubmissionVerdictVO> getSubmissionVerdict(List<Long> submissionIds) {
        // 获取提交
        return submissionEntityService.lambdaQuery().select(Submission::getId,
                                                            Submission::getVerdict,
                                                            Submission::getRunTime,
                                                            Submission::getRunMemory)
                .in(Submission::getId, submissionIds)
                .list()
                .stream()
                .map(
                        submission -> {
                            SubmissionVerdictVO submissionVerdictVO = new SubmissionVerdictVO();
                            submissionVerdictVO.setSubmissionId(submission.getId());
                            submissionVerdictVO.setVerdict(submission.getVerdict());
                            submissionVerdictVO.setRunTime(submission.getRunTime());
                            submissionVerdictVO.setRunMemory(submission.getRunMemory());
                            // 如果是正在评测的提交，获取队列大小
                            if (submission.getVerdict() == JudgeVerdictConstant.IN_QUEUE) {
                                submissionVerdictVO.setWaitQueueSize(getQueueWaiting(submission.getId()));
                            }
                            return submissionVerdictVO;
                        }
                ).collect(Collectors.toList());
    }

    public SubmissionDetailVO getSubmissionDetail(Long submissionId) {
        // 获取提交
        Submission submission = submissionEntityService.lambdaQuery().eq(Submission::getId, submissionId).one();
        if (submission == null) {
            throw new BusinessException(ResultEnum.NOT_FOUND, "提交不存在");
        }
        // 获取题目
        Long problemId = submission.getProblemId();
        Problem problem = problemEntityService.lambdaQuery().eq(Problem::getId, problemId).one();
        // 获取该提交的所有测试用例情况
        List<SubmissionCase> submissionCases = submissionCaseEntityService.lambdaQuery()
                .eq(SubmissionCase::getSubmissionId, submissionId)
                .orderByDesc(SubmissionCase::getId)
                .list();
        SubmissionDetailVO submissionDetailVO = new SubmissionDetailVO();
        submissionDetailVO.setSubmissionId(submissionId);
        submissionDetailVO.setProblemId(problemId);
        submissionDetailVO.setProblemDisplayId(submission.getProblemDisplayId());
        submissionDetailVO.setProblemTitle(problem.getTitle());
        submissionDetailVO.setUserId(submission.getUserId());
        submissionDetailVO.setUsername(submission.getUsername());
        submissionDetailVO.setLanguage(submission.getLanguage());
        submissionDetailVO.setCode(submission.getCode());
        submissionDetailVO.setDate(submission.getSubmitTime());
        submissionDetailVO.setRunTime(submission.getRunTime());
        submissionDetailVO.setRunMemory(submission.getRunMemory());
        submissionDetailVO.setVerdict(submission.getVerdict());
        submissionDetailVO.setSubmissionCaseList(submissionCases);

        return submissionDetailVO;
    }

    /**
     * 根据提交id获取队列中等待的提交数量
     * @param submissionId
     * @return
     */
    private Integer getQueueWaiting(Long submissionId) {
        try {
            Integer frontId = (Integer) redisUtil.get(RedisKeyConstant.JUDGE_QUEUE_FRONT);
            if (ObjUtil.isNotEmpty(frontId)) {
                return Math.abs(frontId - submissionId.intValue());
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }
}
