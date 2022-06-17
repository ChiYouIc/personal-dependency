package cn.cy.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

/**
 * @Author: 友
 * @Date: 2022/6/1 11:42
 * @Description:
 */
@Slf4j
public class ApplicationStartAfterEvent implements ApplicationListener<ApplicationStartedEvent> {

    private final RequestMappingHandlerMapping mappingHandlerMapping;

    public ApplicationStartAfterEvent(RequestMappingHandlerMapping mappingHandlerMapping) {
        this.mappingHandlerMapping = mappingHandlerMapping;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("Application started");
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> handler : handlerMethods.entrySet()) {
            RequestMethodInfo info = new RequestMethodInfo();

            RequestMappingInfo mappingInfo = handler.getKey();
            HandlerMethod handlerMethod = handler.getValue();

            // method 全路径名称
            info.setHandleMethod(handlerMethod.toString());

            info.setParamCount(handlerMethod.getMethodParameters().length);

            // url
            PathPatternsRequestCondition pathPatternsCondition = mappingInfo.getPathPatternsCondition();
            for (String url : pathPatternsCondition.getPatternValues()) {
                info.setUrl(url);
            }

            RequestMapping annotation = handlerMethod.getMethodAnnotation(RequestMapping.class);
            for (RequestMethod method : annotation.method()) {
                info.setRequestMethod(method.name());
            }

            log.info(info.toString());

        }
    }

}

