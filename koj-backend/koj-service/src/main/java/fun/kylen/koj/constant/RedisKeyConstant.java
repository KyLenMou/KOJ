package fun.kylen.koj.constant;

/**
 * @Author: KyLen
 * @Date: 2024/8/4 23:35
 * @Description:
 */
public class RedisKeyConstant {
    public final static String EMAIL_REGISTER = "email:register:";


    // debugID
    public final static String DEBUG = "debug:";
    // 上次debug的userId，ttl是过期时间
    public final static String DEBUG_LAST = "debug:last:";
    // 上次submit的userId，ttl是过期时间
    public final static String JUDGE_LAST = "judge:last:";
}
