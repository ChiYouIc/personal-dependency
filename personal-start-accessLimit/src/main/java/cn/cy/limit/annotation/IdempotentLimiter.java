package cn.cy.limit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 友叔
 * @Date: 2021/7/22 11:07
 * @Description: 接口幂等性控制注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IdempotentLimiter {

    /**
     * 接口幂等性
     */
    boolean enable() default true;

}
