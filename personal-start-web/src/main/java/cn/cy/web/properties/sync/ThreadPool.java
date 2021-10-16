package cn.cy.web.properties.sync;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/25 14:56
 * @Description: 线程池配置
 */
@Setter
@Getter
public class ThreadPool {
    // 核心线程池大小
    private int corePoolSize = 50;

    // 最大可创建的线程数
    private int maxPoolSize = 200;

    // 队列最大长度
    private int queueCapacity = 1000;

    // 线程池维护线程所允许的空闲时间
    private int keepAliveSeconds = 300;
}