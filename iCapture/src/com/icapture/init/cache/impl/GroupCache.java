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
        
        String sql = "SELECT * FROM " + Group.DB_NAME;
        
		List<Group> result = DBHandle.query(sql, new Object[0], Group.class);
		
		GlobalCache.global.put(GlobalCache._group, result);
		
	}

}
