package cn.cy.log;

import cn.cy.log.expression.LogRecordContext;

/**
 * 表达式工具
 *
 * @author: you
 * @date: 2022-04-21 8:59
 * @description: Spel 表达式参数工具
 */
public class ExpUtils {

    /**
     * 向 Sepl 表达式传入变量
     *
     * @param key   变量名
     * @param value 变量值
     */
    public static void variable(String key, String value) {
        LogRecordContext.putVariable(key, value);
    }
}
