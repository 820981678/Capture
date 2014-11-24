package com.icapture.service.diy.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.db.DBHandle.Base;
import com.connection.page.Page;
import com.icapture.entity.classify.CommonPage;
import com.icapture.entity.diy.Group;
import com.icapture.service.diy.GroupService;

/**
 * 分组数据库服务实现
 * 
 * @author huxiaohuan
 *
 */
@Component
public class GroupServiceImpl implements GroupService {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws DBException
	 */
	@Override
	public Page<Group> queryByPage(Page<Group> page) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ").append(Group.DB_NAME).append(" WHERE 1=1 ");
		sql.append("ORDER BY ID DESC");
		
		return DBHandle.query(sql.toString(), new Object[0], page, Base.Mysql);
	}

	/**
	 * 查询全部
	 * 
	 * @return
	 * @throws DBException
	 */
	@Override
	public List<Group> queryAll() throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ").append(Group.DB_NAME);
		sql.append(" ORDER BY ID DESC");
		
		return DBHandle.query(sql.toString(), new Object[0], Group.class);
	}

	/**
	 * 添加
	 * 
	 * @param group
	 * @return
	 * @throws DBException
	 */
	@Override
	public boolean add(Group group) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ").append(Group.DB_NAME);
		sql.append(" (NAME) ");
		sql.append(" VALUES(?)");
		
		Object[] params = {
			group.getName()
		};
		
		return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
	}

	/**
	 * 修改
	 * 
	 * @param group
	 * @return
	 * @throws DBException
	 */
	@Override
	public boolean update(Group group) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ").append(Group.DB_NAME).append(" SET");
		sql.append(" NAME=?");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND ID=?");
		
		Object[] params = {
			group.getName(),group.getId()
		};
		
		return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
	}

	/**
	 * 删除,删除掉该分组, 并将关联的common_page表的group_groupid重置为null
	 * 
	 * @param group
	 * @return
	 * @throws DBException
	 */
	public boolean delete(Group group) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ").append(Group.DB_NAME).append(" WHERE 1=1");
		sql.append(" AND ID=?");
		Object[] params = { group.getId()};
		
		//将common_page表中的所有该分组的数据分组修改为null
		StringBuffer sql_common = new StringBuffer();
		sql_common.append("UPDATE ").append(CommonPage.DB_NAME).append(" SET");
		sql_common.append(" group_groupid=NULL").append(" WHERE group_groupid=?");
		Object[] params_common = { group.getId()};
		
		try {
			DBHandle.beginTransation();
			
			DBHandle.exceute(sql_common.toString(), params_common);
			DBHandle.exceute(sql.toString(), params);
			
			DBHandle.commit();
		} catch (DBException e) {
			DBHandle.rollback();
			throw e;
		}
		return true;
	}
	
}
