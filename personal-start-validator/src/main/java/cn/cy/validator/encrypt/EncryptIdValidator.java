package cn.cy.validator.encrypt;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author: 友叔
 * @Date: 2021/12/20 14:59
 * @Description: Id加密校验器
 */
@Slf4j
public class EncryptIdValidator implements ConstraintValidator<EncryptId, String> {

	/**
	 * 实现验证逻辑。 {@code value} 状态不得改变。
	 * 该方法可以并发访问，必须通过实现来保证线程安全。
	 *
	 * @param value   要验证的对象
	 * @param context 评估约束的上下文
	 * @return 如果 value 未通过约束，则为 false
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return false;
	}

	/**
	 * 初始化验证器以准备 {@link #isValid(String, ConstraintValidatorContext)} 调用。 传递给定约束声明的约束注释。
	 * 保证在使用此实例进行任何验证之前调用此方法。
	 * 默认实现是无操作。
	 *
	 * @param constraintAnnotation 约束注解实例
	 */
	@Override
	public void initialize(EncryptId constraintAnnotation) {


	}
}
