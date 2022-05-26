package cn.cy.limit.idemp;

import cn.cy.limit.idemp.context.IdempContext;
import cn.cy.limit.idemp.context.IdempContextHolder;
import cn.cy.limit.idemp.service.IIdempotentLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 友
 * @Date: 2022/5/26 17:05
 * @Description: 接口幂等性Bean注入
 */
@Slf4j
public class IdempotentLimitBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder beanDefinitionBuilder;

        StandardAnnotationMetadata metadata = (StandardAnnotationMetadata) annotationMetadata;
        Class<?> introspectedClass = metadata.getIntrospectedClass();
        EnableIdempotentLimit annotation = introspectedClass.getAnnotation(EnableIdempotentLimit.class);

        IdempContext context = IdempContext.builder()
                .key(annotation.key())
                .time(annotation.time())
                .build();
        IdempContextHolder.setContext(context);

        IdempType type = annotation.type();
        if (type == IdempType.SPECIFIC) {
            log.info("Enable specific idempotent limit");
            beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(IdempotentLimiterAspect.class);
        } else {
            log.info("Enable global idempotent limit");
            beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(GlobalIdempotentLimitInterceptorRegistrar.class);
        }

        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        String beanName = BeanDefinitionReaderUtils.generateBeanName(beanDefinition, registry);
        registry.registerBeanDefinition(beanName, beanDefinition);
    }

    /**
     * 全局的幂等性验证拦截器注册器
     */
    private static class GlobalIdempotentLimitInterceptorRegistrar implements WebMvcConfigurer {

        private final IIdempotentLimitService<?> limitService;

        public GlobalIdempotentLimitInterceptorRegistrar(IIdempotentLimitService<?> limitService) throws ClassNotFoundException {
            if (limitService == null) {
                throw new ClassNotFoundException("No implementation class for " + IIdempotentLimitService.class.getName() + " was found");
            }
            this.limitService = limitService;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            IdempLimitInterceptor interceptor = new IdempLimitInterceptor(limitService);
            registry.addInterceptor(interceptor);
            log.info("Register global idempotent limit interceptor");
        }
    }

}
