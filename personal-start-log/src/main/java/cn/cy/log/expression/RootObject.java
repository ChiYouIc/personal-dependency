package cn.cy.log.expression;

/**
 * @author: you
 * @date: 2022-04-05 14:27
 * @description: 根对象，用于处理 Spel 表达式解析，其中参数 object 对应的解析根对象，而 args 是目标方法参数
 */
public class RootObject {
    
    private final Object object;

    private final Object[] args;

    public RootObject(Object object, Object[] args) {
        this.object = object;
        this.args = args;
    }

    public Object getObject() {
        return object;
    }

    public Object[] getArgs() {
        return args;
    }
}
