package cn.cy.dtc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: you
 * @date: 2022-04-23 14:32
 * @description: 动态线程池配置
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {DtpConfig.BASE_PACKAGE})
public class DtpConfig {

    protected static final String BASE_PACKAGE = "cn.cy.dtp";

}
