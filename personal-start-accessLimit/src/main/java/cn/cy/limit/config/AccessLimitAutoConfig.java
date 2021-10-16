package cn.cy.limit.config;

import cn.cy.limit.interceptor.AccessLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 友叔
 * @Date: 2021/7/22 11:15
 * @Description: 配置
 */
@Configuration
@ComponentScan(basePackages = {"cn.cy.limit"})
public class AccessLimitAutoConfig implements WebMvcConfigurer {

    @Autowired
    private AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
