package cn.cy.sync.schedule;

import cn.cy.sync.util.Threads;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Author: 友叔
 * @Date: 2021/12/16 15:11
 * @Description: 定期任务执行器
 */
@Slf4j
public class VisibleScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {

	public VisibleScheduledThreadPoolExecutor(int corePoolSize) {
		super(corePoolSize);
	}

	/**
	 * 在给定 Runnable 的执行完成后调用的方法。 该方法由执行任务的线程调用
	 *
	 * @param r 已完成的可运行
	 * @param t 导致终止的异常，如果执行正常完成，则为 null
	 */
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		Threads.printException(r, t);
	}

	public void showThreadPoolInfo(String prefix) {
		log.info("{}, taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
				prefix,
				getTaskCount(),
				getCompletedTaskCount(),
				getActiveCount(),
				getQueue().size());
	}

	@Override
	public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
		showThreadPoolInfo("schedule(Runnable, long, TimeUnit)");
		return super.schedule(command, delay, unit);
	}

	@Override
	public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
		showThreadPoolInfo("schedule(Callable, long, TimeUnit)");
		return super.schedule(callable, delay, unit);
	}

	@Override
	public void execute(Runnable command) {
		showThreadPoolInfo("execute(Runnable)");
		super.execute(command);
	}

	@Override
	public Future<?> submit(Runnable task) {
		showThreadPoolInfo("submit(Runnable)");
		return super.submit(task);
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		showThreadPoolInfo("submit(Runnable, T)");
		return super.submit(task, result);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		showThreadPoolInfo("submit(Callable)");
		return super.submit(task);
	}
}
