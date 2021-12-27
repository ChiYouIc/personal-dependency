package cn.cy.redis.service.impl;

import cn.cy.redis.service.IRedisKeysService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Author: 友叔
 * @Date: 2021/1/1 23:47
 * @Description: Redis Key 操作封装Service 实体类
 */
@Service
public class IRedisKeysServiceImpl implements IRedisKeysService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
