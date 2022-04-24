package cn.cy.dtc.core.notify;

import cn.cy.dtc.common.dto.NotifyItem;
import cn.cy.dtc.common.em.NotifyTypeEnum;
import cn.cy.dtc.core.thread.DtpExecutor;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author: you
 * @date: 2022-04-23 16:31
 * @description: 报警限制器
 */
public class AlarmLimiter {

    /**
     * 缓存
     */
    private static final Map<String, Cache<String, String>> ALARM_LIMITER = new ConcurrentHashMap<>();

    private AlarmLimiter() {
    }

    /**
     * 初始化限制器
     * <p>配置变更通知不需要使用到限制器
     * <p>使用延迟缓存，默认延迟 120 秒 {@link NotifyItem#interval}
     *
     * @param dtpName    线程池名称
     * @param notifyItem 通知条目
     */
    public static void initAlarmLimiter(String dtpName, NotifyItem notifyItem) {

        // 配置更改通知不做限制
        if (NotifyTypeEnum.CHANGE.getValue().equalsIgnoreCase(notifyItem.getType())) {
            return;
        }

        String outerKey = buildOuterKey(dtpName, notifyItem.getType());

        Cache<String, String> cache = CacheBuilder.newBuilder()
                // 延迟缓存，
                .expireAfterWrite(notifyItem.getInterval(), TimeUnit.SECONDS)
                .build();
        ALARM_LIMITER.put(outerKey, cache);
    }

    /**
     * @param executor
     * @param type
     */
    public static void putVal(DtpExecutor executor, String type) {
        String outerKey = buildOuterKey(executor.getThreadPoolName(), type);
        ALARM_LIMITER.get(outerKey).put(type, type);
    }

    /**
     * 获取报警限值信息
     *
     * @param outerKey 外键，对应的是 dtcName
     * @param innerKey 内键，对应的是 NotifyType
     * @return 类型
     */
    public static String getAlarmLimitInfo(String outerKey, String innerKey) {
        return ALARM_LIMITER.get(outerKey).getIfPresent(innerKey);
    }

    /**
     * 是否需要报警，如果限制器中存在该执行器的对应的通知类型，则需要进行报警通知
     *
     * @param executor 执行器
     * @param type     通知类型
     * @return boolean 是否存在
     */
    public static boolean ifAlarm(DtpExecutor executor, String type) {
        String key = buildOuterKey(executor.getThreadPoolName(), type);
        return StringUtils.isBlank(getAlarmLimitInfo(key, type));
    }

    /**
     * 构建缓存key
     *
     * @param dtpName 动态线程池名称
     * @param type    通知类型
     * @return 缓存的外键
     */
    public static String buildOuterKey(String dtpName, String type) {
        return dtpName + ":" + type;
    }
}
