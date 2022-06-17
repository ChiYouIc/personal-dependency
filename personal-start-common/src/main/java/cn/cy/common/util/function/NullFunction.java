package cn.cy.common.util.function;

/**
 * @Author: 友
 * @Date: 2022/5/27 12:07
 * @Description: 没有任何参数和参数值的函数
 */
@FunctionalInterface
public interface NullFunction {

    /**
     * 执行
     */
    public void run();

}