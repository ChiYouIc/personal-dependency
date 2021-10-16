package cn.cy.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: 友叔
 * @Date: 2021/1/4 20:43
 * @Description: 验证码验证不通过
 */
public class BadCaptchaException extends AuthenticationException {

    public BadCaptchaException() {
        super("验证错误！");
    }
}
