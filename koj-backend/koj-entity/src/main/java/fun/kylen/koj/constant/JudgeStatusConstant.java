package fun.kylen.koj.constant;

/**
 * @Author: KyLen
 * @Date: 2024/9/20 21:45
 * @Description:
 */
public class JudgeStatusConstant {
    /**
     * 0 未评测/正在运行 仅用于在遇错止评下单个测试用例未评测的情况/正在判题中
     */
    public final static int NULL = 0;

    /**
     * 提交状态
     * 1 正在排队
     * -1 提交失败
     * 2 评测被取消
     * -2 评测被驳回
     */
    public final static int IN_QUEUE = 1;
    public final static int SUBMIT_FAIL = -1;
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
     * 通过状态
     * 200 全对通过
     * 201 部分正确
     */
    public final static int ACCEPTED = 200;
    public final static int PARTIALLY_ACCEPTED = 201;

    /**
     * 非通过状态
     */
    public final static int WRONG_ANSWER = 300;
    public final static int TIME_LIMIT_EXCEEDED = 301;
    public final static int MEMORY_LIMIT_EXCEEDED = 302;
    public final static int STACK_LIMIT_EXCEEDED = 303;
    public final static int OUTPUT_LIMIT_EXCEEDED = 304;
    public final static int RUNTIME_ERROR = 305;

    /**
     * 系统状态
     * 400 系统错误
     */
    public final static int SYSTEM_ERROR = 400;

}
