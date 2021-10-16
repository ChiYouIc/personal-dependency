package cn.cy.security.config;

import cn.cy.security.util.JwtTokenUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: 友叔
 * @Date: 2020/11/26 21:44
 * @Description: jwt 配置信息
 */
@Getter
@Setter
@Component(value = "jwtConfig")
@ConfigurationProperties(prefix = JwtConfig.PREFIX)
public class JwtConfig {
    public static final String PREFIX = "jwt";

    private String tokenHeader = "Authorization";

    private String secret = "pw-admin-secret";

    private Long expiration = 604800L;

    private String tokenHead = StrUtil.EMPTY;

    private Integer tokenRefreshInterval = 1800;

    /**
     * jwt token 工具类
     */
    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }
}
