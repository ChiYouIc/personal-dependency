package cn.cy.log;

import cn.cy.log.bo.AuditLogLevel;
import cn.cy.log.expression.LogRecordContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: you
 * @date: 2022-04-18 21:22
 * @description: 日志工具
 */
public class LogUtils {

    private final static Logger log = LoggerFactory.getLogger(LogUtils.class);

    /**
     * info 日志
     *
     * @param logMsgPattern 日志消息模板
     * @param logParams     日志模板参数
     */
    public static void info(String logMsgPattern, Object... logParams) {
        String format = LogMsgFormatter.format(logMsgPattern, logParams);
        LogRecordContext.auditLog(format, AuditLogLevel.INFO);
        log.info(format);
    }

    /**
     * debug 日志
     *
     * @param logMsgPattern 日志消息模板
     * @param logParams     日志模板参数
     */
    public static void debug(String logMsgPattern, Object... logParams) {
        String format = LogMsgFormatter.format(logMsgPattern, logParams);
        log.debug(format);
    }

    /**
     * trace 日志
     *
     * @param logMsgPattern 日志消息模板
     * @param logParams     日志模板参数
     */
    public static void trace(String logMsgPattern, Object... logParams) {
        String format = LogMsgFormatter.format(logMsgPattern, logParams);
        log.trace(format);
    }

    /**
     * warn 日志
     *
     * @param logMsgPattern 日志消息模板
     * @param logParams     日志模板参数
     */
    public static void warn(String logMsgPattern, Object... logParams) {
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
    public static void error(String logMsgPattern, Object... logParams) {
        String format = LogMsgFormatter.format(logMsgPattern, logParams);
        LogRecordContext.auditLog(format, AuditLogLevel.ERROR);
        log.error(format);
    }
}
