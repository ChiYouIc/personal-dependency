package cn.cy.dtc.core.reject;

import cn.cy.dtc.common.em.NotifyTypeEnum;
import cn.cy.dtc.core.notify.AlarmManager;
import cn.cy.dtc.core.thread.DtpExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: you
 * @date: 2022-04-23 16:25
 * @description:
 */
public interface RejectedAware {

    /**
     * 在拒绝策略执行前执行
     *
     * @param executor 线程池执行器
     */
    public default void beforeReject(ThreadPoolExecutor executor) {
        if (executor instanceof DtpExecutor) {
            DtpExecutor dtpExecutor = (DtpExecutor) executor;
            dtpExecutor.incRejectCount(1);
            Runnable runnable = () -> AlarmManager.doAlarm(dtpExecutor, NotifyTypeEnum.REJECT);
            AlarmManager.triggerAlarm(dtpExecutor.getThreadPoolName(), NotifyTypeEnum.REJECT.getValue(), runnable);
        }
    }

}
