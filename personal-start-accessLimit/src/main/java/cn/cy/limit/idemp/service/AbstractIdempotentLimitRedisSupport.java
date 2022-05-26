package cn.cy.limit.idemp.service;

import cn.cy.common.util.SpringUtil;
import cn.cy.redis.service.IRedisService;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 友
 * @Date: 2022/5/25 11:21
 * @Description: 幂等性 Redis 实现
 */
@Slf4j
public abstract class AbstractIdempotentLimitRedisSupport<T> implements IIdempotentLimitService<T> {

    private final IRedisService redisService;

    public AbstractIdempotentLimitRedisSupport() {
        this.redisService = SpringUtil.getBean(IRedisService.class);
        log.info("Use redis to support idempotency.");
    }

    @Override
    public boolean containKey(String key, int time) {
        Integer count = redisService.get(key, Integer.class);
        if (ObjectUtil.isNull(count) || count == 0) {
            redisService.incr(key, 1);
            redisService.expire(key, time);
            return false;
        }
        return true;
    }

    @Override
    public void removeKey(String key) {
        redisService.del(key);
    }

}
