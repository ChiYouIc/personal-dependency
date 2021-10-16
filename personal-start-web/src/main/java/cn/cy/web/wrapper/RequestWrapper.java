package cn.cy.web.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * request 包装类
 * 1.预防xss攻击
 * 2.拓展 requestBody 无限获取(HttpServletRequestWrapper只能获取一次)
 *
 * @Author: 友叔
 * @Date: 2020/8/23 11:56
 * @Description: request 包装类
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        return null;
    }

    @Override
    public String getParameter(String name) {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public String getQueryString() {
        return null;
    }

    /**
     * 使用spring HtmlUtils 转义html标签达到预防xss攻击效果
     *
     * @param str
     * @see org.springframework.web.util.HtmlUtils#htmlEscape
     */
    protected String htmlEscape(String str) {
        return null;
    }

    /**
     * 使用spring HtmlUtils 转义html标签达到预防xss攻击效果
     *
     * @param field
     * @param str
     * @see org.springframework.web.util.HtmlUtils#htmlEscape
     */
    protected String htmlEscape(String field, String str) {
        return null;
    }

}
