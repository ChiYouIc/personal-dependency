package cn.cy.security.component;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author: 友叔
 * @Date: 2021/1/4 15:29
 * @Description: 自定义用户密码认证令牌
 */
public class JwtUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    /**
     * 验证码
     */
    private final String captchaCode;

    /**
     * 验证码的uuid
     */
    private final String captchaCodeUuid;

    public JwtUsernamePasswordAuthenticationToken(Object principal, Object credentials, String captchaCode, String captchaCodeUuid) {
        super(principal, credentials);
        this.captchaCode = captchaCode;
        this.captchaCodeUuid = captchaCodeUuid;
    }

    public JwtUsernamePasswordAuthenticationToken(Object principal, Object credentials, String captchaCode, String captchaCodeUuid, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.captchaCode = captchaCode;
        this.captchaCodeUuid = captchaCodeUuid;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public String getCaptchaCodeUuid() {
        return captchaCodeUuid;
    }
}
