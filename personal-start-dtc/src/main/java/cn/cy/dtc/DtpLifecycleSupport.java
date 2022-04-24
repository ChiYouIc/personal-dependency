package cn.cy.dtc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.*;

/**
 * 动态线程池生命周期支持
 *
 * @author: you
 * @date: 2022-04-23 14:41
 * @description: 通过继承 {@link ThreadPoolExecutor} 对 ThreadPoolExecutor 进行扩展；实现 {@link DisposableBean} 是为了介入线程池关闭时的行为
 * @see java.util.concurrent.ThreadPoolExecutor
 * @see org.springframework.beans.factory.DisposableBean
 */
@Slf4j
public class DtpLifecycleSupport extends ThreadPoolExecutor implements DisposableBean {

    /**
     * 线程池名称
     */
    protected String threadPoolName;

    /**
     * 是否在关机时等待计划任务完成，不中断正在运行的任务并执行队列中的所有任务。
     */
    protected boolean waitForTasksToCompleteOnShutdown = false;

    /**
     * <p>此执行程序应该在关闭时阻止的最大秒数，以便在容器的其余部分继续关闭之前等待剩余任务完成执行。
     *
     * @see ThreadPoolExecutor#awaitTermination(long, TimeUnit)
     */
    protected int awaitTerminationSeconds = 0;

    public DtpLifecycleSupport(int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime,
                               TimeUnit unit,
                               BlockingQueue<Runnable> workQueue,
                               ThreadFactory threadFactory,
                               RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public DtpLifecycleSupport(int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime,
                               TimeUnit unit,
                               BlockingQueue<Runnable> workQueue,
                               ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public void setWaitForTasksToCompleteOnShutdown(boolean waitForTasksToCompleteOnShutdown) {
        this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
    }

    public void setAwaitTerminationSeconds(int awaitTerminationSeconds) {
        this.awaitTerminationSeconds = awaitTerminationSeconds;
    }

    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    public String getThreadPoolName() {
        return threadPoolName;
    }

    @Override
    public void destroy() throws Exception {
        internalShutdown();
    }

    /**
     * 对底层 ExecutorService 执行关闭。
     *
     * @see java.util.concurrent.ExecutorService#shutdown()
     * @see java.util.concurrent.ExecutorService#shutdownNow()
     */
    public void internalShutdown() {
        log.info("Shutting down ExecutorService, poolName: {}", threadPoolName);
        if (this.waitForTasksToCompleteOnShutdown) {
            this.shutdown();
        } else {
            for (Runnable remainingTask : this.shutdownNow()) {
                cancelRemainingTask(remainingTask);
            }
        }
        awaitTerminationIfNecessary();
    }

    /**
     * 取消从 {@link ExecutorService#shutdownNow()} 返回的从未推荐执行的给定剩余任务。
     *
     * @param task 要取消的任务 (通常是一个 {@link RunnableFuture})
     * @see #shutdown()
     * @see RunnableFuture#cancel(boolean)
     * @since 5.0.5
     */
    protected void cancelRemainingTask(Runnable task) {
        if (task instanceof Future) {
            ((Future<?>) task).cancel(true);
        }
    }

    /**
     * 等待程序终止，依赖于对 {@link #setAwaitTerminationSeconds "awaitTerminationSeconds"} 的给定参数值 {@link #awaitTerminationSeconds}.
     */
    private void awaitTerminationIfNecessary() {
        if (this.awaitTerminationSeconds <= 0) {
            return;
        }
        try {
            if (!awaitTermination(this.awaitTerminationSeconds, TimeUnit.SECONDS)) {
                log.warn("Timed out while waiting for executor {} to terminate", threadPoolName);
            }
        } catch (InterruptedException ex) {
            log.warn("Interrupted while waiting for executor {} to terminate", threadPoolName);
            Thread.currentThread().interrupt();
        }
    }
}
