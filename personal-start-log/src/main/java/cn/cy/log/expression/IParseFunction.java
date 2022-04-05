package cn.cy.log.expression;

/**
 * @author: you
 * @date: 2022-04-03 12:50
 * @description: 函数解析接口
 */
public interface IParseFunction {

    /**
     * 自定义函数是否在业务代码执行之前解析
     *
     * @return boolean
     */
    default boolean executeBefore() {
        return false;
    }

    /**
     * 获取函数名称
     *
     * @return 函数名称
     */
    public String functionName();

    /**
     * @param value
     * @return
     */
    public String apply(String value);
}
