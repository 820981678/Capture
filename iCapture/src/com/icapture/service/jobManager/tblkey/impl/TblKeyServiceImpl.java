package com.icapture.service.jobManager.tblkey.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.entity.tblkey.TblKey;
import com.icapture.service.jobManager.tblkey.TblKeyService;

/**
 * 关键字数据库服务实现类
 * 
 * @author huxiaohuan
 *
 */
@Component
public class TblKeyServiceImpl implements TblKeyService {

	@Override
	public List<TblKey> queryAll() throws DBException {
		String sql = "SELECT * FROM " + TblKey.DB_NAME;
		
		List<TblKey> result = DBHandle.query(sql, new Object[0], TblKey.class);
		
		return result;
	}

	
}
