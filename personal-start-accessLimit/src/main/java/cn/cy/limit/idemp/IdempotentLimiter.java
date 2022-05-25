package cn.cy.limit.idemp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口幂等性控制注解
 *
 * @Author: 友叔
 * @Date: 2021/7/22 11:07
 * @Description: 接口幂等性控制注解，该注解参数 time 表示幂等性控制默认在 time 秒之后失效，防止因其它异常问题导致接口一直不可以访问。
 * @see cn.cy.limit.idemp.IdempotentLimiterAspect
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IdempotentLimiter {

    /**
     * 幂等性 key
     */
    String key() default "idemp_limit:";

    /**
     * 是否开启接口幂等性
     */
    boolean enable() default true;

    /**
     * 默认 3 秒后幂等性失效
     */
    int time() default 3;

}
