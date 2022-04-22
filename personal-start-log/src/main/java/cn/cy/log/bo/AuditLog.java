package cn.cy.log.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author: 友
 * @Date: 2022/4/8 19:33
 * @Description: 审计日志对象
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AuditLog {

    private Long id;

    /**
     * 操作日志ID
     */
    private Long operationId;

    /**
     * 操作日志ID
     */
    private String traceId;

    /**
     * 审计内容
     */
    private String auditContent;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    /**
     * 日志级别
     */
    private AuditLogLevel level;

}
