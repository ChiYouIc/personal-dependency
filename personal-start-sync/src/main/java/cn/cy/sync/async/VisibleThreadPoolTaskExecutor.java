package cn.cy.sync.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: 友叔
 * @Date: 2021/12/16 10:58
 * @Description: 查看线程池任务
 */
@Slf4j
public class VisibleThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

	/**
	 * 打印线程池中的任务总数、已完成数、活跃线程数，以及队列大小
	 *
	 * @param prefix 前缀
	 */
	private void showThreadPoolInfo(String prefix) {
		ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

		log.info("{},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
				prefix,
				threadPoolExecutor.getTaskCount(),
				threadPoolExecutor.getCompletedTaskCount(),
				threadPoolExecutor.getActiveCount(),
				threadPoolExecutor.getQueue().size());
	}

	@Override
	public void execute(Runnable task) {
		showThreadPoolInfo("execute(Runnable)");
		super.execute(task);
	}

	@Override
	public void execute(Runnable task, long startTimeout) {
		showThreadPoolInfo("execute(Runnable, long)");
		super.execute(task, startTimeout);
	}

	@Override
	public Future<?> submit(Runnable task) {
		showThreadPoolInfo("submit(Runnable)");
		return super.submit(task);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		showThreadPoolInfo("submit(Callable)");
		return super.submit(task);
	}

	@Override
	public ListenableFuture<?> submitListenable(Runnable task) {
		showThreadPoolInfo("submitListenable(Runnable)");
		return super.submitListenable(task);
	}

	@Override
	public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
		showThreadPoolInfo("submitListenable(Callable)");
		return super.submitListenable(task);
	}
}