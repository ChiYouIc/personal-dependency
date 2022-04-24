package cn.cy.dtc.core.notify;

import cn.cy.dtc.common.config.DtpProperties;
import cn.cy.dtc.common.config.ThreadPoolProperties;
import cn.cy.dtc.common.dto.NotifyItem;
import cn.cy.dtc.common.dto.NotifyPlatform;
import cn.cy.dtc.common.em.NotifyTypeEnum;
import cn.cy.dtc.core.thread.DtpExecutor;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.*;
import java.util.stream.Collectors;

import static cn.cy.dtc.common.em.NotifyTypeEnum.*;
import static java.util.stream.Collectors.toList;

/**
 * @author: you
 * @date: 2022-04-23 16:32
 * @description: 消息通知助手
 */
@Slf4j
public class NotifyHelper {


    private static final List<String> COMMON_ALARM_KEYS = Lists.newArrayList("alarmType", "threshold");

    private static final Set<String> LIVENESS_ALARM_KEYS = Sets.newHashSet("corePoolSize", "maximumPoolSize", "poolSize", "activeCount");

    private static final Set<String> CAPACITY_ALARM_KEYS = Sets.newHashSet("queueType", "queueCapacity", "queueSize", "queueRemaining");

    private static final Set<String> REJECT_ALARM_KEYS = Sets.newHashSet("rejectType", "rejectCount");

    private static final Set<String> RUN_TIMEOUT_ALARM_KEYS = Sets.newHashSet("runTimeoutCount");

    private static final Set<String> QUEUE_TIMEOUT_ALARM_KEYS = Sets.newHashSet("queueTimeoutCount");

    private static final Set<String> ALL_ALARM_KEYS;

    private static final Map<String, Set<String>> ALARM_KEYS = Maps.newHashMap();

    static {
        ALARM_KEYS.put(LIVENESS.name(), LIVENESS_ALARM_KEYS);
        ALARM_KEYS.put(CAPACITY.name(), CAPACITY_ALARM_KEYS);
        ALARM_KEYS.put(REJECT.name(), REJECT_ALARM_KEYS);
        ALARM_KEYS.put(RUN_TIMEOUT.name(), RUN_TIMEOUT_ALARM_KEYS);
        ALARM_KEYS.put(QUEUE_TIMEOUT.name(), QUEUE_TIMEOUT_ALARM_KEYS);

        ALL_ALARM_KEYS = ALARM_KEYS.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
        ALL_ALARM_KEYS.addAll(COMMON_ALARM_KEYS);
    }

    private NotifyHelper() {
    }

    public static Set<String> getAllAlarmKeys() {
        return ALL_ALARM_KEYS;
    }

    public static Set<String> getAlarmKeys(NotifyTypeEnum typeEnum) {
        val keys = ALARM_KEYS.get(typeEnum.name());
        keys.addAll(COMMON_ALARM_KEYS);
        return keys;
    }

    /**
     * 获取通知条目
     *
     * @param dtpExecutor 执行器
     * @param typeEnum    通知类型枚举
     * @return 通知条目
     */
    public static NotifyItem getNotifyItem(DtpExecutor dtpExecutor, NotifyTypeEnum typeEnum) {
        // 获取执行器中所有的通知条目
        List<NotifyItem> notifyItems = dtpExecutor.getNotifyItems();

        // 过滤符合的通知条目
        val notifyItemOpt = notifyItems.stream()
                .filter(x -> typeEnum.getValue().equalsIgnoreCase(x.getType()) && x.isEnabled())
                .findFirst();

        if (!notifyItemOpt.isPresent()) {
            log.debug("DynamicTp notify, no such [{}] notify item configured, threadPoolName: {}", typeEnum.getValue(), dtpExecutor.getThreadPoolName());
            return null;
        }

        return notifyItemOpt.get();
    }

    /**
     * 设置通知平台
     *
     * @param platforms   平台
     * @param notifyItems 通知条目列表
     */
    public static void fillPlatforms(List<NotifyPlatform> platforms, List<NotifyItem> notifyItems) {
        if (CollUtil.isEmpty(platforms)) {
            log.warn("DynamicTp notify, no notify platforms configured.");
            return;
        }

        List<String> platformNames = platforms.stream().map(NotifyPlatform::getPlatform).collect(toList());

        for (NotifyItem item : notifyItems) {
            if (CollUtil.isEmpty(item.getPlatforms())) {
                item.setPlatforms(platformNames);
            }
        }
    }

    public static void updateNotifyItems(DtpExecutor executor, DtpProperties dtpProperties, ThreadPoolProperties tpp) {
        if (CollUtil.isEmpty(dtpProperties.getPlatforms())) {
            executor.setNotifyItems(Collections.emptyList());
            return;
        }

        fillPlatforms(dtpProperties.getPlatforms(), tpp.getNotifyItems());
        List<NotifyItem> oldNotifyItems = executor.getNotifyItems();
        Map<String, NotifyItem> oldNotifyItemMap = StreamUtil.toMap(oldNotifyItems, NotifyItem::getType);
        tpp.getNotifyItems().forEach(x -> {
            NotifyItem oldNotifyItem = oldNotifyItemMap.get(x.getType());
            if (Objects.nonNull(oldNotifyItem) && oldNotifyItem.getInterval() == x.getInterval()) {
                return;
            }
            AlarmLimiter.initAlarmLimiter(executor.getThreadPoolName(), x);
            AlarmCounter.init(executor.getThreadPoolName(), x.getType());
        });
        executor.setNotifyItems(tpp.getNotifyItems());
    }

}
