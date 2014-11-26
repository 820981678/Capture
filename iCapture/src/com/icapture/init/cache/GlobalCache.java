package com.icapture.init.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.connection.db.DBException;
import com.icapture.util.GlobalLogger;
import com.util.LogsUtil;

/**
 * 全局缓存持有器
 * 
 * @author huxiaohuan
 *
 */
public class GlobalCache implements InitializingBean {
	
	/**
	 * 全局缓存持有map
	 */
	public static Map<String, List<?>> global = new HashMap<String, List<?>>();
	
	/**
	 * 缓存加载器集合
	 */
	private static Map<String,CacheService> cacheMap;
	
	/**
	 * label标签缓存key
	 */
	private static String label = "label";
	
	/**
	 * group分组缓存key
	 */
	private static String group = "group";
	
	/**
	 * 日志记录器
	 */
	private static Logger logger = GlobalLogger.init_global;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info(LogsUtil.LINE);
        logger.info(LogsUtil.PREFIX2 + "globalCache Load ...");
        for (String cache : cacheMap.keySet()) {
        	logger.info(LogsUtil.PREFIX3 + cache + " loading");
        	try {
        		cacheMap.get(cache).init();
				logger.info(LogsUtil.PREFIX3 + cache + " Loading is complete");
			} catch (DBException e) {
				logger.error(LogsUtil.PREFIX3 + cache + "load error",e);
			}
		}
        logger.info(LogsUtil.PREFIX3 + "globalCache is complete");
	}
	
	public static void init(String key){
		try {
			cacheMap.get(key).init();
		} catch (DBException e) {
			logger.error(LogsUtil.PREFIX3 + key + "cache reload error",e);
		}
	}
	
	/**
	 * 获取缓存中的值
	 * 
	 * @param key 缓存key
	 */
	@SuppressWarnings("unchecked")
	public static<T> List<T> getCache(String key,Class<T> cl){
		List<T> array = new ArrayList<T>();
		array.addAll((Collection<? extends T>) global.get(key));
		return array;
	}

	public static Map<String, CacheService> getCacheMap() {
		return cacheMap;
	}

	public static void setCacheMap(Map<String, CacheService> cacheMap) {
		GlobalCache.cacheMap = cacheMap;
	}

	public static String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		GlobalCache.label = label;
	}

	public static String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		GlobalCache.group = group;
	}
	
}
