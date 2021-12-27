package cn.cy.validator;

import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: 友叔
 * @Date: 2021/12/20 14:47
 * @Description: 自定义的List集合校验
 */
public class ValidatorList<E> implements List<E> {

	@Delegate
	@Valid
	public List<E> list;

	@Override
	public String toString() {
		return list.toString();
	}
}
