package com.icapture.service.diy;

import com.connection.db.DBException;
import com.connection.page.Page;
import com.icapture.entity.diy.Keyword;

/**
 * 关键字数据库服务接口
 * 
 * @author huxiaohuan
 *
 */
public interface KeywordService {
	
	/**
	 * 分页查询全部关键字
	 * 
	 * @return
	 * @throws DBException
	 */
	Page<Keyword> queryPageAll(Page<Keyword> page) throws DBException;
	
}
