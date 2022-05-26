package cn.cy.limit.idemp;

import cn.cy.common.util.RequestUtils;
import cn.cy.limit.idemp.service.IIdempotentLimitService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @Author: 友
 * @Date: 2022/5/24 18:20
 * @Description: 接口幂等性切面
 */
@Slf4j
@Aspect
public class IdempotentLimiterAspect {

    private final IIdempotentLimitService<?> limitService;

    public IdempotentLimiterAspect(IIdempotentLimitService<?> limitService) {
        this.limitService = limitService;
    }

    @Before(value = "@annotation(limiter)")
    public void before(JoinPoint point, IdempotentLimiter limiter) {

        if (!limiter.enable()) {
            return;
        }

        String path = RequestUtils.getPath();
        try {
            if (limitService.containKey(this.getCombineKey(point, limiter), limiter.time())) {
                log.error("The path '{}' access frequency is too high", path);
                throw new IdempotentLimiterException("The access frequency of interface " + path + " is too high");
            }
        } catch (IdempotentLimiterException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Idempotent limiter handler exception");
        }

    }

    @After(value = "@annotation(limiter)")
    public void after(JoinPoint point, IdempotentLimiter limiter) {
        this.limitService.removeKey(this.getCombineKey(point, limiter));
    }

    /**
     * 获取一个唯一键，用于标识当前用户的访问标记在缓存中唯一
     *
     * @param point   切面
     * @param limiter 限流注解
     * @return key
     */
    public String getCombineKey(JoinPoint point, IdempotentLimiter limiter) {
        String key = limiter.key();

        StringBuilder builder = new StringBuilder(key);
        Object o = limitService.connectionSign();
        builder.append(o).append("-");

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        builder.append(targetClass.getName()).append("-").append(method.getName());

        return builder.toString();
    }

}
