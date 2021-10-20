package cn.cy.sync.config;

import cn.cy.sync.util.Threads;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程配置
 *
 * @Author 开水白菜
 */
@Configuration
@EnableConfigurationProperties(ThreadPool.class)
public class ThreadPoolConfig {

    /**
     * 并发、异步线程池
     */
    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(ThreadPool pool) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(pool.getMaxPoolSize());
        executor.setCorePoolSize(pool.getCorePoolSize());
        executor.setQueueCapacity(pool.getQueueCapacity());
        executor.setKeepAliveSeconds(pool.getKeepAliveSeconds());
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    /**
     * 执行周期性或定时任务
     *
     * @param pool
     * @return
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService(ThreadPool pool) {
        return new ScheduledThreadPoolExecutor(pool.getCorePoolSize(), new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                Threads.printException(r, t);
            }
        };
    }
}
