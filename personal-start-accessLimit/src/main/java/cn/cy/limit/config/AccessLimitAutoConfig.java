package cn.cy.limit.config;

import cn.cy.limit.idemp.IdempotentLimiterAspect;
import cn.cy.limit.idemp.service.IIdempotentLimitService;
import cn.cy.limit.rate.RateLimiterAspect;
import cn.cy.redis.service.IRedisScriptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 友叔
 * @Date: 2021/7/22 11:15
 * @Description: 配置
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = AccessLimitAutoConfig.BASE_PACKAGE)
public class AccessLimitAutoConfig implements WebMvcConfigurer {

    protected static final String BASE_PACKAGE = "cn.cy.limit";

    @Bean
    public RateLimiterAspect rateLimiterAspect(@Autowired(required = false) IRedisScriptService redisScriptService) {
        if (redisScriptService == null) {
            log.warn("No implementation class for " + IRedisScriptService.class.getName() + " was found");
            return null;
        }
        return new RateLimiterAspect(redisScriptService);
    }

    @Bean
    public IdempotentLimiterAspect idempotentLimiterAspect(@Autowired(required = false) IIdempotentLimitService<?> limitService) {
        if (limitService == null) {
            log.warn("No implementation class for " + IIdempotentLimitService.class.getName() + " was found");
            return null;
        }
        return new IdempotentLimiterAspect(limitService);
    }

}
