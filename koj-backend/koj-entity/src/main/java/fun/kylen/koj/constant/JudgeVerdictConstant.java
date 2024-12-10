package fun.kylen.koj.constant;

/**
 * @Author: KyLen
 * @Date: 2024/9/20 21:45
 * @Description:
 */
public class JudgeVerdictConstant {
    /**
     * 提交状态
     * 0 已提交(Submission)/未评测(SubmissionCase)
     * 1 正在排队
     * -1 提交失败
     * 2 评测被取消
     * -2 评测被驳回
     */
    public final static int NULL = 0;
    public final static int IN_QUEUE = 1;
    public final static int SUBMIT_FAILED = -1;
    public final static int CANCELLED = 2;
    public final static int REJECTED = -2;

    /**
     * 编译状态
     * 100 编译中
     * 101 编译失败
     */
    public final static int COMPILING = 100;
    public final static int COMPILE_ERROR = 101;

    /**
     * 运行和判题状态
     * 200 正在运行代码
     * 201 正在评测代码
     */
    public final static int RUNNING = 200;
    public final static int JUDGING = 201;

    /**
     * 通过状态
     * 300 全对通过
     * 301 部分正确
     */
    public final static int ACCEPTED = 300;
    public final static int PARTIALLY_ACCEPTED = 301;

    /**
     * 非通过状态
     */
    public final static int WRONG_ANSWER = 400;
    public final static int RUNTIME_ERROR = 401;
    public final static int TIME_LIMIT_EXCEEDED = 402;
    public final static int MEMORY_LIMIT_EXCEEDED = 403;
    public final static int STACK_LIMIT_EXCEEDED = 404;
    public final static int OUTPUT_LIMIT_EXCEEDED = 405;

    /**
     * 系统状态
     * 500 系统错误
     */
    public final static int SYSTEM_ERROR = 500;

}
