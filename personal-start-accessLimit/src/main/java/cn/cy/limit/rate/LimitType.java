package cn.cy.limit.rate;

/**
 * @Author: 友
 * @Date: 2022/5/24 17:30
 * @Description: 限流类型
 */
public enum LimitType {
    /**
     * 默认策略全局限流
     */
    DEFAULT,
    /**
     * 根据请求者IP进行限流
     */
    IP
}
