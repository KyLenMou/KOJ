package fun.kylen.koj.manager.oj;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.dao.SubmissionCaseEntityService;
import fun.kylen.koj.dao.SubmissionEntityService;
import fun.kylen.koj.dao.UserInfoEntityService;
import fun.kylen.koj.domain.Submission;
import fun.kylen.koj.domain.SubmissionCase;
import fun.kylen.koj.domain.UserInfo;
import fun.kylen.koj.model.oj.vo.SubmissionDetailVO;
import fun.kylen.koj.model.oj.vo.SubmissionListVO;
import fun.kylen.koj.model.oj.vo.SubmissionVerdictVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private UserInfoEntityService userInfoEntityService;
    @Autowired
    private SubmissionCaseEntityService submissionCaseEntityService;

    public Page<SubmissionListVO> listSubmissionByPage(Long current,
                                                       Long size,
                                                       Long problemId,
                                                       String problemDisplayId,
                                                       String userId,
                                                       String username,
                                                       String language) {
        return submissionEntityService.listSubmissionByPage(new Page<>(current, size),
                                                            problemId,
                                                            problemDisplayId,
                                                            userId,
                                                            username,
                                                            language);
    }

    public SubmissionVerdictVO getSubmissionVerdict(Long submissionId) {
        Submission submission = submissionEntityService.lambdaQuery()
                .eq(Submission::getId, submissionId)
                .one();
        if (submission == null) {
            // 不抛异常，直接返回空
            return null;
        }
        // 根据提交id获取到对应所有的测试用例状态
        List<SubmissionCase> submissionCases = submissionCaseEntityService.lambdaQuery()
                .eq(SubmissionCase::getSubmissionId, submissionId)
                .list();
        // 返回信息
        SubmissionVerdictVO submissionVerdictVO = new SubmissionVerdictVO();
        submissionVerdictVO.setVerdict(submissionVerdictVO.getVerdict());
        submissionVerdictVO.setVerdictIndex(submissionCases.size() + 1);
        submissionVerdictVO.setTime(submission.getRunTime());
        submissionVerdictVO.setMemory(submissionVerdictVO.getMemory());

        /*
          遍历到下标第i-1个即第i个测试用例
          如果为0，则verdict 0， index i （RUNNING ON TEST i）
          如果不为200， 则verdict x，index i （{ERROR_STATUS} ON TEST i）
          如果为200，继续遍历；全部是200，则verdict 200，index max_i （ACCEPTED）
         */

        for (int i = 0; i < submissionCases.size(); i++) {
            Integer verdict = submissionCases.get(i).getVerdict();
            Integer time = submissionCases.get(i).getTime();
            Integer memory = submissionCases.get(i).getMemory();
            if (verdict != 200) {
                submissionVerdictVO.setVerdict(verdict);
                submissionVerdictVO.setVerdictIndex(i + 1);
                submissionVerdictVO.setTime(time);
                submissionVerdictVO.setMemory(memory);
                break;
            }
        }
        return submissionVerdictVO;
    }

    public SubmissionDetailVO getSubmissionDetail(Long submissionId) {
        Submission submission = submissionEntityService.lambdaQuery()
                .eq(Submission::getId, submissionId)
                .one();
        if (submission == null) {
            throw new BusinessException(ResultEnum.NOT_FOUND, "提交不存在");
        }
        SubmissionDetailVO submissionDetailVO = new SubmissionDetailVO();
        submissionDetailVO.setSubmissionId(submissionId);
        // 查用户信息
        String userId = submission.getUserId();
        UserInfo userInfo = userInfoEntityService.lambdaQuery()
                .eq(UserInfo::getId, userId)
                .one();
        if (userInfo == null) {
            throw new BusinessException(ResultEnum.NOT_LOGIN, "用户不存在");
        }
        String username = userInfo.getUsername();
        submissionDetailVO.setUserId(userId);
        submissionDetailVO.setUsername(username);
        return null;
    }
}
