package cn.cy.sync.config;

import cn.cy.sync.async.VisibleThreadPoolTaskExecutor;
import cn.cy.sync.schedule.VisibleScheduledThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程配置
 *
 * @Author 开水白菜
 * @Description: 线程池配置
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({AsyncProperties.class, ScheduleProperties.class})
public class ThreadPoolConfig {

    /**
     * 并发、异步线程池
     */
    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(AsyncProperties async) {
        log.info("Create threadPoolTaskExecutor");

        ThreadPoolTaskExecutor executor = new VisibleThreadPoolTaskExecutor();

        // 最大线程数
        executor.setMaxPoolSize(async.getMaxPoolSize());

        // 核心线程数
        executor.setCorePoolSize(async.getCorePoolSize());

        // 队列容量
        executor.setQueueCapacity(async.getQueueCapacity());

        // 保持活动秒数
        executor.setKeepAliveSeconds(async.getKeepAliveSeconds());

        // 配置线程的名称前缀
        executor.setThreadNamePrefix(async.getNamePrefix());

        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();
        return executor;
    }

    /**
     * 执行周期性或定时任务
     *
     * @param schedule 配置
     * @return 周期性定时任务执行器
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService(ScheduleProperties schedule) {
        log.info("Create ScheduledExecutorService");

        VisibleScheduledThreadPoolExecutor executor = new VisibleScheduledThreadPoolExecutor(schedule.getCorePoolSize());

        // 创建新线程的线程工厂
        executor.setThreadFactory(new BasicThreadFactory.Builder().namingPattern("schedule-async-%d").daemon(true).build());

        // 允许的最大线程数
        executor.setMaximumPoolSize(schedule.getMaxPoolSize());

        // 线程在终止之前可以保持空闲的时间限制
        executor.setKeepAliveTime(schedule.getKeepAliveSeconds(), TimeUnit.MICROSECONDS);

        return executor;
    }
}
