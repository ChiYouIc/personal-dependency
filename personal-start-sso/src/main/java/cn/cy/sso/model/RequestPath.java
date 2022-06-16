package cn.cy.sso.model;

import lombok.Data;

/**
 * @Author: 友
 * @Date: 2022/6/16 18:12
 * @Description:
 */
@Data
public class RequestPath {
    /**
     * 请求处理方法
     */
    private String handleMethod;

    /**
     * 请求 url
     */
    private String url;

    /**
     * 请求使用的方法，Get、Post....
     */
    private String requestMethod;

    /**
     * 参数个数
     */
    private int paramCount = 0;

}
