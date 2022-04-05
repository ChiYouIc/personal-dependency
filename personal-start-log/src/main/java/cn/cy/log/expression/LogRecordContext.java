package cn.cy.log.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author: you
 * @date: 2022-04-03 12:54
 * @description: 日志记录上下文
 */
public class LogRecordContext {
    private static InheritableThreadLocal<Stack<Map<String, Object>>> variableMapStack = new InheritableThreadLocal<>();

    public static void putVariable(String key, Object value) {
        HashMap<String, Object> map = new HashMap<>(8);
        map.put(key, value);
        variableMapStack.get().push(map);
    }

    public static Map<String, Object> getVariables() {
        Stack<Map<String, Object>> stack = variableMapStack.get();
        if (stack != null && stack.size() != 0) {
            return variableMapStack.get().pop();
        }
        return null;
    }
}
