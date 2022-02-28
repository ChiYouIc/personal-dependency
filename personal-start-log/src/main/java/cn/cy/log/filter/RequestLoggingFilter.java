package cn.cy.log.filter;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 友叔
 * @Date: 2022/2/25 14:17
 * @Description: 请求日志过滤器，通过它可以实现记录每一次的请求连接信息，
 * @see org.springframework.web.filter.CommonsRequestLoggingFilter
 */
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

	@Override
	protected boolean shouldLog(HttpServletRequest request) {
		return logger.isDebugEnabled();
	}

	/**
	 * 请求被处理前
	 *
	 * @param request 请求
	 * @param message 请求消息
	 */
	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		logger.debug(message);
	}

	/**
	 * 请求处理后执行
	 *
	 * @param request 请求
	 * @param message 请求消息
	 */
	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		logger.debug(message);
	}
}
