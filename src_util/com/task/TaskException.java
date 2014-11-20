package com.task;

/**
 * <pre>
 * <b>任务调度操作异常基类.</b>
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
public class TaskException extends ComonException {

    private static final long serialVersionUID = 1L;

    // 默认错误代码.
    private static final String UNKNOWN = "TASK_UNKNOWN";

    public static final String PAUSE_ERROR = "TASK_PAUSE_ERROR";

    /**
     * 构造方法.
     */
    public TaskException() {
        super(UNKNOWN);
    }

    /**
     * 构造方法.
     * 
     * @param code 错误代码.
     */
    public TaskException(String code) {
        super(code);
    }

    /**
     * 构造方法.
     * 
     * @param throwable 错误堆栈信息.
     */
    public TaskException(Throwable throwable) {
        super(UNKNOWN, throwable);
    }

    /**
     * 构造方法.
     * 
     * @param code 错误代码.
     * @param message 自定义错误消息.
     */
    public TaskException(String code, String message) {
        super(code, message);
    }

    /**
     * 构造方法.
     * 
     * @param code 错误代码.
     * @param throwable 错误堆栈信息.
     */
    public TaskException(String code, Throwable throwable) {
        super(code, throwable);
    }

    /**
     * 构造方法.
     * 
     * @param code 错误代码.
     * @param message 自定义错误消息.
     * @param throwable 错误堆栈信息.
     */
    public TaskException(String code, String message, Throwable throwable) {
        super(code, message, throwable);
    }

}
