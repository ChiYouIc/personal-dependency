package cn.cy.log.expression;

import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: you
 * @date: 2022-04-03 12:50
 * @description: 函数解析工厂
 */
public class ParseFunctionFactory {
    private Map<String, IParseFunction> allFunctionMap;

    public ParseFunctionFactory(List<IParseFunction> parseFunctions) {
        if (CollectionUtils.isEmpty(parseFunctions)) {
            return;
        }
        allFunctionMap = new HashMap<>();
        for (IParseFunction parseFunction : parseFunctions) {
            if (parseFunction.functionName().isEmpty()) {
                continue;
            }
            allFunctionMap.put(parseFunction.functionName(), parseFunction);
        }
    }

    /**
     * 获取自定义函数
     *
     * @param functionName 函数名称
     * @return 自定义函数
     */
    public IParseFunction getFunction(String functionName) {
        return allFunctionMap.get(functionName);
    }

    /**
     * 自定义函数是否在业务代码执行之前解析
     *
     * @param functionName 函数名称
     * @return boolean
     */
    public boolean isBeforeFunction(String functionName) {
        return allFunctionMap.get(functionName) != null && allFunctionMap.get(functionName).executeBefore();
    }


}
