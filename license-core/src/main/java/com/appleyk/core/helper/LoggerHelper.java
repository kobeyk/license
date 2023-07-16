package com.appleyk.core.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>日志输出辅助类</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:42 下午 2020/8/21
 */
public class LoggerHelper {

    private static Logger logger = LoggerFactory.getLogger(LoggerHelper.class);
    public LoggerHelper() {
    }

    public static void info(String message) {
        logger.info(message);
    }
    public static void debug(String message) {
        logger.debug(message);
    }
    public static void error(String message, Exception ex) {
        logger.error(message, ex);
    }
    public static void error(Integer errCode, String message) {
        logger.error("错误码：" + errCode + "，错误消息：" + message);
    }
    public static void error(String message) {
        logger.error("错误消息：" + message);
    }
    public static void error(Integer errCode, String message, Exception ex) {
        logger.error("错误码：" + errCode + "，错误消息：" + message + ",异常信息：" + ex.getMessage());
    }
}
