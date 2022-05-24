package cn.cy.limit.interceptor;

import cn.cy.limit.IAccessLimitService;
import cn.cy.web.response.FailedResponse;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @Author: 友叔
 * @Date: 2021/7/22 11:13
 * @Description: 接口访问控制拦截器
 */
public class AccessLimitInterceptor implements HandlerInterceptor {

    private final IAccessLimitService<?> accessLimitService;

    private final static Logger log = LoggerFactory.getLogger(AccessLimitInterceptor.class);

    public AccessLimitInterceptor(final IAccessLimitService<?> accessLimitService) {
        this.accessLimitService = accessLimitService;
    }

    /**
     * <p>拦截处理程序的执行。 在 HandlerMapping 确定合适的处理程序对象之后，但在 HandlerAdapter 调用处理程序之前调用
     * （确定了 controller 但还没有执行到 controller）。</p>
     *
     * <p>DispatcherServlet 处理执行链中的处理程序，由任意数量的拦截器组成，处理程序本身位于末尾。
     * 使用此方法，每个拦截器都可以决定中止执行链，通常是发送 HTTP 错误或编写自定义响应。</p>
     *
     * <p>注意：特殊注意事项适用于异步请求处理。 有关更多详细信息，请参阅 AsyncHandlerInterceptor 。</p>
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  选择的处理程序执行，用于类型和/或实例评估
     * @return 默认实现返回true
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        // HandleMethod : 封装有关由方法和bean组成的处理程序方法的信息
        if (handler instanceof HandlerMethod) {
            String path = request.getRequestURI();

            if (accessLimitService.containPath(path)) {
                log.error("The path '{}' access frequency is too high", path);
                FailedResponse failedResponse = new FailedResponse();
                failedResponse.setMsg("接口: " + path + " 访问频率过高！");
                this.render(response, failedResponse);
                return false;
            }

            this.accessLimitService.addPath(path);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        this.accessLimitService.removePath(request.getRequestURI());
    }

    /**
     * 消息返回
     *
     * @param response       响应体
     * @param failedResponse 消息封装
     * @throws IOException
     */
    private void render(HttpServletResponse response, FailedResponse failedResponse) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSONUtil.toJsonStr(failedResponse);
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
