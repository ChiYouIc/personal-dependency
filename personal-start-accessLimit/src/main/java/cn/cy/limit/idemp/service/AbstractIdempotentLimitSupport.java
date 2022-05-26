package cn.cy.limit.idemp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author: 开水白菜
 * @description: 幂等性控制抽象实现，单机版本
 * @create: 2021-11-20 14:56
 **/
public abstract class AbstractIdempotentLimitSupport<T> implements IIdempotentLimitService<T> {

    private final Collection<String> cacheKey;

    public AbstractIdempotentLimitSupport() {
        List<String> list = new ArrayList<>();
        this.cacheKey = Collections.synchronizedCollection(list);
    }

    @Override
    public boolean containKey(String key, int time) {
        if (!cacheKey.contains(key)) {
            cacheKey.add(key);
            return false;
        }

        return true;
    }

    @Override
    public void removeKey(String key) {
        cacheKey.remove(key);
    }

}
