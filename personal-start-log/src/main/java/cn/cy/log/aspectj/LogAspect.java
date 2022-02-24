package cn.cy.log.aspectj;

import cn.cy.log.Log;
import cn.cy.log.bo.LogRecord;
import cn.cy.log.service.ILogRecordService;
import cn.cy.log.service.IOperatorGetService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final IOperatorGetService operatorGetService;

    private final ILogRecordService logRecordService;

    public LogAspect(@Autowired IOperatorGetService operatorGetService, @Autowired(required = false) ILogRecordService logRecordService) {
        this.operatorGetService = operatorGetService;
        this.logRecordService = logRecordService;
    }

    /**
     * 切点
     */
    @Pointcut(value = "@annotation(cn.cy.log.Log)")
    public void pointcut() {

    }

    /**
     * 前置通知
     *
     * @param joinPoint
     */
    @Before(value = "pointcut()")
    public void before(JoinPoint joinPoint) {

    }

    /**
     * 后置通知
     */
    @After(value = "pointcut()")
    public void after(JoinPoint joinPoint) {

    }

    /**
     * 返回通知
     *
     * @param result 返回结果
     */
    @AfterReturning(pointcut = "pointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        LogRecord record = this.logRecord(joinPoint);
        record.setResult(result);
        this.record(record);
    }

    /**
     * 异常通知
     *
     * @param e 异常
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        LogRecord record = this.logRecord(joinPoint);
        log.error(e.getMessage());
        this.record(record);
    }

    /**
     * 日志记录封装
     *
     * @param joinPoint 切点
     * @return 日志记录
     */
    private LogRecord logRecord(JoinPoint joinPoint) {
        // 参数信息
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info(arg.toString());
        }

        // 获取接口方法
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        // 日志对象
        LogRecord record = new LogRecord().setOperator(operatorGetService.getOperator());

        // 获取日志注解对象
        Log l = method.getAnnotation(Log.class);
        log.info("description : {}, operation : {}.", l.description(), l.operation());

        GetMapping get = method.getAnnotation(GetMapping.class);
        log.info("request method : {}, path : {}", get.path(), get.value());

        return record;
    }

    /**
     * 保存日志记录
     *
     * @param logRecord 日志记录
     */
    private void record(LogRecord logRecord) {
        if (logRecordService != null) {
            logRecordService.record(logRecord);
        }
    }
}
