package cn.cy.web.enums;

/**
 * @Author: 友叔
 * @Date: 2020/11/26 21:25
 * @Description: 基础枚举
 */
public interface IEnum<T> {
    /**
     * 获取枚举值
     */
    public T getValue();

    /**
     * 获取枚举描述
     */
    public String getDescription();

    /**
     * 是否匹配
     *
     * @param matchedEnum 枚举
     * @return boolean
     */
    default boolean matched(IEnum<T> matchedEnum) {
        return this == matchedEnum;
    }

    /**
     * 是否匹配
     *
     * @param value 枚举值
     * @return boolean
     */
    default boolean matched(T value) {
        return this.getValue().equals(value);
    }

}
