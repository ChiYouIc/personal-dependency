package cn.cy.validator.encrypt;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author: 友叔
 * @Date: 2021/12/20 14:57
 * @Description: Id加密校验
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EncryptIdValidator.class})
public @interface EncryptId {
	/**
	 * 默认的错误消息
	 */
	String message() default "id加密格式错误";

	/**
	 * 分组
	 */
	Class<?>[] groups() default {};

	/**
	 * 负载
	 */
	Class<? extends Payload>[] payload() default {};
}
