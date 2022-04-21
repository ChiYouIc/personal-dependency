package cn.cy.log.aspect;

import cn.cy.log.Log;
import cn.cy.log.SpelUtils;
import cn.cy.log.bo.AuditLog;
import cn.cy.log.bo.OperationLog;
import cn.cy.log.bo.OperationStatus;
import cn.cy.log.expression.LogRecordContext;
import cn.cy.log.expression.LogRecordExpressionEvaluator;
import cn.cy.log.service.ILogRecordService;
import cn.cy.log.service.IOperatorGetService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

/**
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

    private final LogRecordExpressionEvaluator<String> evaluator = new LogRecordExpressionEvaluator<>();

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
        LogRecordContext.initVariableMap();
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
        OperationLog operationLog = this.operationLog(joinPoint, OperationStatus.SUCCESS, result, "").setResult(result);
        this.saveRecord(operationLog);
    }

    /**
     * 异常通知
     *
     * @param e 异常
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        OperationLog operationLog = this.operationLog(joinPoint, OperationStatus.ERROR, null, e.getMessage()).setThrowable(e);
        this.saveRecord(operationLog);
    }

    /**
     * 日志记录封装
     *
     * @param joinPoint 切点
     * @param status    状态
     * @return 日志记录
     */
    private OperationLog operationLog(JoinPoint joinPoint, OperationStatus status, Object result, String errMsg) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String traceId = method.getName() + "_" + System.currentTimeMillis();

        // 参数
        Map<String, Object> paramMap = methodParamMap(joinPoint);

        Log l = method.getAnnotation(Log.class);

        // 解析日志描述
        String description = status == OperationStatus.SUCCESS ? l.success() : l.error();

        Object targetObject = joinPoint.getTarget();
        Class<?> targetObjectClass = targetObject.getClass();
        Object[] args = joinPoint.getArgs();
        // 表达式在评估上下文中执行。正是在这种情况下，在表达式评估期间遇到引用时会解析。
        EvaluationContext evaluationContext = evaluator.createEvaluationContext(targetObject, method, targetObjectClass, args, result, errMsg);
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetObjectClass);

        // 解析 Spel 表达式
        List<String> strings = SpelUtils.parseExpression(description);
        for (String condition : strings) {
            // 执行 condition
            String value = evaluator.condition(condition, methodKey, evaluationContext, String.class);
            description = description.replaceFirst(SpelUtils.REG, Optional.ofNullable(value).orElse("null"));
        }

        // 解析审计日志
        Stack<AuditLog> auditLogStack = LogRecordContext.getAuditLog();
        if (auditLogStack != null) {
            auditLogStack.forEach(o -> o.setTraceId(traceId));
        }

        // 日志对象
        return new OperationLog()
                .setTraceId(traceId)
                .setParams(paramMap)
                .setMethod(method.getName())
                .setOperator(operatorGetService.getOperator())
                .setDescription(description)
                .setOperationStatus(status)
                .setOperation(l.operation())
                .setOperationTime(LocalDateTime.now())
                .setAuditLogList(auditLogStack);
    }

    /**
     * 保存操作日志记录
     *
     * @param operationLog 操作日志
     */
    private void saveRecord(OperationLog operationLog) {
        log.info(this.formatter(operationLog));
        if (logRecordService != null) {
            logRecordService.record(operationLog);
        }
    }

    /**
     * 方法参数获取
     *
     * @param joinPoint 切点
     * @return 方法参数，key 参数名称，value 参数值
     */
    private Map<String, Object> methodParamMap(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        Map<String, Object> paramMap = new HashMap<>(16);
        for (int i = 0; i < args.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }

        return paramMap;
    }

    /**
     * 格式化日志输出
     *
     * @param operationLog 日志对象
     * @return 日志内容
     */
    private String formatter(OperationLog operationLog) {
        return operationLog.toString();
    }

}
