package cn.cy.sso.model;

/**
 * @Author: 友
 * @Date: 2022/6/16 18:12
 * @Description:
 */
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

    public String getHandleMethod() {
        return handleMethod;
    }

    public void setHandleMethod(String handleMethod) {
        this.handleMethod = handleMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public int getParamCount() {
        return paramCount;
    }

    public void setParamCount(int paramCount) {
        this.paramCount = paramCount;
    }
}
