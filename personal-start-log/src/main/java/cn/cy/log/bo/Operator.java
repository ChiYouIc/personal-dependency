package cn.cy.log.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author: 友叔
 * @Date: 2022/2/24 15:52
 * @Description: 操作人信息
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Operator {

	private String id;

	private String operatorName;

	public Operator(String id, String operatorName) {
		this.id = id;
		this.operatorName = operatorName;
	}
}
