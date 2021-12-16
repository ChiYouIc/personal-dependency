package cn.cy.sync;

import cn.cy.sync.async.AsyncExecutor;
import cn.cy.sync.schedule.ScheduleExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 确保应用退出时能关闭后台线程
 *
 * @author luopeng
 */
@Slf4j
@Component
public class ShutdownManager implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Initializing thread pool shutdown manager");
    }

    @Override
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
