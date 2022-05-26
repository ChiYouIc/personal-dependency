package cn.cy.limit.idemp;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: 友
 * @Date: 2022/5/26 17:04
 * @Description: 开启接口幂等性
 */
@Documented
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(IdempotentLimitBeanDefinitionRegistrar.class)
public @interface EnableIdempotentLimit {

    /**
     * 幂等性限制类型，默认是 SPECIFIC 特定接口
     */
    IdempType type() default IdempType.SPECIFIC;

    /**
     * 幂等性 key
     */
    String key() default "global_idemp_limit:";

    /**
     * 默认 3 秒后幂等性失效
     */
    int time() default 3;

}
