package fun.kylen.koj.constant;

/**
 * @Author: KyLen
 * @Date: 2024/9/20 21:45
 * @Description:
 */
public class JudgeStatusConstant {
    /**
     * 运行状态
     * 其中大于等于1表示正在RUNNING第几个测试点，
     * 如果ac了，那么就ac了
     * 如果wa了，在第几个RUNNING就表示在第几个测试点wa的
     */
    public final static int RUNNING = 1;

    /**
     * 提交状态
     * 0 正在排队评测
     * -1 提交失败
     */
    public final static int IN_QUEUE = 0;
    public final static int SUBMIT_FAIL = -1;


    /**
     * 编译状态
     * -100 编译中
     * -101 编译失败
     */
    public final static int COMPILING = -100;
    public final static int COMPILE_ERROR = -101;

    /**
     * 通过状态
     * -200 全对通过
     * -201 部分正确
     */
    public final static int ACCEPTED = -200;
    public final static int PARTIALLY_ACCEPTED = -201;

    /**
     * 非通过状态
     */
    public final static int WRONG_ANSWER = -300;
    public final static int TIME_LIMIT_EXCEEDED = -301;
    public final static int MEMORY_LIMIT_EXCEEDED = -302;
    public final static int STACK_LIMIT_EXCEEDED = -303;
    public final static int OUTPUT_LIMIT_EXCEEDED = -304;
    public final static int RUNTIME_ERROR = -305;

    /**
     * 系统状态
     * -400 系统错误
     * -401 提交被取消
     * -402 提交被无效
     */
    public final static int SYSTEM_ERROR = -400;
    public final static int CANCELLED = -401;
    public final static int REJECTED = -402;
}
