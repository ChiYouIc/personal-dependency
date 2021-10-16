package cn.cy.log.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * @author: 开水白菜
 * @description: 日志配置
 * @create: 2021-08-11 22:25
 **/
@Configuration
public class LogConfig {

    @Bean
    public CommonsRequestLoggingFilter loggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        // 记录 客户端 IP 信息
        loggingFilter.setIncludeClientInfo(true);
        // 记录请求头
        loggingFilter.setIncludeHeaders(true);
        // 如果记录请求头，可以指定哪些需要记录，那些不需要记录
        // loggingFilter.setHeaderPredicate();

        // 记录 请求体 特别是 POST 请求的 body 参数
        loggingFilter.setIncludePayload(true);
        // 请求体的大小限制，默认时 50
        loggingFilter.setMaxPayloadLength(10000);
        // 记录请求路径中的 query 参数
        loggingFilter.setIncludeQueryString(true);

        return loggingFilter;
    }

}
