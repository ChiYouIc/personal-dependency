package cn.cy.dtc.core.notify;

import cn.cy.dtc.common.ApplicationContextHolder;
import cn.cy.dtc.common.config.DtpProperties;
import cn.cy.dtc.common.dto.AlarmInfo;
import cn.cy.dtc.common.dto.NotifyItem;
import cn.cy.dtc.common.em.NotifyTypeEnum;
import cn.cy.dtc.common.em.QueueTypeEnum;
import cn.cy.dtc.common.em.RejectedTypeEnum;
import cn.cy.dtc.core.context.DtpContext;
import cn.cy.dtc.core.context.DtpContextHolder;
import cn.cy.dtc.core.handler.NotifierHandler;
import cn.cy.dtc.core.support.ThreadPoolBuilder;
import cn.cy.dtc.core.thread.DtpExecutor;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * 报警管理器
 *
 * @author: you
 * @date: 2022-04-23 16:29
 * @description: 报警管理器
 */
@Slf4j
public class AlarmManager {
    /**
     * 报警管理器线程池
     */
    private static final ExecutorService ALARM_EXECUTOR = ThreadPoolBuilder.newBuilder()
            .threadPoolName("dtp-alarm")
            .threadFactory("dtp-alarm")
            .corePoolSize(2)
            .maximumPoolSize(4)
            .workQueue(QueueTypeEnum.LINKED_BLOCKING_QUEUE.getName(), 2000, false)
            .rejectedExecutionHandler(RejectedTypeEnum.DISCARD_OLDEST_POLICY.getName())
            .dynamic(false)
            .buildWithTtl();

    private AlarmManager() {
    }

    /**
     * 触发报警
     *
     * @param dtpName    动态线程池名称
     * @param notifyType 通知类型
     * @param runnable   任务
     */
    public static void triggerAlarm(String dtpName, String notifyType, Runnable runnable) {
        AlarmCounter.incAlarmCounter(dtpName, notifyType);
        ALARM_EXECUTOR.execute(runnable);
    }

    /**
     * 触发报警任务
     *
     * @param runnable 任务
     */
    public static void triggerAlarm(Runnable runnable) {
        ALARM_EXECUTOR.execute(runnable);
    }

    /**
     * 执行报警
     *
     * @param executor  执行器
     * @param typeEnums 通知类型列表
     */
    public static void doAlarm(DtpExecutor executor, List<NotifyTypeEnum> typeEnums) {
        typeEnums.forEach(x -> doAlarm(executor, x));
    }

    /**
     * 执行报警
     *
     * @param executor 执行器
     * @param typeEnum 通知类型枚举
     */
    public static void doAlarm(DtpExecutor executor, NotifyTypeEnum typeEnum) {

        // 通知类型前置检查
        if (!preCheck(executor, typeEnum)) {
            return;
        }

        // 是否需要进行报警通知
        boolean ifAlarm = AlarmLimiter.ifAlarm(executor, typeEnum.getValue());
        if (!ifAlarm) {
            log.debug("DynamicTp notify, alarm limit, dtpName: {}, type: {}", executor.getThreadPoolName(), typeEnum.getValue());
            return;
        }

        // dtp 配置
        DtpProperties dtpProperties = ApplicationContextHolder.getBean(DtpProperties.class);

        // 从 NotifyHelper 中获取通知条目
        NotifyItem notifyItem = NotifyHelper.getNotifyItem(executor, typeEnum);

        if (ObjectUtil.isNull(notifyItem)) {
            return;
        }

        // 获取报警对象
        AlarmInfo alarmInfo = AlarmCounter.getAlarmInfo(executor.getThreadPoolName(), notifyItem.getType());

        // 构建 dtp 上下文
        DtpContext dtpContext = DtpContext.builder()
                .dtpExecutor(executor)
                .platforms(dtpProperties.getPlatforms())
                .notifyItem(notifyItem)
                .alarmInfo(alarmInfo)
                .build();

        // 配置上下文持有者
        DtpContextHolder.set(dtpContext);

        // 将执行器的通知类型存入报警限制器中
        AlarmLimiter.putVal(executor, typeEnum.getValue());

        // 发送通知，发送通知之后调用 DtpContextHolder.remove()
        NotifierHandler.getInstance().sendAlarm(typeEnum);

        // 重置该执行器的报警信息
        AlarmCounter.reset(executor.getThreadPoolName(), notifyItem.getType());
    }

    /**
     * 前置检查，针对通知类型，对执行器进行状态检查
     *
     * @param executor 执行器
     * @param typeEnum 通知类型枚举
     * @return boolean 是否通过检测
     */
    private static boolean preCheck(DtpExecutor executor, NotifyTypeEnum typeEnum) {
        switch (typeEnum) {
            case REJECT:
                return checkReject(executor);
            case CAPACITY:
                return checkCapacity(executor);
            case LIVENESS:
                return checkLiveness(executor);
            case RUN_TIMEOUT:
                return checkRunTimeout(executor);
            case QUEUE_TIMEOUT:
                return checkQueueTimeout(executor);
            default:
                log.error("Unsupported alarm type, type: {}", typeEnum);
                return false;
        }
    }

    /**
     * 检查是否需要推送线程池使用率通知 {@link NotifyTypeEnum#LIVENESS}
     *
     * @param executor 执行器
     * @return boolean
     */
    private static boolean checkLiveness(DtpExecutor executor) {

        NotifyItem notifyItem = NotifyHelper.getNotifyItem(executor, NotifyTypeEnum.LIVENESS);
        if (Objects.isNull(notifyItem)) {
            return false;
        }

        // 线程池最大容量
        int maximumPoolSize = executor.getMaximumPoolSize();

        // 线程池使用率
        double div = NumberUtil.div(executor.getActiveCount(), maximumPoolSize, 2) * 100;
        return satisfyBaseCondition(notifyItem) && div >= notifyItem.getThreshold();
    }

    /**
     * 检查是否需要推送容量阈值通知 {@link NotifyTypeEnum#CAPACITY}
     *
     * @param executor 执行器
     * @return boolean
     */
    private static boolean checkCapacity(DtpExecutor executor) {
        // 获取阻塞队列
        BlockingQueue<Runnable> workQueue = executor.getQueue();
        if (CollUtil.isEmpty(workQueue)) {
            return false;
        }

        NotifyItem notifyItem = NotifyHelper.getNotifyItem(executor, NotifyTypeEnum.CAPACITY);
        if (Objects.isNull(notifyItem)) {
            return false;
        }

        // 执行器当前阻塞队列容量
        int queueCapacity = executor.getQueueCapacity();

        // 阻塞队列使用率
        double div = NumberUtil.div(workQueue.size(), queueCapacity, 2) * 100;
        return satisfyBaseCondition(notifyItem) && div >= notifyItem.getThreshold();
    }

    /**
     * 检查是否需推送拒绝报警 {@link NotifyTypeEnum#REJECT}
     *
     * @param executor 执行器
     * @return boolean
     */
    private static boolean checkReject(DtpExecutor executor) {
        NotifyItem notifyItem = NotifyHelper.getNotifyItem(executor, NotifyTypeEnum.REJECT);
        if (Objects.isNull(notifyItem)) {
            return false;
        }

        // 获取拒绝报警信息
        AlarmInfo alarmInfo = AlarmCounter.getAlarmInfo(executor.getThreadPoolName(), notifyItem.getType());

        // 报警信息数量
        int rejectCount = alarmInfo.getCount();
        return satisfyBaseCondition(notifyItem) && rejectCount >= notifyItem.getThreshold();
    }

    /**
     * 检查是否需要推送运行超时报警 {@link NotifyTypeEnum#RUN_TIMEOUT}
     *
     * @param executor 执行器
     * @return boolean
     */
    private static boolean checkRunTimeout(DtpExecutor executor) {
        NotifyItem notifyItem = NotifyHelper.getNotifyItem(executor, NotifyTypeEnum.RUN_TIMEOUT);
        if (Objects.isNull(notifyItem)) {
            return false;
        }

        // 获取运行超时报警信息
        AlarmInfo alarmInfo = AlarmCounter.getAlarmInfo(executor.getThreadPoolName(), notifyItem.getType());

        // 报警信息数量
        int runTimeoutTaskCount = alarmInfo.getCount();
        return satisfyBaseCondition(notifyItem) && runTimeoutTaskCount >= notifyItem.getThreshold();
    }

    /**
     * 检查是否需要推送消息队列超时报警 {@link NotifyTypeEnum#QUEUE_TIMEOUT}
     *
     * @param executor 执行器
     * @return boolean
     */
    private static boolean checkQueueTimeout(DtpExecutor executor) {
        NotifyItem notifyItem = NotifyHelper.getNotifyItem(executor, NotifyTypeEnum.QUEUE_TIMEOUT);
        if (Objects.isNull(notifyItem)) {
            return false;
        }
        // 获取队列超时报警信息
        AlarmInfo alarmInfo = AlarmCounter.getAlarmInfo(executor.getThreadPoolName(), notifyItem.getType());

        // 报警信息数量
        int queueTimeoutTaskCount = alarmInfo.getCount();
        return satisfyBaseCondition(notifyItem) && queueTimeoutTaskCount >= notifyItem.getThreshold();
    }

    /**
     * 满足基本条件
     * <ul>
     *     <li>是否启用通知</li>
     *     <li>是否存在需要推送的平台</li>
     * </ul>
     *
     * @param notifyItem 通知条目
     * @return 是否满足基本条件
     */
    private static boolean satisfyBaseCondition(NotifyItem notifyItem) {
        return notifyItem.isEnabled() && CollUtil.isNotEmpty(notifyItem.getPlatforms());
    }
}
