package cn.cy.log;

import cn.cy.log.bo.AuditLogLevel;
import cn.cy.log.expression.LogRecordContext;
import org.slf4j.Logger;

/**
 * @author: you
 * @date: 2022-04-18 21:22
 * @description: 日志工具
 */
public class LogUtils {


    /**
     * info 日志
     *
     * @param logMsgPattern 日志消息模板
     * @param logParams     日志模板参数
     */
    public static void info(Logger log, String logMsgPattern, Object... logParams) {
        String format = LogMsgFormatter.format(logMsgPattern, logParams);
        LogRecordContext.auditLog(format, AuditLogLevel.INFO);
        log.info(format);
    }

    /**
     * warn 日志
     *
     * @param logMsgPattern 日志消息模板
     * @param logParams     日志模板参数
     */
    public static void warn(Logger log, String logMsgPattern, Object... logParams) {
        String format = LogMsgFormatter.format(logMsgPattern, logParams);
        LogRecordContext.auditLog(format, AuditLogLevel.WARN);
        log.warn(format);
    }

    /**
     * error 日志
     *
     * @param logMsgPattern 日志消息模板
     * @param logParams     日志模板参数
     */
    public static void error(Logger log, String logMsgPattern, Object... logParams) {
        String format = LogMsgFormatter.format(logMsgPattern, logParams);
        LogRecordContext.auditLog(format, AuditLogLevel.ERROR);
        log.error(format);
    }
}
