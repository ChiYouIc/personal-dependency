package cn.cy.log.aspectj;

import cn.cy.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Method;

/**
 * @program: personal-website
 * @author: 开水白菜
 * @description: 日志切面
 * @create: 2021-07-13 20:11
 **/
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut(value = "@annotation(cn.cy.log.Log)")
    public void pointcut() {

    }

    @Before(value = "pointcut()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info(arg.toString());
        }
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        Method method = methodSignature.getMethod();
        Log l = method.getAnnotation(Log.class);
        log.info("description : {}, operation : {}.", l.description(), l.operation());

        GetMapping get = method.getAnnotation(GetMapping.class);
        log.info("request method : {}, path : {}", get.path(), get.value());

    }

    @After(value = "pointcut()")
    public void after() {

    }

    @AfterReturning(pointcut = "pointcut()")
    public void afterReturning() {

    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void afterThrowing(Exception e) {
        log.error(e.getMessage());
    }
}
