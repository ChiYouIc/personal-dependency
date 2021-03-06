package cn.cy.sso.interceptor;

import cn.cy.sso.config.properties.ServerType;
import cn.cy.sso.config.properties.SsoProperties;
import cn.cy.sso.model.SsoResult;
import cn.cy.sso.utils.CoreUtil;
import cn.cy.sso.utils.SsoUtil;
import cn.hutool.core.text.StrFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.PriorityOrdered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.BiFunction;

/**
 * @Author: 友叔
 * @Date: 2021/1/7 21:41
 * @Description: 拦截器
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor, PriorityOrdered {

    private final BiFunction<HttpServletRequest, HttpServletResponse, Boolean> handler;

    private final RestTemplate restTemplate;

    private final String appCode;

    public AuthInterceptor(SsoProperties ssoProperties, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        if (ssoProperties.getType() == ServerType.CLIENT) {
            this.handler = this::isClient;
            this.appCode = ssoProperties.getAppCode();
        } else {
            this.handler = this::isServer;
            this.appCode = null;
        }

        log.info("The current service is Sso {}.", ssoProperties.getType().name());
    }

    /**
     * 区分当前服务类型（Sso-Server or Sso-Client）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return this.handler.apply(request, response);
    }

    /**
     * 清空当前线程变量
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清空当前线程变量
        SsoUtil.remove();
    }

    /**
     * 当前服务为单点客户端时
     *
     * @param request  请求
     * @param response 响应
     * @return 验证结果
     */
    public boolean isClient(HttpServletRequest request, HttpServletResponse response) {
        try {
            String token = CoreUtil.getToken(request);
            // 进行单点登录验证
            boolean verify = sendToSsoServer(token);

            // 如果 token 验证失败
            if (!verify) {
                response.sendError(401, "token verify fail");
            }
            return verify;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 当前服务为单点服务时
     *
     * @param request  请求
     * @param response 响应
     * @return 验证结果
     */
    public boolean isServer(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    /**
     * 向单点服务发送 token 验证
     *
     * @param token token
     * @return 验证是否通过
     */
    private boolean sendToSsoServer(String token) {
        // token 验证地址
        final String uri = StrFormatter.format("/auth?token={}", token);

        ResponseEntity<SsoResult> responseEntity = restTemplate.getForEntity(uri, SsoResult.class);
        HttpStatus statusCode = responseEntity.getStatusCode();

        // 重定向
        if (statusCode == HttpStatus.PERMANENT_REDIRECT) {
            log.warn("sso server response permanent redirect ...");
        } else if (statusCode.value() < HttpStatus.OK.value() || statusCode.value() >= HttpStatus.MULTIPLE_CHOICES.value()) {
            log.error("sso server source rejected access ...");
        }
        SsoResult body = responseEntity.getBody();
        if (body != null && body.isSuccess()) {
            log.info("sso server verify success ...");
            SsoUtil.setInfo(body.getUserInfo());
            return true;
        }

        log.error("sso server verify fail ...");
        return false;
    }

    @Override
    public int getOrder() {
        return 50;
    }
}
