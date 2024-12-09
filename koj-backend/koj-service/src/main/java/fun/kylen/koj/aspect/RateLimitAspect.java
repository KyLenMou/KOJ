package fun.kylen.koj.aspect;

import fun.kylen.koj.common.BusinessException;
import fun.kylen.koj.common.ResultEnum;
import fun.kylen.koj.constant.RedisKeyConstant;
import fun.kylen.koj.model.oj.dto.DebugDTO;
import fun.kylen.koj.model.oj.vo.UserInfoVO;
import fun.kylen.koj.utils.PassportUtil;
import fun.kylen.koj.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Around("@annotation(fun.kylen.koj.annotation.RateLimit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        UserInfoVO user = PassportUtil.getCurrentUserIfLogin();
        Long expireTime = redisUtil.getExpireTime(RedisKeyConstant.DEBUG_LAST + user.getId());
        // 还在冷却时间内
        if (expireTime > 0) {
            throw new BusinessException(ResultEnum.FAIL, "请求过于频繁，请等" + expireTime + "s后再试");
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
