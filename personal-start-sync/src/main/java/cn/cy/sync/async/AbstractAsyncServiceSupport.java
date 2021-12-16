package cn.cy.sync.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @Author: 友叔
 * @Date: 2021/12/16 11:25
 * @Description: 执行异步任务支持类
 */
@Slf4j
public abstract class AbstractAsyncServiceSupport implements AsyncService {

	@Override
	@Async("threadPoolTaskExecutor")
	public <T> void executeAsync(T param, Consumer<T> consumer) {
		log.info("ExecuteAsync params [{}]", param);
		consumer.accept(param);
	}

	@Override
	@Async("threadPoolTaskExecutor")
	public <T, U> void executeAsync(T param1, U param2, BiConsumer<T, U> consumer) {
		log.info("ExecuteAsync params [{} {}]", param1, param2);
		consumer.accept(param1, param2);
	}
}
