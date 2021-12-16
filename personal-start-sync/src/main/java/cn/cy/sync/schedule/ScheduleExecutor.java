package cn.cy.sync.schedule;

import cn.cy.common.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * @Author: 友叔
 * @Date: 2021/12/16 15:05
 * @Description: 定时周期线程执行
 */
@Slf4j
public class ScheduleExecutor {
	private static final ScheduleExecutor me = new ScheduleExecutor();

	private final ScheduledExecutorService executor = SpringUtil.getBean("scheduledExecutorService", ScheduledExecutorService.class);

	private ScheduleExecutor() {

	}

	public static ScheduleExecutor me() {
		return me;
	}

	public void shutdown() {
		log.info("ScheduleExecutorService shutdown");
		executor.shutdown();
	}

	/**
	 * 执行任务
	 *
	 * @param task 任务
	 */
	public void execute(TimerTask task) {
		executor.execute(task);
	}

	public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
		return executor.schedule(command, delay, unit);
	}

	public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
		return executor.schedule(callable, delay, unit);
	}

	public void execute(Runnable command) {
		executor.execute(command);
	}

	public Future<?> submit(Runnable task) {
		return executor.submit(task);
	}

	public <T> Future<T> submit(Runnable task, T result) {
		return executor.submit(task, result);
	}

	public <T> Future<T> submit(Callable<T> task) {
		return executor.submit(task);
	}

	public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		return executor.scheduleAtFixedRate(command, initialDelay, period, unit);
	}

	public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		return executor.scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}

}
