package fun.kylen.koj.constant;

/**
 * @Author: KyLen
 * @Date: 2024/9/10 20:31
 * @Description:
 */
public class MqConstant {
    public final static String KOJ_EXCHANGE = "koj.judge.exchange";
    public static final String KOJ_DEAD_EXCHANGE = "koj.judge.dead.exchange";

    public final static String JUDGE_QUEUE = "koj.judge.queue";
    public final static String JUDGE_BINDING = "koj.judge.binding";
    public final static String JUDGE_ROUTE_KEY = "koj.judge";

    public static final String DEAD_JUDGE_ROUTE_KEY = "koj.judge.dead";
    public static final String DEAD_JUDGE_QUEUE = "koj.judge.dead.queue";
    public static final String DEAD_JUDGE_BINDING = "koj.judge.dead.binding";

    public final static String DEBUG_QUEUE = "koj.debug.queue";
    public final static String DEBUG_BINDING = "koj.debug.binding";
    public final static String DEBUG_ROUTE_KEY = "koj.debug";
}
