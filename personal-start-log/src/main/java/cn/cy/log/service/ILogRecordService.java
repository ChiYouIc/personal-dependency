package cn.cy.log.service;

import cn.cy.log.bo.OperationLog;

/**
 * @Author: 友叔
 * @Date: 2022/2/24 18:09
 * @Description: 日志记录Service，具体保存逻辑，请自行实现
 */
public interface ILogRecordService {

    /**
     * 保存操作日志日志
     *
     * @param operationLog 日志记录
     */
    public void record(OperationLog operationLog);

}
