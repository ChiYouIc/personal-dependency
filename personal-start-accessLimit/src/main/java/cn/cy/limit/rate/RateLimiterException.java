package cn.cy.limit.rate;

/**
 * @Author: 友
 * @Date: 2022/5/24 17:54
 * @Description: 接口限流异常
 */
public class RateLimiterException extends RuntimeException {

    public RateLimiterException(String message) {
        super(message);
    }
}
