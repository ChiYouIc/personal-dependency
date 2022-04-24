package cn.cy.dtc.common.dto;

import cn.cy.dtc.common.em.NotifyPlatformEnum;
import cn.cy.dtc.common.em.NotifyTypeEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知列表相关
 *
 * @author: you
 * @date: 2022-04-23 15:55
 * @description: 通知
 */
@Data
public class NotifyItem {
    /**
     * 需要通知的平台名称，表示这条通知条目信息需要向哪个平台推送
     *
     * @see NotifyPlatformEnum
     * @see cn.cy.dtc.core.handler.NotifierHandler#sendNotice(DtpMainProp, List)
     * @see cn.cy.dtc.core.handler.NotifierHandler#sendAlarm(NotifyTypeEnum)
     */
    private List<String> platforms;

    /**
     * 如果启用通知。
     */
    private boolean enabled = true;

    /**
     * 通知类型
     *
     * @see NotifyTypeEnum
     */
    private String type;

    /**
     * 报警阈值。
     */
    private int threshold;

    /**
     * 报警间隔，时间单位（s）
     */
    private int interval = 120;

    /**
     * 默认通知条目
     */
    private static final List<NotifyItem> DEFAULT_NOTIFY_ITEMS;

    static {
        // 配置更改通知
        NotifyItem changeNotify = new NotifyItem();
        changeNotify.setType(NotifyTypeEnum.CHANGE.getValue());

        // 线程使用率通知
        NotifyItem livenessNotify = new NotifyItem();
        livenessNotify.setType(NotifyTypeEnum.LIVENESS.getValue());
        livenessNotify.setThreshold(80);

        // 容量阈值通知
        NotifyItem capacityNotify = new NotifyItem();
        capacityNotify.setType(NotifyTypeEnum.CAPACITY.getValue());
        capacityNotify.setThreshold(80);

        // 任务拒绝通知
        NotifyItem rejectNotify = new NotifyItem();
        rejectNotify.setType(NotifyTypeEnum.REJECT.getValue());
        rejectNotify.setThreshold(1);

        // 运行超时通知
        NotifyItem runTimeoutNotify = new NotifyItem();
        runTimeoutNotify.setType(NotifyTypeEnum.RUN_TIMEOUT.getValue());
        runTimeoutNotify.setThreshold(1);

        // 队列超时通知
        NotifyItem queueTimeoutNotify = new NotifyItem();
        queueTimeoutNotify.setType(NotifyTypeEnum.QUEUE_TIMEOUT.getValue());
        queueTimeoutNotify.setThreshold(1);

        DEFAULT_NOTIFY_ITEMS = new ArrayList<>(6);
        DEFAULT_NOTIFY_ITEMS.add(livenessNotify);
        DEFAULT_NOTIFY_ITEMS.add(changeNotify);
        DEFAULT_NOTIFY_ITEMS.add(capacityNotify);
        DEFAULT_NOTIFY_ITEMS.add(rejectNotify);
        DEFAULT_NOTIFY_ITEMS.add(runTimeoutNotify);
        DEFAULT_NOTIFY_ITEMS.add(queueTimeoutNotify);
    }

    public static List<NotifyItem> getDefaultNotifyItems() {
        return DEFAULT_NOTIFY_ITEMS;
    }
}
