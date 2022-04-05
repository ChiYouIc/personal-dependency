package cn.cy.limit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 友叔
 * @Date: 2021/7/22 11:07
 * @Description: 接口访问控制注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    /**
     * 访问时限
     */
    int seconds() default 2;

    /**
     * 在 seconds 内，最大访问次数
     */
    int maxCount() default 1;

}
