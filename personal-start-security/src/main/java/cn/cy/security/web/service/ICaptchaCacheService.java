package cn.cy.security.web.service;

/**
 * @Author: 友叔
 * @Date: 2020/12/30 20:23
 * @Description: 验证码缓存
 */
public interface ICaptchaCacheService {
    /**
     * 缓存验证码信息
     *
     * @param uuid 键
     * @param code 验证码
     */
    public void setCaptchaInfo(String uuid, String code);

    /**
     * 获取验证码
     *
     * @param uuid
     * @return 验证码
     */
    public String getCaptchaInfo(String uuid);

    /**
     * 删除uuid 对应的验证码
     *
     * @param uuid
     */
    public void delCaptchaInfo(String uuid);
}
