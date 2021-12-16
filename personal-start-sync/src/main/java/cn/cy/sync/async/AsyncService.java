package cn.cy.sync.async;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @Author: 友叔
 * @Date: 2021/12/16 11:22
 * @Description: 异步任务执行接口
 */
public interface AsyncService {

	/**
	 * 执行异步任务
	 */
	public void executeAsync();

	/**
	 * 执行异步任务
	 *
	 * @param param    参数
	 * @param consumer 函数式接口
	 * @param <T>      参数类型
	 */
	public <T> void executeAsync(T param, Consumer<T> consumer);

	/**
	 * 执行异步任务
	 *
	 * @param param1   参数1
	 * @param param2   参数2
	 * @param consumer 函数式接口
	 * @param <T>      参数类型
	 * @param <U>      参数类型
	 */
	public <T, U> void executeAsync(T param1, U param2, BiConsumer<T, U> consumer);
}
