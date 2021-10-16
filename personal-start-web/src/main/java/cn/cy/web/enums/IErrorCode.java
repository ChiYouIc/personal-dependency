package cn.cy.web.enums;

/**
 * @Author: 友叔
 * @Date: 2020/11/26 21:31
 * @Description: 封装 API 的错误码
 */
public interface IErrorCode {
    /**
     * 状态码
     */
    public Long getCode();

    /**
     * 错误消息
     */
    public String getMessage();
}
