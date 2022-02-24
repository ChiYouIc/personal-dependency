package cn.cy.log.service;

import cn.cy.log.bo.Operator;

/**
 * @Author: 友叔
 * @Date: 2022/2/24 15:52
 * @Description: 操作人获取Service
 */
public interface IOperatorGetService {

	/**
	 * 获取当前操作人，用于日志记录当前的动作执行者
	 *
	 * @return 操作人
	 */
	public Operator getOperator();

}
