package com.icapture.service.diy;

import java.util.List;

import com.connection.db.DBException;
import com.connection.page.Page;
import com.icapture.entity.diy.WarnLevel;
import com.icapture.web.action.CrudInterface;

/**
 * 舆情级别数据库服务接口
 * 
 * @author huxiaohuan
 *
 */
public interface WarnLevelService extends CrudInterface {
	
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
	
}
