package cn.cy.log.expression;

/**
 * @author: you
 * @date: 2022-04-03 12:49
 * @description: 自定义函数
 */
public interface IFunctionService {

    public String apply(String functionName, String value);

    public boolean beforeFunction(String functionName);

}
