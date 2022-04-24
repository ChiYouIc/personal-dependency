package cn.cy.dtc.core.reject;

import cn.cy.dtc.common.ex.DtpException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import static cn.cy.dtc.common.em.RejectedTypeEnum.*;


/**
 * 获取拒绝处理器
 *
 * @author: you
 * @date: 2022-04-23 16:21
 * @description: 对拒绝处理器进行代理封装
 */
@Slf4j
public class RejectHandlerGetter {

    private RejectHandlerGetter() {
    }

    /**
     * 根据名称创建拒绝处理器
     *
     * @param name 处理器名称
     * @return 拒绝策略执行器
     */
    public static RejectedExecutionHandler buildRejectedHandler(String name) {
        if (Objects.equals(name, ABORT_POLICY.getName())) {
            return new ThreadPoolExecutor.AbortPolicy();
        } else if (Objects.equals(name, CALLER_RUNS_POLICY.getName())) {
            return new ThreadPoolExecutor.CallerRunsPolicy();
        } else if (Objects.equals(name, DISCARD_OLDEST_POLICY.getName())) {
            return new ThreadPoolExecutor.DiscardOldestPolicy();
        } else if (Objects.equals(name, DISCARD_POLICY.getName())) {
            return new ThreadPoolExecutor.DiscardPolicy();
        }

        // 当不能匹配已有的拒绝策略执行器时，使用 SPI 机制加载执行器
        ServiceLoader<RejectedExecutionHandler> serviceLoader = ServiceLoader.load(RejectedExecutionHandler.class);
        for (RejectedExecutionHandler handler : serviceLoader) {
            String handlerName = handler.getClass().getSimpleName();
            if (name.equalsIgnoreCase(handlerName)) {
                return handler;
            }
        }

        log.error("Cannot find specified rejectedHandler {}", name);
        throw new DtpException("Cannot find specified rejectedHandler " + name);
    }

    public static RejectedExecutionHandler getProxy(String name) {
        return getProxy(buildRejectedHandler(name));
    }

    public static RejectedExecutionHandler getProxy(RejectedExecutionHandler handler) {
        return (RejectedExecutionHandler) Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                new Class[]{RejectedExecutionHandler.class},
                new RejectedInvocationHandler(handler));
    }

}
