package cn.cy.log.expression;

import cn.cy.log.bo.AuditLog;
import cn.cy.log.bo.AuditLogLevel;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author: you
 * @date: 2022-04-03 12:54
 * @description: 日志记录上下文
 */
@Slf4j
public class LogRecordContext {

    private static InheritableThreadLocal<Stack<Map<String, Object>>> variableMapStack = new InheritableThreadLocal<>();

    private static InheritableThreadLocal<Stack<Stack<AuditLog>>> auditLogStack = new InheritableThreadLocal<>();

    /**
     * 向栈内新增一个 map，在切面方法执行前执行
     * <p>该方法需要在 aop 切面执行前执行，即需要在 @Before advice 里面调用
     */
    public static void initVariableMap() {
        // 新增一个操作日志参数 map
        Stack<Map<String, Object>> stack = variableMapStack.get();
        if (stack == null) {
            stack = new Stack<>();
            variableMapStack.set(stack);
        }
        stack.push(new HashMap<>(16));

        // 新增审计日志记录栈
        Stack<Stack<AuditLog>> auditLog = auditLogStack.get();
        if (auditLog == null) {
            auditLog = new Stack<>();
            auditLogStack.set(auditLog);
        }
        auditLog.push(new Stack<>());
    }

    /**
     * put Spel 表达式变量
     *
     * @param key   键
     * @param value 值
     */
    public static void putVariable(String key, Object value) {
        Stack<Map<String, Object>> stack = variableMapStack.get();
        Map<String, Object> map = stack.lastElement();
        map.put(key, value);
    }

    /**
     * get Spel 表达式变量
     *
     * @return 变量 map
     */
    public static Map<String, Object> getVariables() {
        Stack<Map<String, Object>> stack = variableMapStack.get();
        if (stack != null && stack.size() != 0) {
            return variableMapStack.get().pop();
        }
        return null;
    }

    /**
     * 审计日志记录
     *
     * @param msg 日志信息
     */
    public static void auditLog(String msg, AuditLogLevel level) {
        Stack<Stack<AuditLog>> stacks = auditLogStack.get();
        Stack<AuditLog> lastElement = stacks.lastElement();
        AuditLog log = new AuditLog()
                .setOperationTime(LocalDateTime.now())
                .setAuditContent(msg)
                .setLevel(level);
        lastElement.push(log);
    }

    public static Stack<AuditLog> getAuditLog() {
        Stack<Stack<AuditLog>> stacks = auditLogStack.get();
        if (stacks.size() > 0) {
            return stacks.pop();
        }
        return null;
    }
}
