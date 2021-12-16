package cn.cy.security.component;

import cn.cy.security.config.JwtConfig;
import cn.cy.security.enums.LoginStatus;
import cn.cy.security.factory.LoginLogSyncFactory;
import cn.cy.security.util.JwtTokenUtil;
import cn.cy.security.web.service.IUserCacheService;
import cn.cy.sync.async.AsyncExecutor;
import cn.cy.web.response.SuccessResponse;
import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/6 16:16
 * @Description: 自定义退出登陆处理类
 */
@Component(value = "userLogoutSuccessHandler")
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private IUserCacheService userCacheService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 获取请求头中的 token
        String authHeader = request.getHeader(jwtConfig.getTokenHeader());
        if (authHeader != null && authHeader.startsWith(jwtConfig.getTokenHead())) {
            // The part after "Bearer " 从 token 中获取用户名
            String authToken = authHeader.substring(jwtConfig.getTokenHead().length());
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            userCacheService.deleteUserInfo(username);
            // 日志记录
            AsyncExecutor.me().execute(LoginLogSyncFactory.recordLoginInfo(username, LoginStatus.LOGOUT, authToken, authentication));
            AsyncExecutor.me().execute(LoginLogSyncFactory.recordLoginIpAddress());
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(SuccessResponse
                        .builder()
                        .code(HttpStatus.HTTP_OK)
                        .build()));
        response.getWriter().flush();
    }
}
