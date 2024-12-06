package fun.kylen.koj.config;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import fun.kylen.koj.utils.LogUtil;
import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: KyLen
 * @Date: 2024/12/4 16:10
 * @Description:
 */
@Configuration
public class JudgeAsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        // Java虚拟机可用的处理器数
        int processors = Runtime.getRuntime().availableProcessors();
        // 定义线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(processors);
        // 线程池最大线程数
        taskExecutor.setMaxPoolSize(processors);
        // 线程队列最大线程数
        taskExecutor.setQueueCapacity(200 * processors);
        // 线程名称前缀
        taskExecutor.setThreadNamePrefix("JudgeAsync-");
        // 线程池中线程最大空闲时间，默认：60，单位：秒
        taskExecutor.setKeepAliveSeconds(1);
        // 核心线程是否允许超时，默认:false
        taskExecutor.setAllowCoreThreadTimeOut(false);
        // IOC容器关闭时是否阻塞等待剩余的任务执行完成，默认:false（必须设置setAwaitTerminationSeconds）
        taskExecutor.setWaitForTasksToCompleteOnShutdown(false);
        // 阻塞IOC容器关闭的时间，默认：10秒（必须设置setWaitForTasksToCompleteOnShutdown）
        taskExecutor.setAwaitTerminationSeconds(0);
        /*
          拒绝策略，默认是AbortPolicy
          AbortPolicy：丢弃任务并抛出RejectedExecutionException异常
          DiscardPolicy：丢弃任务但不抛出异常
          DiscardOldestPolicy：丢弃最旧的处理程序，然后重试，如果执行器关闭，这时丢弃任务
          CallerRunsPolicy：执行器执行任务失败，则在策略回调方法中执行任务，如果执行器关闭，这时丢弃任务
         */
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        taskExecutor.initialize();

        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            LogUtil.error("Exception message -> " + throwable.getMessage());
        };
    }
}
