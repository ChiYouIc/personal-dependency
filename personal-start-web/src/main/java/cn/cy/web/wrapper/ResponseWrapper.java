package cn.cy.web.wrapper;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Http response 包装类
 *
 * @Author: 友叔
 * @Date: 2020/8/23 12:01
 * @Description: Http response 包装类
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response The response to be wrapped
     * @throws IllegalArgumentException if the response is null
     */
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }
}
