package com.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * <pre>
 * <b>Object对象辅助工具.</b>
 * <b>Description:</b> 
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
@SuppressWarnings("unchecked")
public abstract class ObjectUtil extends _Util {

    /**
     * 默认构造方法.
     */
    protected ObjectUtil() {
        super();
    }

    /**
     * 对象hash code的16进制字符串.
     * 
     * @param obj 对象.
     * @return String 对象hash code的16进制字符串.
     */
    public static String getHex(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }

    /**
     * 返回某个对象的元素个数.<br/>
     * 如果该对象 == null, 则返回0;<br/>
     * 如果对象类型为集合对象（Collection、Map）, 则返回集合的 size;<br/>
     * 如果对象类型为为数组, 则返回数组的 length;<br/>
     * 如果对象为其他类型, 则返回1.<br/>
     * 
     * @param obj 值对象
     * @return int 对象的元素个数
     */
    public static int getSize(Object obj) {
        // 如果对象为空, 则返回0
        if (isNull(obj)) {
            return 0;
        }
        // 对象的类型为Collection, 则返回size
        else if (obj instanceof Collection<?>) {
            return ((Collection<?>) obj).size();
        }
        // 对象的类型为Map, 则返回size
        else if (obj instanceof Map<?, ?>) {
            return ((Map<?, ?>) obj).size();
        }
        // 对象的类型为数组, 则返回数组的length
        else if (obj.getClass().isArray()) {
            return ((Object[]) obj).length;
        }
        // 其他类型, 则返回1
        else {
            return 1;
        }
    }

    /**
     * 将一个对象加入到已经存在的对象数组中（尾部）.<br/>
     * 如果对象数组为 null, 则创建一个新的对象数组;<br/>
     * 如果加入的对象元素为 null, 则直接返回对象数组.
     * 
     * @param array 已经存在的数组 (可以为null)
     * @param objs 需要加入数组的对象元素
     * @return Object[] 新的对象数组(一定不是null)
     */
    public static Object[] add2Arr(Object[] array, Object... objs) {
        if (null == objs || objs.length == 0) {
            return array;
        }

        Class<?> eleType = Object.class;
        int length = objs.length;
        if (null != array) {
            eleType = array.getClass().getComponentType();
            length = array.length + objs.length;

        } else {
            eleType = objs[0].getClass();
        }

        Object[] _array = (Object[]) Array.newInstance(eleType, length);
        if (null != array) {
            System.arraycopy(array, 0, _array, 0, array.length);
        }

        for (int i = 0; i < objs.length; i++) {
            _array[length - objs.length + i] = objs[i];
        }
        return _array;
    }

    /**
     * 深度克隆对象自己.
     * 
     * @param obj Object
     * @return Object
     */
    public static Object clone(Object obj) {
        Object _obj = null;
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = null;
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;
        try {
            oo = new ObjectOutputStream(bo);
            // 源对象
            oo.writeObject(obj);
            bi = new ByteArrayInputStream(bo.toByteArray());
            oi = new ObjectInputStream(bi);
            // 目标对象
            _obj = oi.readObject();

        } catch (Throwable e) {
            if (logger.isWarnEnabled()) {
                logger.warn("ObjectUtil.clone() error, obj:" + obj + ", reason:" + e.getMessage());
            }
        } finally {
            IoUtil.close(oi, bi, oo, bo);
        }
        return _obj;
    }

    /**
     * 深度克隆对象自己, 并返回具体的类型.
     * 
     * @param <T>
     * @param obj Object
     * @param clazz
     * @return T
     */
    public static <T> T clone(Object obj, Class<T> clazz) {
        return (T) clone(obj);
    }

    /**
     * 安全判断两个对象 相同（invoke equals）.<br/>
     * 如果两个对象都是 null, 则返回 true;<br/>
     * 如果只有一个对象是 null, 则返回 false.<br/>
     * 注意: 要定制比较的细节必须对第一个对象重载equals()方法方可, 默认使用调用Object.equals().
     * 
     * @param obj1 第一个对象.
     * @param obj2 第二个对象.
     * @return boolean 两个对象是否相同.
     */
    public static boolean equals(Object obj1, Object obj2) {
        return (obj1 == obj2 || (null != obj1 && obj1.equals(obj2)));
    }

    /**
     * 判断对象是否为Null.<br/>
     * 如果给出的对象为null 或 "&lt;null&gt;", 则返回true.<br/>
     * 例如: <br/>
     * ObjectUtil.isNull(null) == true;<br/>
     * ObjectUtil.isNull("&lt;null&gt;")== true;<br/>
     * ObjectUtil.isNull("abc") == false;<br/>
     * ObjectUtil.isNull(12345) == false;
     * 
     * @param obj 对象实例.
     * @return boolean 是否为Null.
     */
    public static boolean isNull(Object obj) {
        return (null == obj || (obj instanceof String && String.valueOf(obj).toLowerCase(Locale.ENGLISH)
                .equals(StringUtil.NULL_STR)));
    }

    /**
     * 判断一个Bean对象为空.<br/>
     * Bean对象为 == null, 则返回 true.
     * 
     * @param obj 需要检查的Bean对象.
     * @return boolean 是否为空.
     */
    public static boolean isEmpty(Object obj) {
        return (null == obj);
    }

    /**
     * 判断一个String对象为空.<br/>
     * String对象为 == null 或者 == "" 或者 == " " 或者 == "&lt;null&gt;", 则返回 true.<br/>
     * String对象为 == "a" 或者 == " a" 或者 == "a ", 则返回 false.
     * 
     * @param cs 给出的字符串.
     * @return boolean 是否为null 或 "".
     * @see java.lang.Character#isWhitespace
     */
    public static boolean isEmpty(CharSequence cs) {
        return (null == cs || cs.length() == 0);
    }

    /**
     * 判断一个对象数组为空.<br/>
     * 对象数组为 == null 或者长度为 == 0, 则返回 true.
     * 
     * @param array 需要检查的对象数组.
     * @return boolean 是否为空.
     */
    public static boolean isEmpty(Object[] array) {
        return (null == array || array.length == 0);
    }

    /**
     * 判断一个Properties对象为空.<br/>
     * 对象数组为 == null 或者长度为 == 0, 则返回 true.
     * 
     * @param props 需要检查的Properties对象.
     * @return boolean 是否为空.
     */
    public static boolean isEmpty(Properties props) {
        return (null == props || props.size() == 0);
    }

    /**
     * 判断一个Map对象为空.<br/>
     * 对象数组为 == null 或者长度为 == 0, 则返回 true.
     * 
     * @param map 需要检查的Map对象.
     * @return boolean 是否为空.
     */
    public static boolean isEmpty(Map<? extends Object, ? extends Object> map) {
        return (null == map || map.size() == 0);
    }

    /**
     * 判断一个集合对象为空.<br/>
     * 集合对象为 == null 或者长度为 == 0, 则返回 true.
     * 
     * @param collection 需要检查的集合对象.
     * @return boolean 是否为空.
     */
    public static boolean isEmpty(Collection<? extends Object> collection) {
        return (null == collection || collection.size() == 0);
    }

    /**
     * 将对象转换成Character类型.<br/>
     * 如果obj对象 == null, 则返回 null.
     * 
     * @param obj 原始对象.
     * @return Character Character类型值.
     */
    public static Character toChar(Object obj) {
        return toChar(obj, null);
    }

    /**
     * 将对象转换成Character类型.<br/>
     * 如果obj对象 == null, 则返回 defaultValue.
     * 
     * @param obj 原始对象.
     * @param defaultValue 缺省值.
     * @return Character Character类型值.
     */
    public static Character toChar(Object obj, Character defaultValue) {
        String str = String.valueOf(obj);
        if (isNull(obj) || !StringUtil.hasLength(str)) {
            return defaultValue;
        } else {
            return str.charAt(0);
        }
    }

    /**
     * 将对象转换成String类型.<br/>
     * 如果obj对象 == null, 则返回 null.
     * 
     * @param obj 原始对象.
     * @return String String类型值.
     */
    public static String toString(Object obj) {
        return toString(obj, null);
    }

    /**
     * 将对象转换成String类型.<br/>
     * 如果obj对象 == null, 则返回 defaultValue.
     * 
     * @param obj 原始对象.
     * @param defaultValue 缺省值.
     * @return String String类型值.
     */
    public static String toString(Object obj, String defaultValue) {
        return isNull(obj) ? defaultValue : String.valueOf(obj);
    }

    /**
     * 将对象转换成 Boolean类型.<br/>
     * 如果obj对象 == null 或者为 "", 则返回 null;<br/>
     * 如果obj对象是 Boolean 类型, 则直接返回 obj;<br/>
     * 如果obj对象是 数字类型并且 >0, 则返回 true;<br/>
     * 如果obj为其他类型, 则将会转为字符串, 然后判断等于 "true" 或者 "1", 则返回 true.
     * 
     * @param obj 原始对象.
     * @return Boolean Boolean类型值.
     */
    public static Boolean toBoolean(Object obj) {
        return toBoolean(obj, null);
    }

    /**
     * 将对象转换成 Boolean类型.<br/>
     * 如果obj对象 == null 或者为 "", 则返回 defaultValue;<br/>
     * 如果obj对象是 Boolean 类型, 则直接返回 obj;<br/>
     * 如果obj对象是 数字类型并且 >0, 则返回 true;<br/>
     * 如果obj为其他类型, 则将会转为字符串, 然后判断等于 "true" 或者 "1", 则返回 true.
     * 
     * @param obj 原始对象.
     * @return Boolean Boolean类型值.
     */
    public static Boolean toBoolean(Object obj, Boolean defaultValue) {
        if (isNull(obj)) {
            return defaultValue;
        }

        String str = String.valueOf(obj);
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).doubleValue() > 0;
        } else if (obj instanceof Integer || obj instanceof Long || obj instanceof Float || obj instanceof Double) {
            return Double.valueOf(str) > 0;
        } else {
            return ("true".equalsIgnoreCase(str) || "1".equals(str));
        }
    }

    /**
     * 将对象转换成Byte类型.<br/>
     * 如果obj == null 或者 非byte类型, 则返回 (byte)0.
     * 
     * @param obj 原始对象.
     * @return Byte Byte类型值.
     */
    public static Byte toByte(Object obj) {
        return toByte(obj, (byte) 0);
    }

    /**
     * 将对象转换成Byte类型.<br/>
     * 如果obj == null 或者 非byte类型, 则返回 defaultValue.
     * 
     * @param obj 原始对象.
     * @param defaultValue 缺省值.
     * @return Byte Byte类型值.
     */
    public static Byte toByte(Object obj, byte defaultValue) {
        if (!isNull(obj)) {
            if (obj instanceof Byte) {
                return (Byte) obj;
            }

            String str = String.valueOf(obj);
            try {
                return Byte.valueOf(str);
            } catch (Throwable e) {
                if (logger.isWarnEnabled()) {
                    logger.warn("ObjectUtil.toByte() error, obj:" + obj + ", defaultValue:" + defaultValue + ", reason:"
                            + e.getMessage());
                }
            }
        }
        return defaultValue;
    }

    /**
     * 将对象转换成Int类型.<br/>
     * 如果 obj == null 或者 非整数时, 则返回 0.
     * 
     * @param obj 原始对象.
     * @return Integer Integer类型值.
     */
    public static int toInt(Object obj) {
        return toInt(obj, 0);
    }

    /**
     * 将对象转换成Int类型.<br/>
     * 如果 obj == null 或者 非整数时, 则返回 defaultValue.
     * 
     * @param obj 原始对象.
     * @param defaultValue 缺省值.
     * @return Integer int类型值.
     */
    public static int toInt(Object obj, int defaultValue) {
        if (!isNull(obj)) {
            String str = String.valueOf(obj);
            try {
                return Integer.parseInt(str);
            } catch (Throwable e) {
                if (logger.isWarnEnabled()) {
                    logger.warn("ObjectUtil.toInt() error, obj:" + obj + ", defaultValue:" + defaultValue + ", reason:"
                            + e.getMessage());
                }
            }
        }
        return defaultValue;
    }

    /**
     * 将对象转换成Integer类型.<br/>
     * 如果 obj == null 或者 非整数时, 则返回 null.
     * 
     * @param obj 原始对象.
     * @return Integer Integer类型值.
     */
    public static Integer toInteger(Object obj) {
        return toInteger(obj, null);
    }

    /**
     * 将对象转换成Integer类型.<br/>
     * 如果 obj == null 或者 非整数时, 则返回 defaultValue.
     * 
     * @param obj 原始对象.
     * @param defaultValue 缺省值.
     * @return Integer Integer类型值.
     */
    public static Integer toInteger(Object obj, Integer defaultValue) {
        if (!isNull(obj)) {
            String str = String.valueOf(obj);
            int index = str.indexOf('.');
            if (index >= 0) {
                str = str.substring(0, str.indexOf('.'));
            }
            try {
                return Integer.valueOf(str);
            } catch (Throwable e) {
                if (logger.isWarnEnabled()) {
                    logger.warn("ObjectUtil.toInteger() error, obj:" + obj + ", defaultValue:" + defaultValue + ", reason:"
                            + e.getMessage());
                }
            }
        }
        return defaultValue;
    }

    /**
     * 将对象转换成Long类型.<br/>
     * 如果 obj == null 或者 非长整数时, 则返回 null.
     * 
     * @param obj 原始对象.
     * @return Long Long类型值.
     */
    public static Long toLong(Object obj) {
        return toLong(obj, null);
    }

    /**
     * 将对象转换成Long类型.<br/>
     * 如果 obj == null 或者 非长整数时, 则返回 defaultValue.
     * 
     * @param obj 原始对象.
     * @param defaultValue 缺省值.
     * @return Long Long类型值.
     */
    public static Long toLong(Object obj, Long defaultValue) {
        if (!isNull(obj)) {
            String str = String.valueOf(obj);
            int index = str.indexOf('.');
            if (index >= 0) {
                str = str.substring(0, str.indexOf('.'));
            }
            try {
                return Long.valueOf(str);
            } catch (Throwable e) {
                if (logger.isWarnEnabled()) {
                    logger.warn("ObjectUtil.toLong() error, obj:" + obj + ", defaultValue:" + defaultValue + ", reason:"
                            + e.getMessage());
                }
            }
        }
        return defaultValue;
    }

    /**
     * 将对象转换成Float类型.<br/>
     * 如果 obj == null 或者 非单精度数字时, 则返回 null.
     * 
     * @param obj 原始对象.
     * @return Float Float类型值.
     */
    public static Float toFloat(Object obj) {
        return toFloat(obj, null);
    }

    /**
     * 将对象转换成Float类型.<br/>
     * 如果 obj == null 或者 非单精度数字时, 则返回 defaultValue.
     * 
     * @param obj 原始对象.
     * @param defaultValue 缺省值.
     * @return Float Float类型值.
     */
    public static Float toFloat(Object obj, Float defaultValue) {
        if (!isNull(obj)) {
            String str = String.valueOf(obj);
            try {
                return Float.valueOf(str);
            } catch (Throwable e) {
                if (logger.isWarnEnabled()) {
                    logger.warn("ObjectUtil.toFloat() error, obj:" + obj + ", defaultValue:" + defaultValue + ", reason:"
                            + e.getMessage());
                }
            }
        }
        return defaultValue;
    }

    /**
     * 将对象转换成Double类型.<br/>
     * 如果 obj == null 或者 非双精度数字时, 则返回 null.
     * 
     * @param obj 原始对象.
     * @return Double Double类型值.
     */
    public static Double toDouble(Object obj) {
        return toDouble(obj, null);
    }

    /**
     * 将对象转换成Double类型.<br/>
     * 如果 obj == null 或者 非双精度数字时, 则返回 defaultValue.
     * 
     * @param obj 原始对象.<br/>
     * @param defaultValue 缺省值.<br/>
     * @return Double Double类型值.<br/>
     */
    public static Double toDouble(Object obj, Double defaultValue) {
        if (!isNull(obj)) {
            String str = String.valueOf(obj);
            try {
                return Double.valueOf(str);
            } catch (Throwable e) {
                if (logger.isWarnEnabled()) {
                    logger.warn("ObjectUtil.toDouble() error, obj:" + obj + ", defaultValue:" + defaultValue + ", reason:"
                            + e.getMessage());
                }
            }
        }
        return defaultValue;
    }

    /**
     * 将对象转换成Date类型.<br/>
     * 如果 obj == null 或者为 "", 则返回 null;<br/>
     * 如果 obj 有效, 则系统将采用默认的时间格式 yyyy-MM-dd HH:mm:ss.SSS 转换为Date.
     * 
     * @param obj 原始对象.
     * @return Date Date类型值.
     */
    public static Date toDate(Object obj) {
        return toDate(obj, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /**
     * 将对象转换成Date类型.<br/>
     * 如果 obj == null 或者为 "", 则返回 null.<br/>
     * 如果 format == null 或者为 "" 或者为 " ", 则返回 null;
     * 
     * @param obj 原始对象.
     * @param format 格式字符串, 如: yyyy-MM-dd HH:mm:ss.SSS.
     * @return Date Date类型值.
     */
    public static Date toDate(Object obj, String format) {
        if (isNull(obj) || StringUtil.isEmpty(format)) {
            return null;
        }
        return toDate(obj, new SimpleDateFormat(format));
    }

    /**
     * 将对象转换成Date类型.<br/>
     * 如果 obj == null 或者为 "", 则返回 null.<br/>
     * 如果 dateFormat == null 或者为 "", 则返回 null;
     * 
     * @param obj 原始对象.
     * @param dateFormat 格式对象.
     * @return Date Date类型值.
     */
    public static Date toDate(Object obj, DateFormat dateFormat) {
        String str = String.valueOf(obj);
        if (StringUtil.hasText(str)) {
            try {
                return dateFormat.parse(str);
            } catch (Throwable e) {
                if (logger.isWarnEnabled()) {
                    logger.warn("ObjectUtil.toDate() error, obj:" + obj + ", reason:" + e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * 将BASE64加密后的二进制反序列化成对象.
     * 
     * @param str
     * @return Object
     * @throws Exception
     */
    public static Object fromBinary(String str) throws Exception {
        ByteArrayInputStream bais = null;
        ObjectInputStream in = null;
        try {
            byte[] _data = BASE64Util.decode2Byte(str);
            bais = new ByteArrayInputStream(_data);
            in = new ObjectInputStream(bais);
            return in.readObject();
        } finally {
            IoUtil.close(in);
        }
    }

    /**
     * 将对象二进制序列化，然后进行BASE64加密.
     * 
     * @param obj 对象
     * @return String
     * @throws IOException
     */
    public static String toBinary(Object obj) throws IOException {
        ByteArrayOutputStream buf = null;
        ObjectOutputStream out = null;
        try {
            buf = new ByteArrayOutputStream();
            out = new ObjectOutputStream(buf);
            out.writeObject(obj);
            byte[] bt = buf.toByteArray();
            return BASE64Util.encode(bt);
        } finally {
            IoUtil.close(out);
        }
    }
}
