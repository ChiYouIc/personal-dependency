package cn.cy.security.component;

import cn.cy.web.response.ErrorCodeEnum;
import cn.cy.web.response.FailedResponse;
import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/4 15:09
 * @Description: 自定义返回结果：未登录或登录过期
 */
@Component(value = "userAuthenticationEntryPoint")
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.FORBIDDEN;
        // 用户凭证无效
        if (authException instanceof InsufficientAuthenticationException) {
            errorCodeEnum = ErrorCodeEnum.USER_USERNAME_OR_PASSWORD_IS_WRONG;
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.HTTP_INTERNAL_ERROR);
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(FailedResponse.builder()
                        .code(errorCodeEnum.code())
                        .msg(errorCodeEnum.msg())
                        .exception(authException.getMessage())
                        .build()));
        response.getWriter().flush();
        authException.printStackTrace();
    }
}
