package cn.cy.dtc.common.config;

import cn.cy.dtc.common.config.web.JettyThreadPool;
import cn.cy.dtc.common.config.web.TomcatThreadPool;
import cn.cy.dtc.common.config.web.UndertowThreadPool;
import cn.cy.dtc.common.constant.DynamicTpConst;
import cn.cy.dtc.common.dto.NotifyPlatform;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author: you
 * @date: 2022-04-23 17:35
 * @description: 动态线程池配置
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = DynamicTpConst.MAIN_PROPERTIES_PREFIX)
public class DtpProperties {

    /**
     * If enabled DynamicTp.
     */
    private boolean enabled = true;

    /**
     * If print banner.
     */
    private boolean enabledBanner = true;

    /**
     * Config file type.
     */
    private String configType = "yml";

    /**
     * If enabled metrics collect.
     */
    private boolean enabledCollect = false;

    /**
     * Metrics collector type.
     */
    public String collectorType = "logging";

    /**
     * MetricsLog storage path
     */
    public String logPath;

    /**
     * Monitor interval, time unit（s）
     */
    private int monitorInterval = 5;

    /**
     * ThreadPoolExecutor configs.
     */
    private List<ThreadPoolProperties> executors;

    /**
     * Tomcat worker thread pool.
     */
    private TomcatThreadPool tomcatTp;

    /**
     * Jetty thread pool.
     */
    private JettyThreadPool jettyTp;

    /**
     * Undertow thread pool.
     */
    private UndertowThreadPool undertowTp;

    /**
     * Notify platform configs.
     */
    private List<NotifyPlatform> platforms;


}
