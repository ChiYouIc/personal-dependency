package cn.cy.common.util.function;

import cn.hutool.core.util.ObjectUtil;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 函数式编程工具，
 *
 * @Author: 友叔
 * @Date: 2021/6/16 14:53
 * @Description: 执行器工具
 */
public class ActuatorUtil {

    /**
     * 当传入参数不为空时执行
     *
     * @param param    参数
     * @param function 方法
     * @param <T>      参数类型
     * @param <R>      结果类型
     * @return 执行结果
     */
    public static <T, R> R isNotEmpty(T param, Function<T, R> function) {
        if (ObjectUtil.isNotEmpty(param)) {
            return function.apply(param);
        }
        return null;
    }

    /**
     * 当传入参数不为空时执行
     *
     * @param param    参数
     * @param consumer 方法
     * @param <T>      参数类型
     */
    public static <T> void isNotEmpty(T param, Consumer<T> consumer) {
        if (ObjectUtil.isNotEmpty(param)) {
            consumer.accept(param);
        }
    }

    /**
     * 当传入参数不为空时执行
     *
     * @param param    参数
     * @param function 方法
     * @param <T>      参数类型
     * @return 执行结果
     */
    public static <T> void isNotEmpty(T param, NullFunction function) {
        if (ObjectUtil.isNotEmpty(param)) {
            function.run();
        }
    }

    /**
     * 当传入参数为空执行
     *
     * @param param    参数
     * @param function 方法
     * @param <T>      参数类型
     * @param <R>      返回类型
     */
    public static <T, R> R isEmpty(T param, Function<T, R> function) {
        if (ObjectUtil.isEmpty(param)) {
            return function.apply(param);
        }
        return null;
    }

    /**
     * 当传入参数为空执行
     *
     * @param param    参数
     * @param consumer 方法
     * @param <T>      参数类型
     */
    public static <T> void isEmpty(T param, Consumer<T> consumer) {
        if (ObjectUtil.isEmpty(param)) {
            consumer.accept(param);
        }
    }

    /**
     * 当传入参数为空执行
     *
     * @param param 参数
     * @param <T>   参数类型
     */
    public static <T> void isEmpty(T param, NullFunction run) {
        if (ObjectUtil.isEmpty(param)) {
            run.run();
        }
    }

    /**
     * 当传入参数为空执行
     *
     * @param param 参数
     */
    public static void isTure(boolean param, NullFunction run) {
        if (param) {
            run.run();
        }
    }

    /**
     * 当传入参数为空执行
     *
     * @param param 参数
     */
    public static void isFalse(boolean param, NullFunction run) {
        if (!param) {
            run.run();
        }
    }

}
