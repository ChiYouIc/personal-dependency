package cn.cy.sso.config;

import cn.cy.sso.SsoCoreService;
import cn.cy.sso.model.RequestPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 友
 * @Date: 2022/6/16 18:11
 * @Description: 请求路径收集器
 */
@Slf4j
public class RequestPathCollector implements ApplicationListener<ApplicationStartedEvent> {

    private final RequestMappingHandlerMapping mappingHandlerMapping;

    private final SsoCoreService ssoCoreService;

    public RequestPathCollector(RequestMappingHandlerMapping mappingHandlerMapping, SsoCoreService ssoCoreService) {
        this.mappingHandlerMapping = mappingHandlerMapping;
        this.ssoCoreService = ssoCoreService;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("Scan all request path");

        List<RequestPath> list = new ArrayList<>();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> handler : handlerMethods.entrySet()) {
            RequestPath info = new RequestPath();

            RequestMappingInfo mappingInfo = handler.getKey();
            HandlerMethod handlerMethod = handler.getValue();

            // method 全路径名称
            info.setHandleMethod(handlerMethod.toString());

            // 参数个数
            info.setParamCount(handlerMethod.getMethodParameters().length);

            // url
            PathPatternsRequestCondition pathPatternsCondition = mappingInfo.getPathPatternsCondition();
            for (String url : pathPatternsCondition.getPatternValues()) {
                info.setUrl(url);
            }

            // 请求方法
            RequestMapping annotation = handlerMethod.getMethodAnnotation(RequestMapping.class);
            for (RequestMethod method : annotation.method()) {
                info.setRequestMethod(method.name());
            }

            list.add(info);

            log.debug("url: {}, requestMethod: {}, handleMethod:{}, paramCount: {}",
                    info.getUrl(),
                    info.getRequestMethod(),
                    info.getHandleMethod(),
                    info.getParamCount());

        }

        try {
            boolean b = ssoCoreService.sendRequestUrl(list);
            if (!b) {
                log.warn("Request path failed to push");
            }
        } catch (Exception e) {
            log.warn("Request path failed to push");
        }

    }

}
