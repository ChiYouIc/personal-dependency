package cn.cy.security.component;

import cn.cy.security.config.JwtConfig;
import cn.cy.security.enums.LoginStatus;
import cn.cy.security.factory.LoginLogSyncFactory;
import cn.cy.security.util.JwtTokenUtil;
import cn.cy.security.web.model.LoginUser;
import cn.cy.security.web.model.SecurityUserDetails;
import cn.cy.security.web.service.IUserCacheService;
import cn.cy.sync.manager.AsyncManager;
import cn.cy.web.response.SuccessResponse;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;

/**
 * <p>验证用户登陆表单，并在认证成功后，返回 token
 *
 * @Author: 友叔
 * @Date: 2020/12/1 16:39
 * @Description: Jwt令牌授权过滤器
 */
@Component(value = "jwtTokenLoginFilter")
public class JwtTokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final IUserCacheService userCacheService;

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtConfig jwtConfig;

    @Autowired
    public JwtTokenLoginFilter(IUserCacheService userCacheService, JwtConfig jwtConfig, JwtTokenUtil jwtTokenUtil) {
        this.userCacheService = userCacheService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected AuthenticationManager getAuthenticationManager() {
        return super.getAuthenticationManager();
    }

    /**
     * 配置自定义的认证提供者，进行用户认证
     *
     * @param authenticationProvider 自定义的认证提供者
     */
    @Autowired
    public void setAuthenticationManager(JwtDaoAuthenticationProvider authenticationProvider) {
        ProviderManager providerManager = new ProviderManager(Collections.singletonList(authenticationProvider));
        super.setAuthenticationManager(providerManager);
    }

    /**
     * 接受并解析用户凭证
     * <br>
     * <b>注意：</b>
     * <p>这里使用 {@code new ObjectMapper().readValue(request.getReader(),LoginUser.class)}解析登陆表单，原因是前端的请求头类型默认是{@code application/json; charset=utf-8}
     * 并且使用的 POST 提交，如果这里使用默认的获取方式{@link HttpServletRequest#getParameter(String)}就不能正常获取请求参数，
     * 因为 POST 的 Request Payload 请求正文是一个 json 格式的字符串，不能直接使用 {@link HttpServletRequest#getParameter(String)}处理，
     * 而{@link HttpServletRequest#getParameter(String)}适合处理类似 get 请求 url 的请求参数的这一类型，例如{@code application/x-www-form-urlencoded}、{@code multipart/form-data}
     * 使用{@link HttpServletRequest#getReader()}结合{@link ObjectMapper#readValue(Reader, Class)}处理 </p>
     *
     * @param request  请求
     * @param response 响应
     * @return
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        // 解析参数
        LoginUser loginUser = new ObjectMapper().readValue(request.getReader(), LoginUser.class);
        // 登录名
        String username = loginUser.getUsername();
        username = StrUtil.isEmpty(username) ? StrUtil.EMPTY : username;
        // 密码
        String password = loginUser.getPassword();
        password = StrUtil.isEmpty(password) ? StrUtil.EMPTY : password;
        // 验证码
        String captchaCode = loginUser.getCaptchaCode();
        captchaCode = StrUtil.isEmpty(captchaCode) ? StrUtil.EMPTY : captchaCode;
        // 验证码uuid
        String uuid = loginUser.getCaptchaCodeToken();
        uuid = StrUtil.isEmpty(uuid) ? StrUtil.EMPTY : uuid;

//		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 使用自定义的身份验证令牌
        JwtUsernamePasswordAuthenticationToken authenticationToken = new JwtUsernamePasswordAuthenticationToken(username, password, captchaCode, uuid);
        setDetails(request, authenticationToken);
        return getAuthenticationManager().authenticate(authenticationToken);
    }


    /**
     * <p> 用户登陆成功后，生成 token，并且返回。
     * <p> 记录登陆日志
     *
     * @param request    请求
     * @param response   响应
     * @param chain      拦截器链
     * @param authResult 认证结果
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        SecurityUserDetails userDetails = (SecurityUserDetails) authResult.getPrincipal();
        userCacheService.setUserInfo(userDetails);
        String token = jwtConfig.getTokenHead() + jwtTokenUtil.generateToken(userDetails);
        response.getWriter().println(new ObjectMapper().writeValueAsString(SuccessResponse.builder().data(token).build()));
        // 写入登陆日志
        AsyncManager.me().execute(LoginLogSyncFactory.recordLoginInfo(userDetails.getUsername(), LoginStatus.LOGIN_SUCCESS, token, userDetails));
        AsyncManager.me().execute(LoginLogSyncFactory.recordLoginIpAddress());
    }

    /**
     * 登陆失败
     *
     * @param request  请求
     * @param response 响应
     * @param failed   失败异常
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
