package cn.cy.security.config;

import cn.cy.security.component.*;
import cn.cy.security.web.service.impl.ISecurityUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: 友叔
 * @Date: 2020/11/24 21:29
 * @Description: SpringSecurity 安全配置
 */
@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan({SecurityConfig.BASE_PACKAGE})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String BASE_PACKAGE = "cn.cy.security";

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private JwtTokenLoginFilter jwtTokenLoginFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UserAuthenticationEntryPoint userAuthenticationEntryPoint;

    @Autowired
    private UserLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private ISecurityUserServiceImpl securityUserService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
        // 不需要保护的资源路径允许访问
        for (String url : ignoreUrlsConfig.getUrls()) {
            LOGGER.info("White list url: {}", url);
            registry.antMatchers(url).permitAll();
        }

        // 自定义登陆成功与失败处理
//		registry.and().formLogin()
//				.successHandler()
//				.successHandler()
//				.failureHandler();

        // 自定义用户退出登陆处理器
        registry.and().logout().logoutSuccessHandler(logoutSuccessHandler);

        // 允许跨域请求的 OPTIONS 请求
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();

        // 配置请求身份认证
        registry.and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();

        // 自定义返回结果
        registry.and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(userAuthenticationEntryPoint);

        // 关闭跨站请求拦截，设置 session 生成策略使用无状态
        LOGGER.info("Enables global cross-domain support");
        registry.and()
                .csrf()
                .disable()
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 自定义权限拦截器 JWT 过滤器以及 JWT 授权过滤器，且该过滤器处于 UsernamePasswordAuthenticationFilter 之前
        registry.and()
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(jwtTokenLoginFilter);

    }

    /**
     * 配置身份校验器中密码校验器（密码处理使用{@link BCryptPasswordEncoder}加密处理）
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


}
