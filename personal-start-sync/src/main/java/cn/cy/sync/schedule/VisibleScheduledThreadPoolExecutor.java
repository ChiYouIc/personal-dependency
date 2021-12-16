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

	/**
	 * <p>创建并执行一个周期性动作，在给定的初始延迟后首先启用，然后在给定的时间段内启用； 即执行将在initialDelay之后开始，
	 * 然后是initialDelay+period ，然后是initialDelay + 2 * period ，依此类推。 如果任务的任何执行遇到异常，则后续执行将被抑制。
	 * 否则，任务只会通过取消或终止执行程序而终止。 如果此任务的任何执行时间超过其周期，则后续执行可能会延迟开始，但不会并发执行。
	 *
	 * <p> 每隔 period 执行一次
	 *
	 * @param command      要执行的任务
	 * @param initialDelay 延迟第一次执行的时间
	 * @param period       连续执行之间的时间段
	 * @param unit         initialDelay 和 period 参数的时间单位
	 * @return 一个 ScheduledFuture 表示待完成的任务，其get()方法将在取消时抛出异常
	 */
	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		showThreadPoolInfo("scheduleAtFixedRate");
		return super.scheduleAtFixedRate(command, initialDelay, period, unit);
	}

	/**
	 * <p>创建并执行一个周期性动作，该动作首先在给定的初始延迟后启用，随后在一个执行终止和下一个执行开始之间具有给定的延迟。
	 * 如果任务的任何执行遇到异常，则后续执行将被抑制。 否则，任务只会通过取消或终止执行程序而终止
	 *
	 * <p> 上一次任务执行完毕之后，延迟 delay 执行下一次任务
	 *
	 * @param command      要执行的任务
	 * @param initialDelay 延迟第一次执行的时间
	 * @param delay        一个执行的终止和下一个执行的开始之间的延迟
	 * @param unit         initialDelay 和 period 参数的时间单位
	 * @return 一个 ScheduledFuture 表示待完成的任务，其get()方法将在取消时抛出异常
	 */
	@Override
	public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		showThreadPoolInfo("scheduleWithFixedDelay");
		return super.scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}
}
