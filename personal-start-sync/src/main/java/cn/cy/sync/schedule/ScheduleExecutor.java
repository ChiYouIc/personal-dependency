package cn.cy.sync.schedule;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

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
		return me.schedule(command, delay, unit);
	}

	public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
		return me.schedule(callable, delay, unit);
	}

	public void execute(Runnable command) {
		me.execute(command);
	}

	public Future<?> submit(Runnable task) {
		return me.submit(task);
	}

	public <T> Future<T> submit(Runnable task, T result) {
		return me.submit(task, result);
	}

	public <T> Future<T> submit(Callable<T> task) {
		return me.submit(task);
	}

}
