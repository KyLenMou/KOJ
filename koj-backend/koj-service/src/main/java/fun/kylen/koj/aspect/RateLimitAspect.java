package fun.kylen.koj.aspect;

import fun.kylen.koj.annotation.RateLimit;
import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.constant.RedisKeyConstant;
import fun.kylen.koj.model.oj.dto.DebugDTO;
import fun.kylen.koj.model.oj.dto.SubmissionDTO;
import fun.kylen.koj.model.oj.vo.UserInfoVO;
import fun.kylen.koj.utils.PassportUtil;
import fun.kylen.koj.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: KyLen
 * @Date: 2024/12/8 15:19
 * @Description:
 */
@Aspect
@Component
@Slf4j
public class RateLimitAspect {
    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("@annotation(fun.kylen.koj.annotation.RateLimit)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 检查是否登录
        UserInfoVO user = PassportUtil.getCurrentUserIfLogin();
        // 获取注解类型
        RateLimit.Type type = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RateLimit.class).value();
        // 提交的限制
        if (type == RateLimit.Type.SUBMIT) {
            Long expireTime = redisUtil.getExpireTime(RedisKeyConstant.JUDGE_LAST + user.getId());
            // 还在冷却时间内
            if (expireTime > 0) {
                throw new BusinessException(ResultEnum.FAIL, "提交过于频繁，请等" + expireTime + "s后再试");
            }
            // 冷却五秒
            SubmissionDTO submissionDTO = null;
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg instanceof SubmissionDTO) {
                    submissionDTO = (SubmissionDTO) arg;
                }
            }
            redisUtil.set(RedisKeyConstant.JUDGE_LAST + user.getId(), submissionDTO, 5L);
            return joinPoint.proceed();
        }
        // 调试的限制
        else {
            Long expireTime = redisUtil.getExpireTime(RedisKeyConstant.DEBUG_LAST + user.getId());
            // 还在冷却时间内
            if (expireTime > 0) {
                throw new BusinessException(ResultEnum.FAIL, "调试过于频繁，请等" + expireTime + "s后再试");
            }
            // 冷却好了，设置冷却时间：(测试数量 + 1)*2s
            int testCount = 0;
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg instanceof DebugDTO) {
                    DebugDTO debugDTO = (DebugDTO) arg;
                    testCount = debugDTO.getUserInputList().size();
                }
            }
            redisUtil.set(RedisKeyConstant.DEBUG_LAST + user.getId(), testCount, testCount * 2L + 2);
            return joinPoint.proceed();
        }
    }
}
