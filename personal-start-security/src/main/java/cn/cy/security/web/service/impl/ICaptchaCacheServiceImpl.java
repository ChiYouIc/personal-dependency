package cn.cy.security.web.service.impl;

import cn.cy.redis.service.IRedisService;
import cn.cy.security.web.service.ICaptchaCacheService;
import cn.cy.web.properties.Website;
import cn.cy.web.properties.captcha.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/30 20:25
 * @Description: 验证码缓存Service
 */
@Service
public class ICaptchaCacheServiceImpl implements ICaptchaCacheService {

    private final Captcha captcha;
    @Autowired
    private IRedisService redisService;

    public ICaptchaCacheServiceImpl(@Autowired Website website) {
        this.captcha = website.getCaptcha();
    }

    @Override
    public void setCaptchaInfo(String uuid, String code) {
        String verifyKey = captcha.getCaptchaCodeKey() + uuid;
        redisService.set(verifyKey, code, captcha.getCaptchaExpiration(), TimeUnit.MINUTES);
    }

    @Override
    public String getCaptchaInfo(String uuid) {
        String verifyKey = captcha.getCaptchaCodeKey() + uuid;
        return (String) redisService.get(verifyKey);
    }

    @Override
    public void delCaptchaInfo(String uuid) {
        String verifyKey = captcha.getCaptchaCodeKey() + uuid;
        redisService.del(verifyKey);
    }
}
