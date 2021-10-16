package cn.cy.web.config;

import cn.cy.web.properties.Website;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 开水白菜
 * @description: Web配置
 * @create: 2021-10-15 00:37
 **/
@Configuration
@EnableConfigurationProperties(Website.class)
public class WebConfig {

}
