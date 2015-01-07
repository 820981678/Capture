package com.icapture.service.diy;

import com.connection.db.DBException;
import com.connection.page.Page;
import com.icapture.entity.diy.WarnUser;

/**
 * 舆情处理人数据库服务接口
 * 
 * @author huxiaohuan
 *
 */
public interface WarnUserService {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws DBException
	 */
	Page<WarnUser> queryByPage(Page<WarnUser> page) throws DBException;
	
	/**
	 * 添加舆情处理人
	 * 
	 * @param warn
	 * @return
	 */
	boolean add(WarnUser warn) throws DBException;
	
	/**
	 * 修改舆情处理人
	 * 
	 * @param warn
	 * @return
	 */
	boolean update(WarnUser warn) throws DBException;
	
	/**
	 * 删除舆情处理人
	 * 
	 * @param warn
	 * @return
	 */
	boolean delete(WarnUser warn) throws DBException;
	
}
