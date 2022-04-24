package cn.cy.config;

import cn.cy.redis.service.IRedisService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author: 友叔
 * @Date: 2021/3/26 17:23
 * @Description: 自动配置
 */
@Configuration
//@ComponentScan(AutoConfig.BASE_PACKAGE)
public class AutoConfig {
    public static final String BASE_PACKAGE = "cn.cy";

    public static final String BASE_DAO_PACKAGE = BASE_PACKAGE + ".**.mapper";

    @Resource
    private IRedisService redisService;

    @Bean
    public AccessLimitServiceImpl accessLimitService() {
        return new AccessLimitServiceImpl();
    }
}
