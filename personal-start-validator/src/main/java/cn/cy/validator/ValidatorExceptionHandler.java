package cn.cy.validator;

import cn.cy.web.exception.AbstractExceptionHandleAdvice;
import cn.cy.web.response.FailedResponse;
import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * @Author: 友叔
 * @Date: 2021/12/20 14:20
 * @Description: 全局的参数校验异常处理器
 */
@Slf4j
@ControllerAdvice
public class ValidatorExceptionHandler extends AbstractExceptionHandleAdvice implements Ordered {

	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<FailedResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		StringBuilder sb = new StringBuilder("校验失败:");
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			sb.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append(", ");
		}

		String msg = sb.toString();
		FailedResponse response = FailedResponse.builder()
				.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.msg(msg)
				.exception(ExceptionUtil.stacktraceToString(e))
				.build();

		log.error(e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<FailedResponse> handleConstraintViolationException(ConstraintViolationException e) {

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
		return 99;
	}
}
