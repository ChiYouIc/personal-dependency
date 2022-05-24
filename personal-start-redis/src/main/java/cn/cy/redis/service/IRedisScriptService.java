package cn.cy.redis.service;

import org.springframework.data.redis.core.script.RedisScript;

import java.util.List;

/**
 * @Author: 友
 * @Date: 2022/5/24 18:00
 * @Description: Redis 脚本执行Service
 */
public interface IRedisScriptService {

    /**
     * Redis 脚本执行
     *
     * @param script 脚本
     * @param keys
     * @param args
     * @param <T>
     * @return
     */
    public <T> T execute(RedisScript<T> script, List<Object> keys, Object... args);
}
