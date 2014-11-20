package com.task;

/**
 * <pre>
 * <b>通用异常基类.</b>
 * <b>Description:</b> .
 *   
 * <b>Author:</b> liuuhong@yeah.net
 * <b>Date:</b> 2014-1-1 上午10:00:01
 * <b>Copyright:</b> Copyright &copy;2006-2015 firefly.org Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2014-01-01 10:00:01    liuuhong@yeah.net
 *         new file.
 * </pre>
 */
public class ComonException extends Throwable {

    private static final long serialVersionUID = 1L;

    /**
     * 错误代码前缀.
     */
    public static final String PRIFIX = "COMON_";

    /**
     * 默认错误代码.
     */
    public static final String UNKNOWN = "THREAD_UNKNOWN";

    // 异常代码, 默认格式为 : 模块代码+错误码, 如：COMON_UNKNOWN、COMON_00001
    private String code;

    /**
     * 构造方法.
     */
    public ComonException() {
        super();
        this.code = UNKNOWN;
    }

    /**
     * 构造方法.
     * 
     * @param code 错误代码.
     */
    public ComonException(String code) {
        super();
        this.code = code;
    }

    /**
     * 构造方法.
     * 
     * @param throwable 错误堆栈信息.
     */
    public ComonException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
        this.code = UNKNOWN;
    }

    /**
     * 构造方法.
     * 
     * @param code 错误代码.
     * @param message 自定义错误消息.
     */
    public ComonException(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造方法.
     * 
     * @param code 错误代码.
     * @param throwable 错误堆栈信息.
     */
    public ComonException(String code, Throwable throwable) {
        super(throwable.getMessage(), throwable);
        this.code = code;
    }

    /**
     * 构造方法.
     * 
     * @param code 错误代码.
     * @param message 自定义错误消息.
     * @param throwable 错误堆栈信息.
     */
    public ComonException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    /**
     * 返回 错误代码.
     * 
     * @param code 错误代码.
     */
    public String getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return this.code + ":" + this.getMessage();
    }
}
