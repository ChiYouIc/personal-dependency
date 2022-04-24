package cn.cy.dtc.common.dto;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author: you
 * @date: 2022-04-23 19:55
 * @description:
 */
@Data
public class DtpMainProp {

    private static final List<Field> FIELD_NAMES;

    static {
        FIELD_NAMES = Arrays.asList(DtpMainProp.class.getDeclaredFields());
    }

    /**
     * 动态线程池名称
     */
    private String dtpName;

    /**
     * 核心线程池大小
     */
    private int corePoolSize;

    /**
     * 线程池最大大小
     */
    private int maxPoolSize;

    /**
     * 线程存活时间
     */
    private long keepAliveTime;

    /**
     * 阻塞队列类型
     */
    private String queueType;

    /**
     * 阻塞队列容量
     */
    private int queueCapacity;

    /**
     * 拒绝策略类型
     */
    private String rejectType;

    /**
     * 是否允许核心线程超时
     */
    private boolean allowCoreThreadTimeOut;

    public static List<Field> getMainProps() {
        return FIELD_NAMES;
    }
}
