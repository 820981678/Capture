package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * <pre>
 * <b>Properties 辅助工具.</b>
 * <b>Description:</b> 主要提供如下: 
 *   1、持有缓存全局共享配置属性集合.
 *   2、getXX(props, key) 根据Key获取配置属性集合中对应的属性值, 支持缺省默认值.
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
public abstract class PropsUtil extends _Util {

    /**
     * 属性文件存放的常用目录.
     */
    public static final String baseDirectory = StringUtil.cleanPath(FileUtil.CLASS_PATH + "../");

    /**
     * 全局共享缓存配置属性集合.<br/>
     * 包含Spring 装载的 Properties 配置属性, 主要由IocUtil.init()函数负责填装.
     */
    private static Properties cache = new Properties();

    /**
     * 默认构造方法.
     */
    protected PropsUtil() {
        super();
    }

    /**
     * 通过提供属性文件的相对路径装载配置属性.<br/>
     * 配置文件的装载目录默认为：FileUtil.CLASS_PATH + "../".<br>
     * 如果提供的filePath无效或者装载失败时, 直接返回null.
     * 
     * @param filePath 文件<b>相对路径</b>.
     * @return Properties
     */
    public static Properties load(final String filePath) {
        return load(filePath, false);
    }

    /**
     * 通过提供属性文件的相对路径装载配置属性, 同时指定是否需要放入全局共享缓存属性集合中.<br/>
     * 配置文件的装载目录默认为：FileUtil.CLASS_PATH + "../".<br>
     * 如果提供的filePath无效或者装载失败时, 直接返回null.
     * 
     * @param filePath 文件<b>相对路径</b>.
     * @param autoCache 是否自动缓存.
     * @return Properties
     */
    public static Properties load(final String filePath, final boolean autoCache) {
        if (!StringUtil.hasText(filePath)) {
            if (logger.isWarnEnabled()) {
                logger.warn("PropsUtil.load() error, filePath is invalid:" + filePath);
            }
            return null;
        }

        String absPath = baseDirectory + filePath;
        File file = new File(absPath);
        return load(file, autoCache);
    }

    /**
     * 通过提供属性文件实例装载配置属性.<br/>
     * 如果提供的file不存在或者装载失败时, 直接返回null.
     * 
     * @param file 文件实例.
     * @return Properties
     */
    public static Properties load(final File file) {
        return load(file, false);
    }

    /**
     * 通过提供属性文件实例装载配置属性, 同时指定是否需要放入全局共享缓存属性集合中.<br/>
     * 如果提供的file不存在或者装载失败时, 直接返回null.
     * 
     * @param file 文件实例.
     * @param autoCache 是否自动缓存.
     * @return Properties
     */
    public static Properties load(final File file, final boolean autoCache) {
        if (null == file) {
            if (logger.isWarnEnabled()) {
                logger.warn("PropsUtil.load() error, file is invalid");
            }
            return null;
        }

        if (!file.exists()) {
            if (logger.isWarnEnabled()) {
                logger.warn("PropsUtil.load() error, file isn't exists, absPath:" + file.getAbsolutePath());
            }
            return null;
        }

        InputStream in = null;
        try {
            in = new FileInputStream(file);
            return load(in, autoCache);
        } catch (FileNotFoundException e) {
            if (logger.isWarnEnabled()) {
                logger.warn("PropsUtil.load() error, file:" + file.getPath() + ", reason:" + e.getMessage());
            }
            return null;
        } finally {
            // 自动关闭文件对应的输入流.
            IoUtil.close(in);
        }
    }

    /**
     * 通过提供输入流对象装载配置属性.<br/>
     * 如果提供的in 无效或者装载失败时, 直接返回null.
     * 
     * @param in 输入流对象
     * @return Properties
     */
    public static Properties load(final InputStream in) {
        return load(in, false);
    }

    /**
     * 通过提供输入流对象装载配置属性, 同时指定是否需要放入全局共享缓存属性集合中.<br/>
     * 如果提供的file不存在或者装载失败时, 直接返回null.
     * 
     * @param in 输入流对象
     * @param autoCache 是否自动缓存.
     * @return Properties
     */
    public static Properties load(final InputStream in, final boolean autoCache) {
        if (null == in) {
            if (logger.isWarnEnabled()) {
                logger.warn("PropsUtil.load() error, inputstream is invalid");
            }
            return null;
        }

        Properties props = new Properties();
        try {
            props.load(in);
        } catch (IOException e) {
            if (logger.isWarnEnabled()) {
                logger.warn("PropsUtil.load() error, is:" + String.valueOf(in) + ", reason:" + e.getMessage());
            }
            return null;
        }

        // 判断是否需要进行自动缓存覆盖共享的属性.
        if (autoCache) {
            synchronized (cache) {
                // cache.putAll(props);
                for (Entry<Object, Object> entry : props.entrySet()) {
                    if (cache.containsKey(entry.getKey())) {
                        if (logger.isInfoEnabled()) {
                            logger.info("PropsUtil.load() warn, cache props." + entry.getKey() + " overed by value:"
                                    + entry.getValue() + " oldValue:" + cache.get(entry.getKey()));
                        }
                        cache.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
        return props;
    }

    /**
     * 检验全局共享缓存属性集合是否为空.
     * 
     * @return boolean 如果非null并且size>=1, 则返回 true;其他返回false.
     */
    public static boolean isEmpty() {
        return isEmpty(cache);
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
     * 获取全局共享缓存属性集合.
     * 
     * @return Properties
     */
    public static Properties getCache() {
        return getCache(false);
    }

    /**
     * 获取全局共享缓存属性集合并指定是否复制一份.
     * 
     * @param isCopy 是否复制一份.
     * @return Properties
     */
    static Properties getCache(final boolean isCopy) {
        if (isCopy) {
            return new Properties(cache);
        } else {
            return cache;
        }
    }

    /**
     * 通过Key的正则表达式获取全局共享缓存属性集合中符合匹配的属性并构建新的子集合.
     * 
     * @param keyRegex 查找属性Key的正则表达.
     * @return Properties 共享缓存属性的子集合.
     */
    public Properties getPropsByExpression(final String keyRegex) {
        return getPropsByExpression(cache, keyRegex);
    }

    /**
     * 通过Key的正则表达式获取指定属性集合中符合匹配的属性并构建新的子集合.
     * 
     * @param props 指定属性集合
     * @param keyRegex 查找属性Key的正则表达.
     * @return Properties 指定属性集合的子集合.
     */
    public Properties getPropsByExpression(final Properties props, final String keyRegex) {
        Properties _props = new Properties();
        Pattern pattern = Pattern.compile(keyRegex);
        for (Entry<Object, Object> entry : props.entrySet()) {
            Object key = entry.getKey();
            if (pattern.matcher(String.valueOf(key)).matches()) {
                _props.put(key, entry.getValue());
            }
        }
        return _props;
    }

    /**
     * 获取共享缓存中指定Key对应的字符串类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回 null.
     * 
     * @param key 属性key
     * @return String 字符串属性值
     */
    public static String getString(String key) {
        return getString(cache, key, null);
    }

    /**
     * 获取共享缓存中指定Key对应的字符串类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回参数: defaultValue.
     * 
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return String 字符串属性值.
     */
    public static String getString(String key, String defaultValue) {
        return getString(cache, key, defaultValue);
    }

    /**
     * 获取指定属性集合中指定Key对应的String类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回 null.
     * 
     * @param props 属性提供实例
     * @param key 属性key
     * @return String 字符串属性值
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, null);
    }

    /**
     * 获取指定属性集合中指定Key对应的String类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回参数: defaultValue.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return String 字符串属性值.
     */
    public static String getString(Properties props, String key, String defaultValue) {
        boolean isEmpty = isEmpty(props);
        if (isEmpty) {
            return defaultValue;
        } else {
            return props.getProperty(key, defaultValue);
        }
    }

    /**
     * 获取全局共享属性集合中Key对应的boolean类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回 false.
     * 
     * @param key 属性key
     * @return Boolean
     */
    public static Boolean getBoolean(String key) {
        return getBoolean(cache, key, null);
    }

    /**
     * 获取全局共享属性集合中Key对应的boolean类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回参数: defaultValue.
     * 
     * @param key 属性key
     * @param defaultValue 默认值
     * @return Boolean
     */
    public static Boolean getBoolean(String key, Boolean defaultValue) {
        return getBoolean(cache, key, defaultValue);
    }

    /**
     * 获取指定属性集合中Key对应的boolean类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回 false.
     * 
     * @param props 属性提供实例
     * @param key 属性key
     * @return Boolean
     */
    public static Boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, null);
    }

    /**
     * 获取指定属性集合中Key对应的boolean类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在, 则直接返回参数: defaultValue.
     * 
     * @param props 属性提供实例
     * @param key 属性key
     * @param defaultValue 默认值
     * @return Boolean
     */
    public static Boolean getBoolean(Properties props, String key, Boolean defaultValue) {
        if (props.containsKey(key)) {
            return ObjectUtil.toBoolean(getString(props, key), defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 获取全局共享属性集合中Key对应的Character类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为"", 则直接返回 null.
     * 
     * @param key 属性key.
     * @return Character
     */
    public static Character getChar(String key) {
        return getChar(cache, key, null);
    }

    /**
     * 获取全局共享属性集合中Key对应的Character类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为"", 则直接返回参数: defaultValue.
     * 
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return Character
     */
    public static Character getChar(String key, Character defaultValue) {
        return getChar(cache, key, defaultValue);
    }

    /**
     * 获取指定属性集合中Key对应的Character类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为"", 则直接返回 null.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @return Character
     */
    public static Character getChar(Properties props, String key) {
        return getChar(props, key, null);
    }

    /**
     * 获取指定属性集合中Key对应的Character类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为"", 则直接返回参数: defaultValue.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return Character
     */
    public static Character getChar(Properties props, String key, Character defaultValue) {
        if (props.containsKey(key)) {
            String str = getString(props, key);
            return str.length() >= 1 ? str.charAt(0) : defaultValue;
        } else {
            return defaultValue;
        }
    }

    /**
     * 获取全局共享属性集合中Key对应的Integer类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
     * 
     * @param key 属性key
     * @return Integer
     */
    public static Integer getInteger(String key) {
        return getInteger(cache, key, null);
    }

    /**
     * 获取指定属性集合中Key对应的Integer类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
     * 
     * @param key 属性key
     * @param defaultValue 默认值
     * @return Integer
     */
    public static Integer getInteger(String key, Integer defaultValue) {
        return getInteger(cache, key, defaultValue);
    }

    /**
     * 获取指定属性集合中Key对应的Integer类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
     * 
     * @param props 属性提供实例
     * @param key 属性key
     * @return Integer
     */
    public static Integer getInteger(Properties props, String key) {
        return getInteger(props, key, null);
    }

    /**
     * 获取指定属性集合中Key对应的Integer类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
     * 
     * @param props 属性提供实例
     * @param key 属性key
     * @param defaultValue 默认值
     * @return Integer
     */
    public static Integer getInteger(Properties props, String key, Integer defaultValue) {
        if (props.containsKey(key)) {
            return ObjectUtil.toInteger(getString(props, key), defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 获取全局共享属性集合中Key对应的Long类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
     * 
     * @param key 属性key
     * @return Long
     */
    public static Long getLong(String key) {
        return getLong(cache, key, null);
    }

    /**
     * 获取全局共享属性集合中Key对应的Long类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
     * 
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return Long
     */
    public static Long getLong(String key, Long defaultValue) {
        return getLong(cache, key, defaultValue);
    }

    /**
     * 获取指定属性集合中Key对应的Long类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
     * 
     * @param props 属性提供实例
     * @param key 属性key
     * @return Long
     */
    public static Long getLong(Properties props, String key) {
        return getLong(props, key, null);
    }

    /**
     * 获取指定属性集合中Key对应的Long类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return Long
     */
    public static Long getLong(Properties props, String key, Long defaultValue) {
        if (props.containsKey(key)) {
            return ObjectUtil.toLong(getString(props, key), defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 获取全局共享属性集合中Key对应的Float类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
     * 
     * @param key 属性key.
     * @return Float
     */
    public static Float getFloat(String key) {
        return getFloat(cache, key, null);
    }

    /**
     * 获取全局共享属性集合中Key对应的Float类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
     * 
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return Float
     */
    public static Float getFloat(String key, Float defaultValue) {
        return getFloat(cache, key, defaultValue);
    }

    /**
     * 获取指定属性集合中Key对应的Float类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @return Float
     */
    public static Float getFloat(Properties props, String key) {
        return getFloat(props, key, null);
    }

    /**
     * 获取指定属性集合中Key对应的Float类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return Float
     */
    public static Float getFloat(Properties props, String key, Float defaultValue) {
        if (props.containsKey(key)) {
            return ObjectUtil.toFloat(getString(props, key), defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 获取全局共享属性集合中Key对应的Double类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
     * 
     * @param key 属性key.
     * @return Double
     */
    public static Double getDouble(String key) {
        return getDouble(cache, key, null);
    }

    /**
     * 获取全局共享属性集合中Key对应的Double类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
     * 
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return Double
     */
    public static Double getDouble(String key, Double defaultValue) {
        return getDouble(cache, key, defaultValue);
    }

    /**
     * 获取指定属性集合中Key对应的Double类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @return Double
     */
    public static Double getDouble(Properties props, String key) {
        return getDouble(props, key, null);
    }

    /**
     * 获取指定属性集合中Key对应的Double类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return Double
     */
    public static Double getDouble(Properties props, String key, Double defaultValue) {
        if (props.containsKey(key)) {
            return ObjectUtil.toDouble(getString(props, key), defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 获取全局共享属性集合中Key对应的BigDecimal类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
     * 
     * @param key 属性key.
     * @return Double
     */
    public static BigDecimal getBigDecimal(String key) {
        return getBigDecimal(cache, key, null);
    }

    /**
     * 获取全局共享属性集合中Key对应的BigDecimal类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
     * 
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return Double
     */
    public static BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        return getBigDecimal(cache, key, defaultValue);
    }

    /**
     * 获取指定属性集合中Key对应的BigDecimal类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回 null.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @return Double
     */
    public static BigDecimal getBigDecimal(Properties props, String key) {
        return getBigDecimal(props, key, null);
    }

    /**
     * 获取指定属性集合中Key对应的BigDecimal类型属性值, <br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者属性值为非数字, 则直接返回参数: defaultValue.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return Double
     */
    public static BigDecimal getBigDecimal(Properties props, String key, BigDecimal defaultValue) {
        if (props.containsKey(key)) {
            String str = getString(props, key);
            return StringUtil.hasText(str) ? new BigDecimal(str) : defaultValue;
        } else {
            return defaultValue;
        }
    }

    /**
     * 获取全局共享属性集合中Key对应的Date类型属性值, <br/>
     * 默认日期的格式为: yyyy-MM-dd<br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期格式不对, 则直接返回 null.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @return Date
     */
    public static Date getDate(String key) {
        return getDate(cache, key, null);
    }

    /**
     * 获取全局共享属性集合中Key对应的Date类型属性值, <br/>
     * 默认日期的格式为: yyyy-MM-dd<br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期格式不对, 则直接返回参数: defaultValue.
     * 
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return Date
     */
    public static Date getDate(String key, Date defaultValue) {
        return getDate(cache, key, defaultValue);
    }

    /**
     * 获取指定属性集合中Key对应的Date类型属性值, <br/>
     * 默认日期的格式为: yyyy-MM-dd<br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期格式不对, 则直接返回 null.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @return Date
     */
    public static Date getDate(Properties props, String key) {
        return getDate(props, key, null);
    }

    /**
     * 获取指定属性集合中Key对应的Date类型属性值, <br/>
     * 默认日期的格式为: yyyy-MM-dd<br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期格式不对, 则直接返回参数: defaultValue.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return Date
     */
    public static Date getDate(Properties props, String key, Date defaultValue) {
        if (props.containsKey(key)) {
            Date date = TimeUtil.parseDate(getString(props, key));
            return (null != date ? date : defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 获取全局共享属性集合中Key对应的DateTiime(Date)类型属性值, <br/>
     * 默认日期时间的格式为: yyyy-MM-dd HH:mm:ss<br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期时间格式不对, 则直接返回 null.
     * 
     * @param key 属性key.
     * @return Date
     */
    public static Date getDateTime(String key) {
        return getDate(cache, key, null);
    }

    /**
     * 获取全局共享属性集合中Key对应的DateTiime(Date)类型属性值, <br/>
     * 默认日期时间的格式为: yyyy-MM-dd HH:mm:ss<br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期时间格式不对, 则直接返回参数: defaultValue.
     * 
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return Date
     */
    public static Date getDateTime(String key, Date defaultValue) {
        return getDate(cache, key, defaultValue);
    }

    /**
     * 获取指定属性集合中Key对应的DateTiime(Date)类型属性值, <br/>
     * 默认日期时间的格式为: yyyy-MM-dd HH:mm:ss<br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期时间格式不对, 则直接返回 null.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @return Date
     */
    public static Date getDateTime(Properties props, String key) {
        return getDate(props, key, null);
    }

    /**
     * 获取指定属性集合中Key对应的DateTiime(Date)类型属性值, <br/>
     * 默认日期时间的格式为: yyyy-MM-dd HH:mm:ss<br/>
     * 如果提供的 props == null 或者 Key对应的属性不存在 或者日期时间格式不对, 则直接返回参数: defaultValue.
     * 
     * @param props 属性提供实例.
     * @param key 属性key.
     * @param defaultValue 默认值.
     * @return Date
     */
    public static Date getDateTime(Properties props, String key, Date defaultValue) {
        if (props.containsKey(key)) {
            Date date = TimeUtil.parseDatetime(getString(props, key));
            return (null != date ? date : defaultValue);
        } else {
            return defaultValue;
        }
    }
}
