package cn.cy.limit.config;

import cn.cy.limit.interceptor.AccessLimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Author: 友叔
 * @Date: 2021/7/22 11:15
 * @Description: 配置
 */
@Configuration
public class AccessLimitAutoConfig implements WebMvcConfigurer {

    @Resource
    private AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
