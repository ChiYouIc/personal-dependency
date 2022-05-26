package cn.cy.limit.rate;

import cn.cy.limit.rate.RateLimiterAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author: 友
 * @Date: 2022/5/26 15:47
 * @Description: 自动注入 Bean
 */
@Slf4j
public class RateLimitBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(RateLimiterAspect.class);
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        String beanName = BeanDefinitionReaderUtils.generateBeanName(beanDefinition, registry);
        registry.registerBeanDefinition(beanName, beanDefinition);
    }
}
