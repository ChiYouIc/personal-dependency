package cn.cy.common.util;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: 友
 * @Date: 2022/4/24 9:19
 * @Description: stream 工具集
 */
public class StreamUtils {
    private StreamUtils() {
    }

    /**
     * collection 转 map
     *
     * @param coll 集合
     * @param key  获取键的函数式接口
     * @param <P>  值类型
     * @param <O>  键类型
     * @return map
     */
    public static <P, O> Map<O, P> toMap(Collection<P> coll, Function<P, O> key) {
        if (CollUtil.isEmpty(coll) || Objects.isNull(key)) {
            return Collections.emptyMap();
        }

        return coll.stream().collect(Collectors.toMap(key, Function.identity(), (v1, v2) -> v2));
    }
}
