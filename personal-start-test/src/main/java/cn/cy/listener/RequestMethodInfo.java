package cn.cy.listener;

import lombok.Data;

/**
 * @Author: 友
 * @Date: 2022/6/1 12:27
 * @Description:
 */
@Data
public class RequestMethodInfo {

    private String handleMethod;

    private String url;

    private String requestMethod;

    private int paramCount = 0;
}
