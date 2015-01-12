package com.icapture.service.jobManager.tblkey.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.entity.diy.Keyword;
import com.icapture.service.jobManager.tblkey.TblKeyService;

/**
 * 关键字数据库服务实现类
 * 
 * @author huxiaohuan
 *
 */
@Component
public class TblKeyServiceImpl implements TblKeyService {

	/**
	 * 查询全部关键字
	 * 
	 * @return
	 * @throws DBException
	 */
	@Override
	public List<Keyword> queryAll() throws DBException {
		String sql = "SELECT * FROM " + Keyword.DB_NAME;
		
		List<Keyword> result = DBHandle.query(sql, new Object[0], Keyword.class);
		
		return result;
	}

	
}
