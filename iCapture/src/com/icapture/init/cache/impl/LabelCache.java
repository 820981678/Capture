package com.icapture.init.cache.impl;

import java.util.List;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.entity.diy.Label;
import com.icapture.init.cache.GlobalCache;

/**
 * 标签缓存加载器
 * 
 * @author huxiaohuan
 *
 */
public class LabelCache extends CacheServiceBase {

	@Override
	public void init() throws DBException {
        
        String sql = "SELECT * FROM " + Label.DB_NAME + " ORDER BY ID DESC";
        
		List<Label> result = DBHandle.query(sql, new Object[0], Label.class);
		
		GlobalCache.global.put(GlobalCache.getLabel(), result);
	}

}
