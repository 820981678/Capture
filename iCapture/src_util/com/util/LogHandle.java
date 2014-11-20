package com.util;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * <pre>
 * <b>日志工具类</b>
 * <b>Description:</b> 主要提供如下: 
 *   1、自动生成日志对象
 * 
 * <b>Author:</b> huxiaohuan
 * <b>Date:</b> 2014-1-1 上午10:00:01
 * <b>Copyright:</b> Copyright &copy;2006-2015 firefly.org Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2014-01-01 10:00:01    huxiaohuan
 *         new file.
 * </pre>
 */
public class LogHandle {
	
	/**
     * 日志默认的输出级别.
     */
    public static final Level DEFAULT_LEVEL = Level.DEBUG;

    /**
     * 默认控制台输出信息的类别：System.out.
     */
    public static final String DEFAULT_OUTPUT_TYPE = "System.out";

    /**
     * 默认的日志格式：[%d{yyyy-MM-dd HH:mm:ss.SSS}][%5p][%6c] %m%n
     */
    public static final String DEFAULT_LAYOUT = "[%d{YYYY-MM-dd HH:mm:ss.SSS}][%5p][%11c] %m%n";

    /**
     * 默认日志文件生成文件的重命名表达式：yyyy-MM-dd.
     */
    public static final String DEFAULT_DAILYROLLING_EXPRESSION = "yyyy-MM-dd";

    /**
     * 将日志文件分别输出到控制台和相应的日志文件.
     */
    public static final boolean DEFAULT_ADDITIVITY = true;

    /**
     * 程序中所有待使用的日志记录器容器.
     */
    private static Map<String, Logger> loggers = new ConcurrentHashMap<String, Logger>(16);

    /**
     * 多线程并发控制锁.
     */
    private static final Object _lock = new Object();

    /**
     * 日志记录器.
     */
    protected static final Log logger = LogFactory.getLog("comon.logger");

    /**
     * 私有构造方法, 禁止实例化.
     */
    private LogHandle() {
        super();
    }

    /**
     * 根据日志记录器名称获取记录器.<br/>
     * 如果配置不存在, 则采用默认配置.
     * 
     * @param loggerName 日志记录器名称.
     * @return Logger
     */
    public static Logger getLogger(final String loggerName) {
        // 采用默认配置.
        return getLogger(loggerName, DEFAULT_LEVEL, DEFAULT_LAYOUT, DEFAULT_ADDITIVITY);
    }

    /**
     * 根据日志记录器名称获取记录器.
     * 
     * @param loggerName
     * @param level
     * @param layout
     * @param additivity
     * @return Logger
     */
    public static Logger getLogger(final String loggerName, final Level level, final String layout, final Boolean additivity) {
        // 如果日志名称为无效的，则返回null.
        if (StringUtil.isBlank(loggerName)) {
            return null;
        }

        // 判断当前是否存在日志记录器.
        if (!loggers.containsKey(loggerName)) {
            synchronized (_lock) {
                if (!loggers.containsKey(loggerName)) {
                    Logger _logger = bulidLogger(loggerName, level, layout, additivity);
                    loggers.put(loggerName, _logger);
                    return _logger;
                }
            }
        }
        // 直接返回缓存中以创建的日志记录器.
        return loggers.get(loggerName);
    }

    /**
     * 构建Logger实例.
     * 
     * @param loggerName
     * @param level
     * @param layout
     * @param additivity
     * @return
     */
    private static Logger bulidLogger(final String loggerName, Level level, String layout, Boolean additivity) {
        Logger _logger = Logger.getLogger(loggerName);
        level = (null != level) ? level : Level.ERROR;
        layout = (StringUtil.isBlank(layout)) ? DEFAULT_LAYOUT : layout;
        String fileName = FileUtil.BASE_PATH + "../../logs/" + loggerName + ".log";

        _logger.setLevel(level);
        PatternLayout _layout = new PatternLayout();
        _layout.setConversionPattern(layout);
        try {
            Appender fileAppender = new DailyRollingFileAppender(_layout, fileName, DEFAULT_DAILYROLLING_EXPRESSION);
            // fileAppender = new RollingFileAppender(_layout, _fileName, true);
            // fileAppender.setMaximumFileSize(1024 * 1024 * 1024 * 32); //
            // 每个文件32MB.
            // fileAppender.setMaxBackupIndex(1024); // 可保留128个文件.
            _logger.addAppender(fileAppender);
        } catch (IOException e) {
            logger.error("bulid " + loggerName + "logger failed, invalid log fileName:" + fileName, e);
        }

        additivity = (null != additivity) ? additivity : DEFAULT_ADDITIVITY;
        if (additivity) {
            Appender outAppender = new ConsoleAppender(_layout, DEFAULT_OUTPUT_TYPE);
            outAppender.setLayout(_layout);
            _logger.addAppender(outAppender);
            _logger.setAdditivity(additivity);
        }

        return _logger;
    }
}
