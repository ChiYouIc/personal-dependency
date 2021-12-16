package cn.cy.sync.async;

import cn.cy.common.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @Author: 友叔
 * @Date: 2021/12/16 14:33
 * @Description: 异步执行器
 */
@Slf4j
public class AsyncExecutor {

	private static AsyncExecutor me;

	private final ThreadPoolTaskExecutor executor = SpringUtil.getBean("threadPoolTaskExecutor", ThreadPoolTaskExecutor.class);

	private AsyncExecutor() {

	}

	/**
	 * 获取实例
	 *
	 * @return 异步执行器实例
	 */
	public static AsyncExecutor me() {
		if (me == null) {
			me = new AsyncExecutor();
		}
		return me;
	}

	/**
	 * 执行一个任务
	 *
	 * @param task 任务
	 */
	public void execute(Runnable task) {
		executor.execute(task);
	}

	/**
	 * 执行一个任务
	 *
	 * @param task         任务
	 * @param startTimeout 未知
	 */
	public void execute(Runnable task, long startTimeout) {
		executor.execute(task, startTimeout);
	}

	public Future<?> submit(Runnable task) {
		return executor.submit(task);
	}

	public <T> Future<T> submit(Callable<T> task) {
		return executor.submit(task);
	}

	public ListenableFuture<?> submitListenable(Runnable task) {
		return executor.submitListenable(task);
	}

	public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
		return executor.submitListenable(task);
	}

	/**
	 * 关闭线程池
	 */
	public void shutdown() {
		log.info("ThreadPoolTaskExecutor shutdown");
		executor.shutdown();
	}

}
