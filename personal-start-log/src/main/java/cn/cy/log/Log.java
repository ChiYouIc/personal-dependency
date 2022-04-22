package cn.cy.log;

import cn.cy.log.bo.OperationType;

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
     * 操作人
     */
    String operator() default "";

    /**
     * 操作成功描述
     */
    String success() default "";

    /**
     * 操作失败描述
     */
    String error() default "";

    /**
     * 操作类型
     */
    OperationType operation() default OperationType.SEARCH;

}
