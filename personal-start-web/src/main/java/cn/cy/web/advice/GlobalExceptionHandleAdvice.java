package cn.cy.web.advice;

import cn.cy.web.exception.AbstractExceptionHandleAdvice;
import cn.cy.web.exception.ApiException;
import cn.cy.web.response.FailedResponse;
import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandleAdvice extends AbstractExceptionHandleAdvice implements Ordered {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<FailedResponse> handleApiException(HttpServletRequest request, ApiException e) {
        FailedResponse response = FailedResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .msg(e.getMessage())
                .exception(ExceptionUtil.stacktraceToString(e))
                .build();
        log.error(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailedResponse> handle(HttpServletRequest request, Exception e) {
        FailedResponse response = FailedResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .msg(e.getMessage())
                .exception(ExceptionUtil.stacktraceToString(e))
                .build();
        log.error(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
