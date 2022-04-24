package cn.cy.dtc.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @author: you
 * @date: 2022-04-23 19:50
 * @description: 动态线程池上下文持有者，用 ttl 包装 ThreadLocal。
 */
public class DtpContextHolder {
    private static final TransmittableThreadLocal<DtpContext> CONTEXT = new TransmittableThreadLocal<>();

    private DtpContextHolder() {
    }

    public static void set(DtpContext dtpContext) {
        CONTEXT.set(dtpContext);
    }

    public static DtpContext get() {
        return CONTEXT.get();
    }

    public static void remove() {
        CONTEXT.remove();
    }
}
