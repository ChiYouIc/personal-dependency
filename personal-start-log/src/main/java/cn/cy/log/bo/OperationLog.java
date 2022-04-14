package cn.cy.log.bo;

import cn.cy.log.OperationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Author: 友叔
 * @Date: 2022/2/24 15:58
 * @Description: 日志记录
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class OperationLog {

    private String operationLogId;

    /**
     * 操作者
     */
    private Operator operator;

    /**
     * 执行方法
     */
    private String method;

    /**
     * 参数
     */
    private Map<String, Object> params;

    /**
     * 返回结果
     */
    private Object result;

    /**
     * 日志描述
     */
    private String description;

    /**
     * 操作类型
     */
    private OperationType operation;

    /**
     * 操作状态
     */
    private OperationStatus operationStatus;

    /**
     * 异常信息
     */
    private Throwable throwable;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    /**
     * 审计日志列表
     */
    private List<AuditLog> auditLogList;
}
