package cn.promptness.kpi.support.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : Lynn
 * @date : 2019-05-23 21:33
 */
@Configuration
public class AsyncConfig {

    /**
     * 线程池维护线程的核心线程数数量.
     * Set the ThreadPoolExecutor's core pool size.
     */
    private int corePoolSize = Runtime.getRuntime().availableProcessors() * 20;

    /**
     * 线程池维护线程的预警阀值数量
     * Set the ThreadPoolExecutor's maximum pool size.
     */
    private int maxPoolSize = Runtime.getRuntime().availableProcessors() * 40;

    /**
     * 持有等待执行的任务队列.
     * Set the capacity for the ThreadPoolExecutor's BlockingQueue.
     */
    private int queueCapacity = Runtime.getRuntime().availableProcessors() * 100;

    @Bean
    @Lazy
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        String threadNamePrefix = "send-email-executor-";
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }


}
