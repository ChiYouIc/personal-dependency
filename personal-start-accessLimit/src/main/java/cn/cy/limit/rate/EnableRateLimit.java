package cn.cy.limit.rate;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: 友
 * @Date: 2022/5/26 15:45
 * @Description: 开启限制
 */
@Documented
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(RateLimitBeanDefinitionRegistrar.class)
public @interface EnableRateLimit {
}
