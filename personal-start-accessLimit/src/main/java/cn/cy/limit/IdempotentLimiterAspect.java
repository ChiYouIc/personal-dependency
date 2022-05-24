package cn.cy.limit;

import cn.cy.common.util.RequestUtils;
import cn.cy.limit.annotation.IdempotentLimiter;
import cn.cy.limit.exception.IdempotentLimiterException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @Author: 友
 * @Date: 2022/5/24 18:20
 * @Description: 接口幂等性切面
 */
@Slf4j
@Aspect
public class IdempotentLimiterAspect {

    private final IAccessLimitService<?> accessLimitService;

    public IdempotentLimiterAspect(final IAccessLimitService<?> accessLimitService) {
        this.accessLimitService = accessLimitService;
    }

    @Before(value = "@annotation(limiter)")
    public void before(JoinPoint point, IdempotentLimiter limiter) {

        if (!limiter.enable()) {
            return;
        }

        String path = RequestUtils.getPath();
        try {
            if (accessLimitService.containPath(path)) {
                log.error("The path '{}' access frequency is too high", path);
                throw new IdempotentLimiterException("The access frequency of interface " + path + " is too high.");
            }

            this.accessLimitService.addPath(path);
        } catch (IdempotentLimiterException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Idempotent limiter handler exception.");
        }

    }

    @After(value = "@annotation(limiter)")
    public void after(JoinPoint point, IdempotentLimiter limiter) {
        this.accessLimitService.removePath(RequestUtils.getPath());
    }

}
