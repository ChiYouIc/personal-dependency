package cn.cy.redis.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/5 11:39
 * @Description: Redis 属性配置，如果外部引用没有配置，则使用默认值
 */
@Getter
@Setter
@ConfigurationProperties(prefix = RedisProperties.PREFIX)
public class RedisProperties {

    public static final String PREFIX = "redis";

    private String database = "pw";

    private String admin = "admin";

    private String resourceList = "resourceList";

    private Long common = 86400L;

}
