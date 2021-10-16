package cn.cy.security.component;

import cn.cy.security.web.service.ICaptchaCacheService;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author: 友叔
 * @Date: 2021/1/4 15:39
 * @Description: 自定义身份认证提供者
 */
@Component(value = "jwtDaoAuthenticationProvider")
public class JwtDaoAuthenticationProvider extends DaoAuthenticationProvider {

    private final ICaptchaCacheService captchaCacheService;

    @Autowired
    public JwtDaoAuthenticationProvider(ICaptchaCacheService captchaCacheService, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.captchaCacheService = captchaCacheService;
        // 配置 UserDetailsService，通过这个接口获取连接数据库获取用户信息
        this.setUserDetailsService(userDetailsService);
        // 配置 密码 校验器
        this.setPasswordEncoder(passwordEncoder);
    }

    /**
     * 其他身份验证检查
     * <p> 通过继承修改改方法，可以将我们的验证码验证嵌入进去
     *
     * @param userDetails    用户信息
     * @param authentication 用户认证令牌
     * @throws AuthenticationException
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 查看令牌是否是我们自定义的用户名密码认证令牌
        if (!(authentication instanceof JwtUsernamePasswordAuthenticationToken)) {
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badUsernamePasswordAuthenticationToken", "Bad captchaCode"));
        }
        // 强转为我们自定义的身份认证令牌，并获取封装到其中的用户信息
        JwtUsernamePasswordAuthenticationToken authenticationToken = (JwtUsernamePasswordAuthenticationToken) authentication;
        String captchaCode = authenticationToken.getCaptchaCode();
        String captchaCodeUuid = authenticationToken.getCaptchaCodeUuid();
        if (StrUtil.isNotEmpty(captchaCode) && StrUtil.isNotEmpty(captchaCodeUuid)) {
            // 从缓存中获取我们的验证码
            String code = captchaCacheService.getCaptchaInfo(captchaCodeUuid);
            captchaCacheService.delCaptchaInfo(captchaCodeUuid);
            if (!StrUtil.equals(code, captchaCode)) {
                this.logger.debug("验证码错误.");
                throw new BadCredentialsException(this.messages
                        .getMessage("AbstractUserDetailsAuthenticationProvider.badCaptchaCode", "Bad captchaCode"));
            }
        }
        // 密码不为null
        if (authenticationToken.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        // 密码验证
        String presentedPassword = authenticationToken.getCredentials().toString();
        if (!this.getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
            this.logger.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }
}
