package me.rowkey.libs.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Author: Bryant Hang
 * Date: 16/6/28
 * Time: 16:43
 */
public class LoggerUtil {// log4j
    private static String FQCN = LoggerUtil.class.getName();

    private static final Logger LOGGER = Logger.getLogger(LoggerUtil.class.getName());

    public static synchronized void debug(Logger logger, String msg) {
        if (logger.isDebugEnabled()) {
            logger.debug(msg);
        }
    }

    public static void info(Logger logger, String msg) {
        if (logger.isInfoEnabled()) {
            logger.info(msg);
        }

    }

    public static void error(Logger logger, String msg) {
        // 错误直接打
        logger.error(msg);
    }

    public static void info(Object message) {
        info(message, null);
    }

    public static void info(Object message, Throwable t) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.log(FQCN, Level.INFO, message, t);
        }
    }

    public static void debug(Object message) {
        debug(message, null);
    }

    public static void debug(Object message, Throwable t) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.log(FQCN, Level.DEBUG, message, t);
        }
    }

    public static void warn(Object message) {
        warn(message, null);
    }

    public static void warn(Object message, Throwable t) {
        LOGGER.log(FQCN, Level.WARN, message, t);
    }

    public static void error(Object message) {
        error(message, null);
    }

    public static void error(Object message, Throwable t) {
        LOGGER.log(FQCN, Level.ERROR, message, t);
    }

    public static void trace(Object message) {
        trace(message, null);
    }

    public static void trace(Object message, Throwable t) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.log(FQCN, Level.TRACE, message, t);
        }
    }

    public static void fatal(Object message) {
        fatal(message, null);
    }

    public static void fatal(Object message, Throwable t) {
        LOGGER.log(FQCN, Level.FATAL, message, t);
    }
}
