package cn.cy.sync;

import cn.cy.sync.async.AsyncExecutor;
import cn.cy.sync.schedule.ScheduleExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 确保应用退出时能关闭后台线程
 *
 * @author luopeng
 */
@Slf4j
@Component
public class ShutdownManager {

    @PreDestroy
    public void destroy() {
        shutdownAsyncManager();
    }

    /**
     * 停止异步执行任务
     */
    private void shutdownAsyncManager() {
        try {
            log.info("Close the background thread pool");
            AsyncExecutor.me().shutdown();
            ScheduleExecutor.me().shutdown();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
