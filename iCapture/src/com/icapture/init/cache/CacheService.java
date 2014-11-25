package com.icapture.init.cache;

import com.connection.db.DBException;

/**
 * 全局缓存加载器
 * 
 * @author huxiaohuan
 *
 */
public interface CacheService {
	
	void init() throws DBException;
	
	Class<?> getCl();
	
}
