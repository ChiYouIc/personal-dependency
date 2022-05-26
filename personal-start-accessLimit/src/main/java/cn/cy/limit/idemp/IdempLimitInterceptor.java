package cn.cy.limit.idemp;

import cn.cy.common.util.RequestUtils;
import cn.cy.limit.idemp.context.IdempContextHolder;
import cn.cy.limit.idemp.service.IIdempotentLimitService;
import cn.cy.web.response.FailedResponse;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * @Author: 友
 * @Date: 2022/5/26 17:38
 * @Description: 幂等性限制拦截器
 */
@Slf4j
public class IdempLimitInterceptor implements HandlerInterceptor {

    private final IIdempotentLimitService<?> limitService;

    public IdempLimitInterceptor(IIdempotentLimitService<?> limitService) {
        this.limitService = limitService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        // HandleMethod : 封装有关由方法和bean组成的处理程序方法的信息
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();

            int time = IdempContextHolder.getTime();

            // 如果使用了 IdempotentLimiter 注解，则 time 使用注解上的配置
            IdempotentLimiter limiter = method.getAnnotation(IdempotentLimiter.class);
            if (limiter != null) {
                if (!limiter.enable()) {
                    return true;
                }

                time = limiter.time();
            }

            String key = getCombineKey(IdempContextHolder.getKey(), method);
            String path = RequestUtils.getPath();

            if (limitService.containKey(key, time)) {
                log.error("The path '{}' access frequency is too high", path);
                FailedResponse failedResponse = new FailedResponse();
                failedResponse.setMsg("The access frequency of interface " + path + " is too high");
                this.render(response, failedResponse);
                return false;
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();

            IdempotentLimiter limiter = method.getAnnotation(IdempotentLimiter.class);
            if (limiter != null && !limiter.enable()) {
                return;
            }

            this.limitService.removeKey(getCombineKey(IdempContextHolder.getKey(), method));
        }
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

    /**
     * 获取一个唯一键，用于标识当前用户的访问标记在缓存中唯一
     *
     * @param method 执行的目标方法
     * @return key
     */
    public String getCombineKey(String key, Method method) {
        StringBuilder builder = new StringBuilder(key);
        Object o = limitService.connectionSign();
        builder.append(o).append("-");

        Class<?> targetClass = method.getDeclaringClass();
        builder.append(targetClass.getName()).append("-").append(method.getName());

        return builder.toString();
    }

}
