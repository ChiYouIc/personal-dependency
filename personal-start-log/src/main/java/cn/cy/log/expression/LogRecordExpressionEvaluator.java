package cn.cy.log.expression;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: you
 * @date: 2022-04-03 12:31
 * @description: 日志记录表达式求值器
 */
public class LogRecordExpressionEvaluator<T> extends CachedExpressionEvaluator {

    /**
     * 用于发现方法和构造函数的参数名称的接口
     */
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    /**
     * Spel 表达式缓存
     */
    private final Map<ExpressionKey, Expression> expressionCache = new ConcurrentHashMap<>(64);

    /**
     * 目标方法缓存
     */
    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

    /**
     * 创建表达式上下文
     *
     * @param method      目标方法
     * @param targetClass method 所在对象的类型
     * @param args        方法参数
     * @return 日志记录评估上下文
     */
    public EvaluationContext createEvaluationContext(Object object, Method method, Class<?> targetClass, Object[] args, Object result, String errorMsg) {
        Method targetMethod = this.getTargetMethod(targetClass, method);
        RootObject rootObject = new RootObject(object, args);
        return new LogRecordEvaluationContext(rootObject, targetMethod, args, this.parameterNameDiscoverer, result, errorMsg);
    }

    /**
     * 执行 Spel 条件表达式获取结果
     *
     * @param conditionExpression 条件表达式
     * @param elementKey          方法键，用于在
     * @param evalContext         表达式上下文
     * @param clazz               结果类型对象
     * @return 结果
     */
    public T condition(String conditionExpression, AnnotatedElementKey elementKey, EvaluationContext evalContext, Class<T> clazz) {
        // 获取表达式，并获取表达式的值
        return getExpression(this.expressionCache, elementKey, conditionExpression).getValue(evalContext, clazz);
    }

    /**
     * 获取目标方法
     *
     * @param targetClass method 所在对象的类型
     * @param method      方法实例
     * @return 方法实例
     */
    private Method getTargetMethod(Class<?> targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        Method targetMethod = this.targetMethodCache.get(methodKey);
        if (targetMethod == null) {
            // 获取具体的方法
            targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            this.targetMethodCache.put(methodKey, targetMethod);
        }
        return targetMethod;
    }

}
