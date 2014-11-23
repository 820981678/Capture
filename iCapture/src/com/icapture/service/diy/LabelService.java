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
	 * 修改
	 * 
	 * @param label
	 * @return
	 * @throws DBException
	 */
	boolean update(Label label) throws DBException;
	
	/**
	 * 删除标签
	 * 级联删除common——label中的相关数据
	 * 
	 * @param label
	 * @return
	 */
	boolean delete(Label label) throws DBException;
	
	/**
	 * 根据commonPage的id查询出对应的标签集合
	 * 
	 * @param common_id
	 * @return
	 */
	List<Label> qyeryLabelByCommon(Integer common_id) throws DBException;
	
}
