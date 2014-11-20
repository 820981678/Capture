package com.util;

import java.util.Arrays;
import java.util.Collection;

/**
 * <pre>
 * <b>通用日志辅助工具.</b>
 * <b>Description:</b> 主要提供如下: 
 *   1、提供对日志输出进行做特殊标示;
 *   2、对需要日志输入的内容进行格式化转换操作;
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
public abstract class LogsUtil extends _Util {

    /**
     * Key-Value显示时, Key排版的宽度.
     */
    public static final int NAME_LENGTH = 16;

    /**
     * 日志信息分隔线.
     */
    public static final String LINE = "|-----------------------------------------------------------------------------------------------------------";

    /**
     * 日志信息分隔线.
     */
    public static final String LINE2 = "------------------------------------------------------------------------------------------------------------";

    /**
     * 模块日志标记.
     */
    public static final String PREFIX1 = "| ";

    /**
     * 功能业务日志标记.
     */
    public static final String PREFIX2 = "| @ ";
    public static final String PREFIX3 = "|   ";
    public static final String PREFIX4 = "|     ";
    public static final String PREFIX5 = "|       ";

    /**
     * 默认构造方法.
     */
    protected LogsUtil() {
        super();
    }

    /**
     * 输出字符串（String）的调试信息.
     * 
     * @param name 属性名称.
     * @param source 对应参数实例.
     * @return String 字符串形式的信息.
     */
    public static String debugInfo(String name, Object source) {
        return debugInfo(name, source, NAME_LENGTH);
    }

    /**
     * 输出字符串（String）的调试信息.
     * 
     * @param name 属性名称.
     * @param source 对应参数实例.
     * @param nameLength 属性名称自动补充的长度.
     * @return String 字符串形式的信息.
     */
    public static String debugInfo(String name, Object source, int nameLength) {
        return StringUtil.keepLen(name, nameLength, " ", false) + "= " + ObjectUtil.toString(source, StringUtil.NULL_STR);
    }

    /**
     * 输出集合（Collection）的调试信息.
     * 
     * @param name 属性名称.
     * @param coll 对应的集合实例.
     * @return String 字符串形式的调试信息.
     */
    public static String debugInfo(String name, Collection<?> coll) {
        return debugInfo(name, coll, NAME_LENGTH);
    }

    /**
     * 输出集合（Collection）的调试信息.
     * 
     * @param name 属性名称.
     * @param coll 对应的集合实例.
     * @param nameLength 属性名称自动补充的长度.
     * @return String 字符串形式的调试信息.
     */
    public static String debugInfo(String name, Collection<?> coll, int nameLength) {
        return StringUtil.keepLen(name, nameLength, " ", false) + "= {" + StringUtil.toDelimitedString(coll, SEPARATOR) + "}";
    }

    /**
     * 将对象转成日志信息字符串.
     * 
     * @param source
     * @return String
     */
    public static String toString(Object source) {
        return ObjectUtil.toString(source, StringUtil.NULL_STR);
    }

    /**
     * 将对象转成日志信息字符串.
     * 
     * @param arrays
     * @return String
     */
    public static String toString(Object[] arrays) {
        return (null != arrays ? Arrays.toString(arrays) : StringUtil.NULL_STR);
    }

    /**
     * 将对象转成日志信息字符串.
     * 
     * @param arrays
     * @return String
     */
    public static String toString(Object[][] arrays) {
        if (ObjectUtil.isEmpty(arrays)) {
            return StringUtil.NULL_STR;
        }
        StringBuffer sb = new StringBuffer("[");
        for (Object[] array : arrays) {
            if (ObjectUtil.isEmpty(array)) {
                continue;
            }
            sb.append(Arrays.toString(array));
            sb.append(SEPARATOR);
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    /**
     * 将集合转成日志信息字符串.
     * 
     * @param coll
     * @return toString
     */
    public static String toString(Collection<?> coll) {
        return StringUtil.toDelimitedString(coll, SEPARATOR);
    }

}
