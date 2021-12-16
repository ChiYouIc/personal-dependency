package cn.cy.sync.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: 友叔
 * @Date: 2021/12/16 15:28
 * @Description:
 */
@Setter
@Getter
@ConfigurationProperties(prefix = ScheduleProperties.PREFIX)
public class ScheduleProperties {

	protected static final String PREFIX = "thread.schedule";

	/**
	 * 线程的名称前缀
	 */
	private String namePrefix = "schedule-async-%d";

	/**
	 * 核心线程池大小
	 */
	private int corePoolSize = 50;

	/**
	 * 最大可创建的线程数
	 */
	private int maxPoolSize = 200;

	/**
	 * 线程池维护线程所允许的空闲时间
	 */
	private int keepAliveSeconds = 3600;
}
