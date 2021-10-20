package cn.cy.web.properties;

import cn.cy.web.properties.captcha.Captcha;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/25 14:55
 * @Description: 项目配置
 */
@Setter
@Getter
@ConfigurationProperties(prefix = Website.PREFIX)
public class Website {

    public static final String PREFIX = "takeout";
    /**
     * 是否开启IP地址解析
     */
    public static boolean addressEnable = true;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 版本
     */
    private String version;
    /**
     * 版权年份
     */
    private String copyrightYear;
    /**
     * 域名
     */
    private String host;

    /**
     * 验证码配置
     */
    @NestedConfigurationProperty
    private Captcha captcha = new Captcha();
}
