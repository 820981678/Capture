package com.icapture.service.diy;

import java.util.List;

import com.connection.db.DBException;
import com.connection.page.Page;
import com.icapture.entity.diy.Label;

/**
 * label标签表数据库服务接口
 * 
 * @author huxiaohuan
 *
 */
public interface LabelService {
	
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws DBException
	 */
	Page<Label> queryByPage(Page<Label> page) throws DBException;
	
	/**
	 * 查询全部
	 * 
	 * @return
	 * @throws DBException
	 */
	List<Label> queryAll() throws DBException;
	
	/**
	 * 添加
	 * 
	 * @param label
	 * @return
	 * @throws DBException
	 */
	boolean add(Label label) throws DBException;
	
	/**
	 * 删除
	 * 
	 * @param label
	 * @return
	 * @throws DBException
	 */
	boolean update(Label label) throws DBException;
	
}
