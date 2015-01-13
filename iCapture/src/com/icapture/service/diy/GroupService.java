package com.icapture.service.diy;

import java.util.List;

import com.connection.db.DBException;
import com.connection.page.Page;
import com.icapture.entity.diy.Group;
import com.icapture.web.action.CrudInterface;

/**
 * 分组数据库服务接口
 * 
 * @author huxiaohuan
 *
 */
public interface GroupService extends CrudInterface {
	
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws DBException
	 */
	Page<Group> queryByPage(Page<Group> page) throws DBException;
	
	/**
	 * 查询全部
	 * 
	 * @return
	 * @throws DBException
	 */
	List<Group> queryAll() throws DBException;
	
//	/**
//	 * 添加
//	 * 
//	 * @param group
//	 * @return
//	 * @throws DBException
//	 */
//	boolean add(Group group) throws DBException;
//	
//	/**
//	 * 修改
//	 * 
//	 * @param group
//	 * @return
//	 * @throws DBException
//	 */
//	boolean update(Group group) throws DBException;
//	
//	/**
//	 * 删除,删除掉该分组, 并将关联的common_page表的group_groupid重置为null
//	 * 
//	 * @param group
//	 * @return
//	 * @throws DBException
//	 */
//	boolean delete(Group group) throws DBException;
	
}
