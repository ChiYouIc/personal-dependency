package cn.cy.security.component;

import cn.cy.web.response.ErrorCodeEnum;
import cn.cy.web.response.FailedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/4 19:55
 * @Description: 用户自定义返回结果：没有访问权限
 */
@Component(value = "userAccessDeniedHandler")
public class UserAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(FailedResponse
                        .builder()
                        .code(ErrorCodeEnum.FORBIDDEN.code())
                        .msg(ErrorCodeEnum.FORBIDDEN.msg())
                        .exception(accessDeniedException.getMessage())
                        .build()));
        response.getWriter().flush();
    }
}
