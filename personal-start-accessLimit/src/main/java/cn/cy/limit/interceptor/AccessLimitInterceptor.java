package cn.cy.limit.interceptor;

import cn.cy.limit.annotation.AccessLimit;
import cn.cy.web.response.FailedResponse;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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
@Slf4j
@Component("accessLimitInterceptor")
public class AccessLimitInterceptor implements HandlerInterceptor {

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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            log.info("[*] access method : {}", handlerMethod.getMethod().getName());

            AccessLimit limit = handlerMethod.getMethodAnnotation(AccessLimit.class);

            // 如果没有使用 AccessLimit 注解
            if (ObjectUtil.isEmpty(limit)) {
                return true;
            }

            int seconds = limit.seconds();
            int maxCount = limit.maxCount();


        }
        return true;
    }

    /**
     * <p>拦截处理程序的执行。 在 HandlerAdapter 实际调用处理程序之后调用，但在 DispatcherServlet 呈现视图之前调用。
     * 可以通过给定的 ModelAndView 向视图公开其他模型对象。</p>
     *
     * <p>DispatcherServlet 处理执行链中的处理程序，由任意数量的拦截器组成，处理程序本身位于末尾。
     * 使用这种方法，每个拦截器都可以对执行进行后处理，以与执行链相反的顺序应用。</p>
     *
     * <p>注意：特殊注意事项适用于异步请求处理。 有关更多详细信息，请参阅 AsyncHandlerInterceptor 。</p>
     *
     * <p>默认实现为空。</p>
     *
     * @param request      请求对象
     * @param response     响应对象
     * @param handler      选择的处理程序执行，用于类型和/或实例评估
     * @param modelAndView 模型和视图的持有者
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * <p>请求处理完成后的回调，即渲染视图后。 将在处理程序执行的任何结果上调用，从而允许适当的资源清理。</p>
     *
     * <p>注意：只有在此拦截器的 preHandle 方法成功完成并返回 true 才会调用！</p>
     *
     * <p>与 postHandle 方法一样，该方法将在链中的每个拦截器上以相反的顺序被调用，因此第一个拦截器将是最后一个被调用的。</p>
     *
     * <p>注意：特殊注意事项适用于异步请求处理。 有关更多详细信息，请参阅AsyncHandlerInterceptor 。</p>
     *
     * <p>默认实现为空。</p>
     *
     * @param request  请求对象
     * @param response 详情对象
     * @param handler  模型和视图的持有者
     * @param ex       异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private void render(HttpServletResponse response, FailedResponse failedResponse) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSONUtil.toJsonStr(failedResponse);
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
