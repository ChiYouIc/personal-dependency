package cn.cy.limit.config;

import cn.cy.limit.LimitExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 友叔
 * @Date: 2021/7/22 11:15
 * @Description: 配置
 */
@Slf4j
@Configuration
public class AccessLimitAutoConfig implements WebMvcConfigurer {

    @Bean
    public LimitExceptionHandler limitExceptionHandler() {
        return new LimitExceptionHandler();
    }
}
