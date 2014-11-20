package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <pre>
 * <b>配置文件读取工具</b>
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
public class PropertiesUtil {
	
	/**
	 * 全局properties配置文件对象持有
	 */
	public static Properties properties;
	
	
	/**
     * 通过提供属性文件实例装载配置属性, 同时指定是否需要放入全局共享缓存属性集合中.<br/>
     * 如果提供的file不存在或者装载失败时, 直接返回null.
     * 
     * @param file 文件实例.
     * @param autoCache 是否自动缓存.
     * @return Properties
     */
    public static Properties load(final File file) {
        if (null == file) {
            return null;
        }

        if (!file.exists()) {
            return null;
        }

        InputStream in = null;
        try {
            in = new FileInputStream(file);
            return load(in);
        } catch (FileNotFoundException e) {
            return null;
        } finally {
        	if(in != null){
        		try {
					in.close();
				} catch (IOException e) {
				}
        	}
        }
    }
    
    /**
     * 通过提供输入流对象装载配置属性, 同时指定是否需要放入全局共享缓存属性集合中.<br/>
     * 如果提供的file不存在或者装载失败时, 直接返回null.
     * 
     * @param in 输入流对象
     * @param autoCache 是否自动缓存.
     * @return Properties
     */
    public static Properties load(final InputStream in) {
        if (null == in) {
            return null;
        }

        Properties props = new Properties();
        try {
            props.load(in);
        } catch (IOException e) {
            return null;
        }

        return props;
    }
	
	/**
     * 检验提供的属性集合是否为空.
     * 
     * @param props 提供的属性集合
     * @return boolean 如果非null并且size>=1, 则返回 true;其他返回false.
     */
    public static boolean isEmpty(final Properties props) {
        return (null == props || props.size() == 0);
    }
    
    /**
     * 根据key获取对应值
     * 
     * @param key
     * @return
     */
    public static Object getByKey(String key){
    	if(isEmpty(properties)){
    		return null;
    	}
    	return properties.getProperty(key);
    }
    
//    /**
//     * 获取共享缓存中指定Key对应的字符串类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回 null.
//     * 
//     * @param key 属性key
//     * @return String 字符串属性值
//     */
//    public static String getString(String key) {
//        return getString(key, null);
//    }
//
//    /**
//     * 获取共享缓存中指定Key对应的字符串类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回参数: defaultValue.
//     * 
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return String 字符串属性值.
//     */
//    public static String getString(String key, String defaultValue) {
//        return getString(key, defaultValue);
//    }
//
//    /**
//     * 获取指定属性集合中指定Key对应的String类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回 null.
//     * 
//     * @param props 属性提供实例
//     * @param key 属性key
//     * @return String 字符串属性值
//     */
//    public static String getString(Properties props, String key) {
//        return getString(props, key, null);
//    }
//
//    /**
//     * 获取指定属性集合中指定Key对应的String类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回参数: defaultValue.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return String 字符串属性值.
//     */
//    public static String getString(Properties props, String key, String defaultValue) {
//        boolean isEmpty = isEmpty(props);
//        if (isEmpty) {
//            return defaultValue;
//        } else {
//            return props.getProperty(key, defaultValue);
//        }
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的boolean类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回 false.
//     * 
//     * @param key 属性key
//     * @return Boolean
//     */
//    public static Boolean getBoolean(String key) {
//        return getBoolean(key, null);
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的boolean类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回参数: defaultValue.
//     * 
//     * @param key 属性key
//     * @param defaultValue 默认值
//     * @return Boolean
//     */
//    public static Boolean getBoolean(String key, Boolean defaultValue) {
//        return getBoolean(key, defaultValue);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的boolean类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回 false.
//     * 
//     * @param props 属性提供实例
//     * @param key 属性key
//     * @return Boolean
//     */
//    public static Boolean getBoolean(Properties props, String key) {
//        return getBoolean(props, key, null);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的boolean类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回参数: defaultValue.
//     * 
//     * @param props 属性提供实例
//     * @param key 属性key
//     * @param defaultValue 默认值
//     * @return Boolean
//     */
//    public static Boolean getBoolean(Properties props, String key, Boolean defaultValue) {
//        if (props.containsKey(key)) {
//            return ObjectUtil.toBoolean(getString(props, key), defaultValue);
//        } else {
//            return defaultValue;
//        }
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的Character类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为"", 则直接返回 null.
//     * 
//     * @param key 属性key.
//     * @return Character
//     */
//    public static Character getChar(String key) {
//        return getChar(key, null);
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的Character类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为"", 则直接返回参数: defaultValue.
//     * 
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return Character
//     */
//    public static Character getChar(String key, Character defaultValue) {
//        return getChar(key, defaultValue);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的Character类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为"", 则直接返回 null.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @return Character
//     */
//    public static Character getChar(Properties props, String key) {
//        return getChar(props, key, null);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的Character类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为"", 则直接返回参数: defaultValue.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return Character
//     */
//    public static Character getChar(Properties props, String key, Character defaultValue) {
//        if (props.containsKey(key)) {
//            String str = getString(props, key);
//            return str.length() >= 1 ? str.charAt(0) : defaultValue;
//        } else {
//            return defaultValue;
//        }
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的Integer类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
//     * 
//     * @param key 属性key
//     * @return Integer
//     */
//    public static Integer getInteger(String key) {
//        return getInteger(key, null);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的Integer类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
//     * 
//     * @param key 属性key
//     * @param defaultValue 默认值
//     * @return Integer
//     */
//    public static Integer getInteger(String key, Integer defaultValue) {
//        return getInteger(key, defaultValue);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的Integer类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
//     * 
//     * @param props 属性提供实例
//     * @param key 属性key
//     * @return Integer
//     */
//    public static Integer getInteger(Properties props, String key) {
//        return getInteger(props, key, null);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的Integer类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
//     * 
//     * @param props 属性提供实例
//     * @param key 属性key
//     * @param defaultValue 默认值
//     * @return Integer
//     */
//    public static Integer getInteger(Properties props, String key, Integer defaultValue) {
//        if (props.containsKey(key)) {
//            return ObjectUtil.toInteger(getString(props, key), defaultValue);
//        } else {
//            return defaultValue;
//        }
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的Long类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
//     * 
//     * @param key 属性key
//     * @return Long
//     */
//    public static Long getLong(String key) {
//        return getLong(key, null);
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的Long类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
//     * 
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return Long
//     */
//    public static Long getLong(String key, Long defaultValue) {
//        return getLong(key, defaultValue);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的Long类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
//     * 
//     * @param props 属性提供实例
//     * @param key 属性key
//     * @return Long
//     */
//    public static Long getLong(Properties props, String key) {
//        return getLong(props, key, null);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的Long类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return Long
//     */
//    public static Long getLong(Properties props, String key, Long defaultValue) {
//        if (props.containsKey(key)) {
//            return ObjectUtil.toLong(getString(props, key), defaultValue);
//        } else {
//            return defaultValue;
//        }
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的Float类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
//     * 
//     * @param key 属性key.
//     * @return Float
//     */
//    public static Float getFloat(String key) {
//        return getFloat(key, null);
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的Float类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
//     * 
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return Float
//     */
//    public static Float getFloat(String key, Float defaultValue) {
//        return getFloat(key, defaultValue);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的Float类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @return Float
//     */
//    public static Float getFloat(Properties props, String key) {
//        return getFloat(props, key, null);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的Float类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return Float
//     */
//    public static Float getFloat(Properties props, String key, Float defaultValue) {
//        if (props.containsKey(key)) {
//            return ObjectUtil.toFloat(getString(props, key), defaultValue);
//        } else {
//            return defaultValue;
//        }
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的Double类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
//     * 
//     * @param key 属性key.
//     * @return Double
//     */
//    public static Double getDouble(String key) {
//        return getDouble(key, null);
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的Double类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
//     * 
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return Double
//     */
//    public static Double getDouble(String key, Double defaultValue) {
//        return getDouble(key, defaultValue);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的Double类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @return Double
//     */
//    public static Double getDouble(Properties props, String key) {
//        return getDouble(props, key, null);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的Double类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return Double
//     */
//    public static Double getDouble(Properties props, String key, Double defaultValue) {
//        if (props.containsKey(key)) {
//            return ObjectUtil.toDouble(getString(props, key), defaultValue);
//        } else {
//            return defaultValue;
//        }
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的BigDecimal类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
//     * 
//     * @param key 属性key.
//     * @return Double
//     */
//    public static BigDecimal getBigDecimal(String key) {
//        return getBigDecimal(key, null);
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的BigDecimal类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
//     * 
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return Double
//     */
//    public static BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
//        return getBigDecimal(key, defaultValue);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的BigDecimal类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @return Double
//     */
//    public static BigDecimal getBigDecimal(Properties props, String key) {
//        return getBigDecimal(props, key, null);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的BigDecimal类型属性值, <br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return Double
//     */
//    public static BigDecimal getBigDecimal(Properties props, String key, BigDecimal defaultValue) {
//        if (props.containsKey(key)) {
//            String str = getString(props, key);
//            return StringUtil.hasText(str) ? new BigDecimal(str) : defaultValue;
//        } else {
//            return defaultValue;
//        }
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的Date类型属性值, <br/>
//     * 默认日期的格式为: yyyy-MM-dd<br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期格式不对, 则直接返回 null.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @return Date
//     */
//    public static Date getDate(String key) {
//        return getDate(key, null);
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的Date类型属性值, <br/>
//     * 默认日期的格式为: yyyy-MM-dd<br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期格式不对, 则直接返回参数: defaultValue.
//     * 
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return Date
//     */
//    public static Date getDate(String key, Date defaultValue) {
//        return getDate(key, defaultValue);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的Date类型属性值, <br/>
//     * 默认日期的格式为: yyyy-MM-dd<br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期格式不对, 则直接返回 null.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @return Date
//     */
//    public static Date getDate(Properties props, String key) {
//        return getDate(props, key, null);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的Date类型属性值, <br/>
//     * 默认日期的格式为: yyyy-MM-dd<br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期格式不对, 则直接返回参数: defaultValue.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return Date
//     */
//    public static Date getDate(Properties props, String key, Date defaultValue) {
//        if (props.containsKey(key)) {
//            Date date = DateUtil.parseDate(getString(props, key));
//            return (null != date ? date : defaultValue);
//        } else {
//            return defaultValue;
//        }
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的DateTiime(Date)类型属性值, <br/>
//     * 默认日期时间的格式为: yyyy-MM-dd HH:mm:ss<br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期时间格式不对, 则直接返回 null.
//     * 
//     * @param key 属性key.
//     * @return Date
//     */
//    public static Date getDateTime(String key) {
//        return getDate(key, null);
//    }
//
//    /**
//     * 获取全局共享属性集合中Key对应的DateTiime(Date)类型属性值, <br/>
//     * 默认日期时间的格式为: yyyy-MM-dd HH:mm:ss<br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期时间格式不对, 则直接返回参数: defaultValue.
//     * 
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return Date
//     */
//    public static Date getDateTime(String key, Date defaultValue) {
//        return getDate(key, defaultValue);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的DateTiime(Date)类型属性值, <br/>
//     * 默认日期时间的格式为: yyyy-MM-dd HH:mm:ss<br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期时间格式不对, 则直接返回 null.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @return Date
//     */
//    public static Date getDateTime(Properties props, String key) {
//        return getDate(props, key, null);
//    }
//
//    /**
//     * 获取指定属性集合中Key对应的DateTiime(Date)类型属性值, <br/>
//     * 默认日期时间的格式为: yyyy-MM-dd HH:mm:ss<br/>
//     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期时间格式不对, 则直接返回参数: defaultValue.
//     * 
//     * @param props 属性提供实例.
//     * @param key 属性key.
//     * @param defaultValue 默认值.
//     * @return Date
//     */
//    public static Date getDateTime(Properties props, String key, Date defaultValue) {
//        if (props.containsKey(key)) {
//            Date date = DateUtil.parseDatetime(getString(props, key));
//            return (null != date ? date : defaultValue);
//        } else {
//            return defaultValue;
//        }
//    }
	
}
