package com.icapture.init.cache;

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
	private List<CacheService> cacheList;
	
	/**
	 * label标签缓存key
	 */
	public static final String _label = "label";
	
	/**
	 * group分组缓存key
	 */
	public static final String _group = "group";
	
	/**
	 * 日志记录器
	 */
	private Logger logger = GlobalLogger.init_global;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info(LogsUtil.LINE);
        logger.info(LogsUtil.PREFIX2 + "globalCache Load ...");
        for (CacheService cache : cacheList) {
        	logger.info(LogsUtil.PREFIX3 + cache.getCl() + " loading");
        	try {
				cache.init();
				logger.info(LogsUtil.PREFIX3 + cache.getCl() + " Loading is complete");
			} catch (DBException e) {
				logger.error(LogsUtil.PREFIX3 + cache.getCl() + "load error",e);
			}
		}
        logger.info(LogsUtil.PREFIX3 + "globalCache is complete");
	}
	
	public List<CacheService> getCacheList() {
		return cacheList;
	}

	public void setCacheList(List<CacheService> cacheList) {
		this.cacheList = cacheList;
	}
	
}
