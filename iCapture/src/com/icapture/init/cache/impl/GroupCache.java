package com.icapture.init.cache.impl;

import java.util.List;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.entity.diy.Group;
import com.icapture.init.cache.GlobalCache;

/**
 * 分组缓存加载器
 * 
 * @author huxiaohuan
 *
 */
public class GroupCache extends CacheServiceBase {

	@Override
	public void init() throws DBException {
        
        String sql = "SELECT * FROM " + Group.DB_NAME + " ORDER BY ID DESC";
        
		List<Group> result = null;
		try {
			result = DBHandle.query(sql, new Object[0], Group.class);
		} catch (DBException e) {
			throw e;
		} finally {
			DBHandle.release();
		}
		
		GlobalCache.global.put(GlobalCache.getGroup(), result);
		
	}
	
}
