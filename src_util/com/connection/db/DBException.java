package com.connection.db;

import com.connection.exception.ComonException;

/**
 * <pre>
 * <b>DB操作异常基类.</b>
 * <b>Description:</b> .
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
public class DBException extends ComonException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误代码前缀.
     */
    public static final String PRIFIX = "DB_";

    /**
     * 默认错误代码.
     */
    public static final String UNKNOWN = "THREAD_UNKNOWN";

    /**
     * 构造方法.
     */
    public DBException() {
        super(UNKNOWN);
    }

    /**
     * 构造方法.
     * 
     * @param code 错误代码.
     */
    public DBException(String code) {
        super(code);
    }

    /**
     * 构造方法.
     * 
     * @param throwable 错误堆栈信息.
     */
    public DBException(Throwable throwable) {
        super(UNKNOWN, throwable);
    }

    /**
     * 构造方法.
     * 
     * @param code 错误代码.
     * @param message 自定义错误消息.
     */
    public DBException(String code, String message) {
        super(code, message);
    }

    /**
     * 构造方法.
     * 
     * @param code 错误代码.
     * @param throwable 错误堆栈信息.
     */
    public DBException(String code, Throwable throwable) {
        super(code, throwable);
    }

    /**
     * 构造方法.
     * 
     * @param code 错误代码.
     * @param message 自定义错误消息.
     * @param throwable 错误堆栈信息.
     */
    public DBException(String code, String message, Throwable throwable) {
        super(code, message, throwable);
    }

}
