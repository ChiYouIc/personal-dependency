package cn.cy.limit.rate;

import cn.cy.common.util.RequestUtils;
import cn.cy.common.util.ip.IpUtils;
import cn.cy.redis.service.IRedisScriptService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * @Author: 友
 * @Date: 2022/5/24 17:39
 * @Description: 接口限流切面
 */
@Slf4j
@Aspect
public class RateLimiterAspect {

    private final RedisScript<Long> limitScript;

    private final IRedisScriptService redisScriptService;

    public RateLimiterAspect(IRedisScriptService redisScriptService) throws ClassNotFoundException {
        if (redisScriptService == null) {
            throw new ClassNotFoundException("No implementation class for " + IRedisScriptService.class.getName() + " was found");
        }
        this.redisScriptService = redisScriptService;
        this.limitScript = limitScript();
        log.info("Enable rate limit");
    }

    @Before(value = "@annotation(rateLimiter)")
    public void before(JoinPoint point, RateLimiter rateLimiter) {
        String key = rateLimiter.key();
        int time = rateLimiter.time();
        int count = rateLimiter.count();

        String combineKey = getCombineKey(rateLimiter, point);
        List<Object> keys = Collections.singletonList(combineKey);
        try {
            Long number = redisScriptService.execute(limitScript, keys, count, time);
            if (number == null || number.intValue() > count) {
                throw new RateLimiterException("Too many accesses, Please try again later");
            }
            log.info("Limit the request '{}', Current request'{}', Cache key'{}'", count, number.intValue(), key);
        } catch (RateLimiterException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Rate limiter handler exception");
        }
    }

    /**
     * 获取组合键
     *
     * @param rateLimiter 注解
     * @param point       切点
     * @return key
     */
    public String getCombineKey(RateLimiter rateLimiter, JoinPoint point) {
        StringBuilder stringBuffer = new StringBuilder(rateLimiter.key());
        if (rateLimiter.limitType() == LimitType.IP) {
            stringBuffer.append(IpUtils.getIpAddr(RequestUtils.getRequest())).append("-");
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
        return stringBuffer.toString();
    }

    /**
     * 获取限流脚本
     *
     * @return Redis 脚本
     */
    public RedisScript<Long> limitScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/rate_limit.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }

}
