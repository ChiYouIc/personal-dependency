package cn.cy.dtc.common.em;

import lombok.Getter;

/**
 * 通知类型枚举
 *
 * @author: you
 * @date: 2022-04-23 16:46
 */
@Getter
public enum NotifyTypeEnum {
    /**
     * 配置更改通知
     */
    CHANGE("change"),

    /**
     * 线程池使用率通知。
     * <p> livenes = activeCount / maximumPoolSize
     */
    LIVENESS("liveness"),

    /**
     * 容量阈值通知。
     */
    CAPACITY("capacity"),

    /**
     * 拒绝通知。
     */
    REJECT("reject"),

    /**
     * 任务运行超时报警。
     */
    RUN_TIMEOUT("run_timeout"),

    /**
     * 任务队列等待超时告警。
     */
    QUEUE_TIMEOUT("queue_timeout");

    private final String value;

    NotifyTypeEnum(String value) {
        this.value = value;
    }

    public static NotifyTypeEnum of(String value) {
        for (NotifyTypeEnum typeEnum : NotifyTypeEnum.values()) {
            if (typeEnum.value.equals(value)) {
                return typeEnum;
            }
        }
        return null;
    }
}
