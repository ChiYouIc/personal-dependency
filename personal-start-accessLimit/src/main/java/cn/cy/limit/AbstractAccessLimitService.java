package cn.cy.limit;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 开水白菜
 * @description: 访问控制抽象实现
 * @create: 2021-11-20 14:56
 **/
public abstract class AbstractAccessLimitService<T> implements IAccessLimitService<T> {

    private final ConcurrentHashMap<T, Set<String>> pathMap = new ConcurrentHashMap<>();

    @Override
    public boolean containPath(String path) {
        if (pathMap.containsKey(this.connectionSign())) {
            Set<String> pathSet = pathMap.get(this.connectionSign());
            return pathSet.contains(path);
        }

        return false;
    }

    @Override
    public void addPath(String path) {
        if (pathMap.containsKey(this.connectionSign())) {
            pathMap.get(this.connectionSign()).add(path);
        } else {
            Set<String> pathSet = new HashSet<>();
            pathSet.add(path);
            pathMap.put(this.connectionSign(), pathSet);
        }
    }

    @Override
    public void removePath(String path) {
        if (pathMap.containsKey(this.connectionSign())) {
            Set<String> pathSet = pathMap.get(this.connectionSign());

            // pathSet 只是一个简单的集合容器，为了线程安全，所以在操作 remove 时，添加锁
            synchronized (this) {
                pathSet.remove(path);
            }

            // 如果当前连接已经没有正在处理请求了，那么释放掉 pathMap 中的缓存
            if (pathSet.size() == 0) {
                pathMap.remove(this.connectionSign());
            }
        }
    }
}
