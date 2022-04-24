package cn.cy.dtc.core.notify;

import cn.cy.dtc.common.dto.AlarmInfo;
import cn.cy.dtc.common.em.NotifyTypeEnum;
import lombok.val;
import lombok.var;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static cn.cy.dtc.common.constant.DynamicTpConst.UNKNOWN;
import static cn.cy.dtc.common.em.NotifyTypeEnum.*;


/**
 * 报警计数器
 *
 * @author: you
 * @date: 2022-04-23 16:31
 * @description: 报警计数器
 */
public class AlarmCounter {

    private AlarmCounter() {
    }

    /**
     * 报警信息缓存器
     */
    private static final Map<String, AlarmInfo> ALARM_INFO_CACHE = new ConcurrentHashMap<>();

    /**
     * 初始化报警计数器
     *
     * @param dtpName    动态线程池名称
     * @param notifyType 通知类型
     */
    public static void init(String dtpName, String notifyType) {
        String key = buildKey(dtpName, notifyType);
        val alarmInfo = AlarmInfo.builder()
                .type(NotifyTypeEnum.of(notifyType))
                .build();
        ALARM_INFO_CACHE.putIfAbsent(key, alarmInfo);
    }

    /**
     * 获取报警对象
     *
     * @param dtpName    动态线程池名称
     * @param notifyType 通知类型
     * @return
     */
    public static AlarmInfo getAlarmInfo(String dtpName, String notifyType) {
        String key = buildKey(dtpName, notifyType);
        return ALARM_INFO_CACHE.get(key);
    }

    /**
     * 重置报警对象
     *
     * @param dtpName    动态线程池名称
     * @param notifyType 通知类型
     */
    public static void reset(String dtpName, String notifyType) {
        String key = buildKey(dtpName, notifyType);
        var alarmInfo = ALARM_INFO_CACHE.get(key);
        alarmInfo.reset();
    }

    /**
     * 报警计数 +1
     *
     * @param dtpName    动态线程池名称
     * @param notifyType 通知类型
     */
    public static void incAlarmCounter(String dtpName, String notifyType) {
        String key = buildKey(dtpName, notifyType);
        var alarmInfo = ALARM_INFO_CACHE.get(key);
        if (Objects.nonNull(alarmInfo)) {
            alarmInfo.incCounter();
        }
    }

    public static Triple<String, String, String> countNotifyItems(String dtpName) {
        val rejectAlarm = getAlarmInfo(dtpName, REJECT.getValue());
        String rejectCount = rejectAlarm == null ? UNKNOWN : String.valueOf(rejectAlarm.getCount());

        val runTimeoutAlarm = getAlarmInfo(dtpName, RUN_TIMEOUT.getValue());
        String runTimeoutCount = runTimeoutAlarm == null ? UNKNOWN : String.valueOf(runTimeoutAlarm.getCount());

        val queueTimeoutAlarm = getAlarmInfo(dtpName, QUEUE_TIMEOUT.getValue());
        String queueTimeoutCount = queueTimeoutAlarm == null ? UNKNOWN : String.valueOf(queueTimeoutAlarm.getCount());

        return new ImmutableTriple<>(rejectCount, runTimeoutCount, queueTimeoutCount);
    }

    private static String buildKey(String dtpName, String notifyType) {
        return dtpName + ":" + notifyType;
    }
}
