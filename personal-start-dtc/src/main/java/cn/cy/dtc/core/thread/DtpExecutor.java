package cn.cy.dtc.core.thread;

import cn.cy.dtc.DtpLifecycleSupport;
import cn.cy.dtc.common.dto.NotifyItem;
import cn.cy.dtc.core.support.wrapper.TaskWrapper;
import cn.cy.dtc.core.reject.RejectHandlerGetter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动态线程池执行器
 *
 * @author: you
 * @date: 2022-04-23 15:49
 * @description: Dynamic ThreadPoolExecutor 继承了 DtpLifecycleSupport，并做了一些扩展
 */
public class DtpExecutor extends DtpLifecycleSupport {

    /**
     * 计数拒绝的数量
     */
    private final AtomicInteger rejectCount = new AtomicInteger(0);

    /**
     * 拒绝策略名称
     */
    private String rejectHandlerName;

    /**
     * 通知条目
     *
     * @see NotifyItem
     */
    private List<NotifyItem> notifyItems;

    /**
     * 任务包装列表
     */
    private List<TaskWrapper> taskWrappers = new ArrayList<>();

    /**
     * 是否预启动所有核心线程
     */
    private boolean preStartAllCoreThreads;

    /**
     * 任务执行超时，单位（ms），仅用于统计。
     */
    private long runTimeout;

    /**
     * 任务队列等待超时，单位（ms），仅用于统计。
     */
    private long queueTimeout;

    /**
     * 计算在运行超时的任务数量。
     */
    private final AtomicInteger runTimeoutCount = new AtomicInteger();

    /**
     * 计数在队列中等待超时的任务数量。
     */
    private final AtomicInteger queueTimeoutCount = new AtomicInteger();

    public DtpExecutor(int corePoolSize,
                       int maximumPoolSize,
                       long keepAliveTime,
                       TimeUnit unit,
                       BlockingQueue<Runnable> workQueue,
                       ThreadFactory threadFactory,
                       RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);

        // 拒绝策略名称
        this.rejectHandlerName = handler.getClass().getSimpleName();

        // 获取拒绝策略的代理
        RejectedExecutionHandler rejectedExecutionHandler = RejectHandlerGetter.getProxy(handler);

        // 设置拒绝策略
        setRejectedExecutionHandler(rejectedExecutionHandler);

        // 与启动所有核心线程
        if (preStartAllCoreThreads) {
            prestartAllCoreThreads();
        }
    }
}
