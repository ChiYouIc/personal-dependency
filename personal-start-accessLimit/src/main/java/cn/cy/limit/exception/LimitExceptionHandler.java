package cn.cy.limit.exception;

import cn.cy.web.exception.AbstractExceptionHandleAdvice;
import cn.cy.web.response.FailedResponse;
import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: 友
 * @Date: 2022/5/24 18:11
 * @Description: 异常处理
 */
@Slf4j
@RestControllerAdvice
public class LimitExceptionHandler extends AbstractExceptionHandleAdvice implements Ordered {

    /**
     * 处理接口限流异常
     *
     * @param e 异常
     * @return 处理结果
     */
    @ExceptionHandler({RateLimiterException.class})
    public ResponseEntity<FailedResponse> handleRateLimiterException(RateLimiterException e) {

        FailedResponse response = FailedResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .msg(e.getMessage())
                .exception(ExceptionUtil.stacktraceToString(e))
                .build();

        log.error(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理接口幂等性异常
     *
     * @param e 异常
     * @return 处理结果
     */
    @ExceptionHandler({IdempotentLimiterException.class})
    public ResponseEntity<FailedResponse> handleIdempotentLimiterException(IdempotentLimiterException e) {

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
        return 101;
    }
}
