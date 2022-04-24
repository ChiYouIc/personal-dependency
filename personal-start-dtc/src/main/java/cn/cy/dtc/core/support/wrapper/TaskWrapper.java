package cn.cy.dtc.core.support.wrapper;

/**
 * @author: you
 * @date: 2022-04-23 16:07
 * @description: 任务包装
 */
@FunctionalInterface
public interface TaskWrapper {

    /**
     * 任务包装名称
     *
     * @return name
     */
    public default String name() {
        return null;
    }

    /**
     * 对 Runnable 进行增强
     *
     * @param runnable 可运行的任务
     * @return 目标 runnable
     */
    public Runnable wrap(Runnable runnable);

}
