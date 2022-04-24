package cn.cy.dtc.core.support;

import cn.cy.dtc.common.VariableLinkedBlockingQueue;
import cn.cy.dtc.core.thread.EagerDtpExecutor;
import org.springframework.lang.NonNull;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author: you
 * @date: 2022-04-23 16:51
 * @description: 任务队列
 */
public class TaskQueue extends VariableLinkedBlockingQueue<Runnable> {

    private static final long serialVersionUID = -1L;

    private transient EagerDtpExecutor executor;

    public TaskQueue(int capacity) {
        super(capacity);
    }

    public void setExecutor(EagerDtpExecutor exec) {
        executor = exec;
    }

    @Override
    public boolean offer(@NonNull Runnable runnable) {
        if (executor == null) {
            throw new RejectedExecutionException("The task queue does not have executor.");
        }
        int currentPoolThreadSize = executor.getPoolSize();
        if (currentPoolThreadSize == executor.getMaximumPoolSize()) {
            return super.offer(runnable);
        }
        // 有空闲的 worker 线程。 将任务放入队列，让 worker 线程去处理任务。
        if (executor.getSubmittedTaskCount() < currentPoolThreadSize) {
            return super.offer(runnable);
        }
        // return false to let executor create new worker.
        // 返回一个 false 值使得执行器去创建一个新的 worker 线程
        if (currentPoolThreadSize < executor.getMaximumPoolSize()) {
            return false;
        }
        // currentPoolThreadSize >= max
        return super.offer(runnable);
    }

    /**
     * 强制性的向任务队列中添加任务
     *
     * @param o 任务
     * @return boolean 是否执行成功
     * @throws RejectedExecutionException 如果执行者被终止。
     */
    public boolean force(Runnable o, long timeout, TimeUnit unit) throws InterruptedException {
        if (executor.isShutdown()) {
            throw new RejectedExecutionException("Executor is shutdown.");
        }
        return super.offer(o, timeout, unit);
    }
}
