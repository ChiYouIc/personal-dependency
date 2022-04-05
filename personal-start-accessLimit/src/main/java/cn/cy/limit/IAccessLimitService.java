package cn.cy.limit;

/**
 * @author: 开水白菜
 * @description: 访问控制接口
 * @create: 2021-11-20 14:38
 *
 * <p>
 * 该接口提供了一个抽象类 {@link AbstractAccessLimitService} 进行了一个默认实现，但是该抽象类
 * 并未完全实现这里面的所有接口，除 {@link #connectionSign()} 以外，其他的方法都已实现。
 * {@link #connectionSign()} 方法主要是用于获取请求标识的，可理解为用户，当前请求是哪个用户发起的，
 * 这个方法不提供默认实现，但又是必要的，由引用者自己去实现。如果引用者的应用程序实际生产环境中是多个实例，
 * 而需要引入像 Redis 这样的中间件时，可以直接实现 {@link IAccessLimitService} 接口，
 * 如果只是单机模式，那么直接继承 {@link AbstractAccessLimitService} 抽象类，并实现 {@link #connectionSign()}
 * 方法已经就可以满足需求了。
 **/
public interface IAccessLimitService<T> {

    /**
     * 获取请求标记，该标记用来表示当前是谁发起的请求
     *
     * @return 标记
     */
    public T connectionSign();

    /**
     * 该访问路径是否正处于访问中（请求未结束）
     *
     * @param path 访问路径
     * @return true 请求进行中，false 请求已结束
     */
    public boolean containPath(String path);

    /**
     * 添加path
     *
     * @param path 访问路径
     */
    public void addPath(String path);

    /**
     * 移除 path
     *
     * @param path 访问路径
     */
    public void removePath(String path);
}
