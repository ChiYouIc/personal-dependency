package cn.cy.dtc.core.reject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: you
 * @date: 2022-04-23 16:24
 * @description: 拒绝调用处理程序
 */
public class RejectedInvocationHandler implements InvocationHandler, RejectedAware {

    private final Object target;

    public RejectedInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) args[1];
            beforeReject(executor);

            return method.invoke(target, args);
        } catch (InvocationTargetException ex) {
            throw ex.getCause();
        }
    }
}
