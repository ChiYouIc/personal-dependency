package cn.cy.dtc.common.em;

import lombok.Getter;

/**
 * @author: you
 * @date: 2022-04-23 16:17
 * @description: 拒绝类型枚举
 */
@Getter
public enum RejectedTypeEnum {
    /**
     * RejectedExecutionHandler type while triggering reject policy.
     */
    ABORT_POLICY("AbortPolicy"),

    CALLER_RUNS_POLICY("CallerRunsPolicy"),

    DISCARD_OLDEST_POLICY("DiscardOldestPolicy"),

    DISCARD_POLICY("DiscardPolicy");

    private final String name;

    RejectedTypeEnum(String name) {
        this.name = name;
    }
}
