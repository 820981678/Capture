package com.icapture.service.jobManager.tblkey;

import java.util.List;

import com.connection.db.DBException;
import com.icapture.entity.diy.Keyword;

/**
 * 关键字数据库服务接口
 * 
 * @author huxiaohuan
 *
 */
public interface TblKeyService {

	/**
	 * 查询全部关键字
	 * 
	 * @return
	 * @throws DBException
	 */
	List<Keyword> queryAll() throws DBException;
	
}
