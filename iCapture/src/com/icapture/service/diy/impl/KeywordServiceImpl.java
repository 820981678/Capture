package com.icapture.service.diy.impl;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.db.DBHandle.Base;
import com.connection.page.Page;
import com.icapture.entity.diy.Keyword;
import com.icapture.service.diy.KeywordService;

/**
 * 关键字数据库服务实现
 * 
 * @author huxiaohuan
 *
 */
@Component
public class KeywordServiceImpl implements KeywordService {

	/**
	 * 查询全部关键字
	 * 
	 * @return
	 * @throws DBException
	 */
	@Override
	public Page<Keyword> queryPageAll(Page<Keyword> page) throws DBException {
		String sql = "SELECT * FROM " + Keyword.DB_NAME + " ORDER BY ID DESC";
		
		return DBHandle.query(sql, new Object[0], Keyword.class, page, Base.Mysql);
	}

}
