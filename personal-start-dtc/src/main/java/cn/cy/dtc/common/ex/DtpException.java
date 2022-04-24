package cn.cy.dtc.common.ex;

/**
 * 动态线程池异常
 *
 * @author: you
 * @date: 2022-04-23 16:42
 */
public class DtpException extends RuntimeException {
    public DtpException(String message) {
        super(message);
    }
}
