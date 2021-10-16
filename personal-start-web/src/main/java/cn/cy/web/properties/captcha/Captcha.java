package cn.cy.web.properties.captcha;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/30 15:35
 * @Description: 验证码配置
 */
@Setter
@Getter
public class Captcha {

    /**
     * 验证码类型（默认数字类型）
     */
    private String captchaType = "math";

    /**
     * 验证码有效时间（默认两分钟）
     */
    private Integer captchaExpiration = 2;

    /**
     * 验证码 redis key
     */
    private String captchaCodeKey = "captcha_codes:";


}
