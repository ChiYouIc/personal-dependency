package cn.cy.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: personal-website
 * @author: 开水白菜
 * @description: 自定义日志注解
 * @create: 2021-07-13 21:21
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 描述
     */
    String description() default "";

    /**
     * 操作类型
     */
    OperationType operation() default OperationType.SEARCH;

}
