package cn.cy.mybatis.type;

/**
 * @Author: 友叔
 * @Date: 2021/12/28 11:33
 * @Description: 主键Id
 */
public class PrimaryId {

	private String value;

	public PrimaryId(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
