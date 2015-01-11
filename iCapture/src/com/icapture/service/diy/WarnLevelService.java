package com.icapture.service.diy;

import java.util.List;

import com.connection.db.DBException;
import com.connection.page.Page;
import com.icapture.entity.diy.WarnLevel;

/**
 * 舆情级别数据库服务接口
 * 
 * @author huxiaohuan
 *
 */
public interface WarnLevelService {
	
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws DBException
	 */
	Page<WarnLevel> queryByPage(Page<WarnLevel> page) throws DBException;
	
	/**
	 * 查询全部
	 * 
	 * @return
	 * @throws DBException
	 */
	List<WarnLevel> queryAll() throws DBException;
	
	/**
	 * 添加舆情级别
	 * 
	 * @param warn
	 * @return
	 */
	boolean add(WarnLevel warn) throws DBException;
	
	/**
	 * 修改舆情级别
	 * 
	 * @param warn
	 * @return
	 */
	boolean update(WarnLevel warn) throws DBException;
	
	/**
	 * 删除舆情级别
	 * 
	 * @param warn
	 * @return
	 */
	boolean delete(WarnLevel warn) throws DBException;
	
}
