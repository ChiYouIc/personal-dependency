package cn.cy.dtc.common.dto;

import cn.cy.dtc.common.em.NotifyTypeEnum;
import cn.hutool.core.date.DateUtil;
import lombok.Builder;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: you
 * @date: 2022-04-23 16:34
 * @description: 告警信息
 */
@Data
@Builder
public class AlarmInfo {

    /**
     * 通知类型
     */
    private NotifyTypeEnum type;

    /**
     * 报警时间
     */
    private String lastAlarmTime;

    /**
     * 报警次数
     */
    private final AtomicInteger counter = new AtomicInteger(0);

    public void incCounter() {
        counter.incrementAndGet();
    }

    /**
     * 重置报警信息
     */
    public void reset() {
        lastAlarmTime = DateUtil.now();
        counter.set(0);
    }

    public int getCount() {
        return counter.get();
    }
}
