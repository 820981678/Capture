package com.icapture.service.jobManager.tblkey;

import com.connection.db.DBException;
import com.connection.page.Page;
import com.icapture.entity.tblkey.TblKeyPages;

/**
 * 关键字 对应的页面信息数据库服务接口
 * 
 * @author huxiaohuan
 *
 */
public interface TblKeyPagesService {

	/**
	 * 根据分页信息查询
	 * 
	 * @param page	分页信息
	 * @param id	关键字id
	 * @return
	 * @throws DBException
	 */
	Page<TblKeyPages> queryByPage(Page<TblKeyPages> page,Integer id) throws DBException;
	
}
