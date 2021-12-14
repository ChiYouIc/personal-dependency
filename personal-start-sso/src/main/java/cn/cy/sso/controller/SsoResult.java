package cn.cy.sso.controller;

import cn.cy.framework.ResponseType;

/**
 * @author: 开水白菜
 * @description: Sso消息封装
 * @create: 2021-12-14 23:21
 **/
public class SsoResult implements ResponseType {

    private static final long serialVersionUID = 1L;
    /**
     * http 状态码
     */
    private int code;
    /**
     * 结果集返回
     */
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
