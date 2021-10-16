package cn.cy.web.advice;

import cn.cy.web.exception.AbstractExceptionHandleAdvice;
import cn.cy.web.exception.ApiException;
import cn.cy.web.response.FailedResponse;
import cn.hutool.core.exceptions.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/4 15:09
 * @Description: 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandleAdvice extends AbstractExceptionHandleAdvice implements Ordered {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandleAdvice.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<FailedResponse<Object>> handle(HttpServletRequest request, ApiException e) {
        FailedResponse<Object> response = FailedResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .msg(e.getMessage())
                .exception(ExceptionUtil.stacktraceToString(e))
                .build();
        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailedResponse<Object>> handle(HttpServletRequest request, Exception e) {
        FailedResponse<Object> response = FailedResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .msg(e.getMessage())
                .exception(ExceptionUtil.stacktraceToString(e))
                .build();
        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
