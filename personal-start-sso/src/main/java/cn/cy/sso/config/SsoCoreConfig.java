package cn.cy.sso.config;

import cn.cy.sso.SsoCoreService;
import cn.cy.sso.config.properties.SsoProperties;
import cn.cy.sso.interceptor.AuthInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;

/**
 * @Author: 友叔
 * @Date: 2021/1/7 21:42
 * @Description: sso单点核心配置
 */
@Configuration
@ComponentScan(basePackages = SsoCoreConfig.BASE_PACKAGE)
@EnableConfigurationProperties(value = SsoProperties.class)
public class SsoCoreConfig implements WebMvcConfigurer {

    protected final static String BASE_PACKAGE = "cn.cy.sso";

    private final static Log logger = LogFactory.getLog(SsoCoreConfig.class);

    @Resource
    private SsoProperties properties;

    /**
     * 配置拦截器 AuthInterceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("Add sso core interceptor AuthInterceptor");
        registry.addInterceptor(new AuthInterceptor(properties, restTemplate()))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/error",
                        "/logout",
                        "/callback/**");
    }

    /**
     * 配置 RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplateBuilder()
                .rootUri(properties.getUrl())
                .defaultHeader("appCode", properties.getAppCode())
                .build();

        // 自定义的错误处理器
        template.setErrorHandler(new RestResponseErrorHandler());
        return template;
    }

    /**
     * Sso 客户端的 request url 扫描器
     */
    @Bean
    @ConditionalOnProperty(prefix = "sso-core", name = "type", havingValue = "client")
    public RequestPathCollector requestPathCollector(@Autowired RequestMappingHandlerMapping mappingHandlerMapping, @Autowired SsoCoreService ssoCoreService) {
        return new RequestPathCollector(mappingHandlerMapping, ssoCoreService);
    }
}
