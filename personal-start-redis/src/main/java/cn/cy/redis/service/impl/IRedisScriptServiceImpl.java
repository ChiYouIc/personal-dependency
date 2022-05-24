package cn.cy.redis.service.impl;

import cn.cy.redis.service.IRedisScriptService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 友
 * @Date: 2022/5/24 18:01
 * @Description: Redis 脚本执行 Service 实现
 */
@Service
public class IRedisScriptServiceImpl implements IRedisScriptService {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public <T> T execute(RedisScript<T> script, List<Object> keys, Object... args) {
        return redisTemplate.execute(script, keys, args);
    }

}
