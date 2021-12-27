package cn.cy.security.web.service.impl;

import cn.cy.redis.properties.RedisProperties;
import cn.cy.redis.service.IRedisKeysService;
import cn.cy.redis.service.IRedisService;
import cn.cy.security.web.model.SecurityUserDetails;
import cn.cy.security.web.service.IUserCacheService;
import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/5 11:33
 * @Description: 用户信息缓存Service实现类
 */
@Service
public class IUserCacheServiceImpl implements IUserCacheService {

    @Resource
    private IRedisService redisService;

    @Resource
    private IRedisKeysService keysService;

    @Resource
    private RedisProperties redisProperties;

    @Override
    public void setUserInfo(SecurityUserDetails loginUser) {
        String key = redisProperties.getDatabase() + ":" + redisProperties.getAdmin() + ":" + loginUser.getUsername();
        redisService.set(key, loginUser, redisProperties.getCommon());
    }

    @Override
    public SecurityUserDetails getUserInfo(String username) {
        String key = redisProperties.getDatabase() + ":" + redisProperties.getAdmin() + ":" + username;
        return (SecurityUserDetails) redisService.get(key);
    }

    @Override
    public boolean deleteUserInfo(String username) {
        String key = redisProperties.getDatabase() + ":" + redisProperties.getAdmin() + ":" + username;
        return redisService.del(key);
    }

    @Override
    public List<String> getUsernameList() {
        String pattern = redisProperties.getDatabase() + ":" + redisProperties.getAdmin() + "*";
        Set<String> list = keysService.getKeys(pattern);
        return CollUtil.isEmpty(list) ? null : list.stream().map(o -> o.split(":")[2]).collect(Collectors.toList());
    }
}
