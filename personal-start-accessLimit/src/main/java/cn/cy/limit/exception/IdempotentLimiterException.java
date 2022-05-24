package cn.cy.limit.exception;

/**
 * @Author: 友
 * @Date: 2022/5/24 18:23
 * @Description: 接口幂等性异常
 */
public class IdempotentLimiterException extends RuntimeException {
    public IdempotentLimiterException(String message) {
        super(message);
    }
}
