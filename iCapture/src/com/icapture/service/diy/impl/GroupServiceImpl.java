package com.icapture.service.diy.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.db.DBHandle.Base;
import com.connection.page.Page;
import com.icapture.entity.classify.CommonPage;
import com.icapture.entity.diy.Group;
import com.icapture.service.diy.GroupService;
import com.icapture.web.action.CrudEntity;
import com.util.StringUtil;

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
	public Page<Group> queryByPage(Page<Group> page,Group select) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ").append(Group.DB_NAME).append(" WHERE 1=1 ");
		
		List<Object> params = new ArrayList<Object>();
		addSelect(sql, params, select);
		
		sql.append(" ORDER BY ID DESC");
		
		return DBHandle.query(sql.toString(), params.toArray(), page, Base.Mysql);
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
	public boolean add(CrudEntity crud) throws DBException {
		if(!(crud instanceof Group)){
			throw new DBException();
		}
		Group group = (Group) crud;
		
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
	public boolean update(CrudEntity crud) throws DBException {
		if(!(crud instanceof Group)){
			throw new DBException();
		}
		Group group = (Group) crud;
		
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
	@Override
	public boolean delete(CrudEntity crud) throws DBException {
		if(!(crud instanceof Group)){
			throw new DBException();
		}
		Group group = (Group) crud;
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ").append(Group.DB_NAME).append(" WHERE 1=1");
		sql.append(" AND ID=?");
		Object[] params = { group.getId()};
		
		//将common_page表中的所有该分组的数据分组修改为null
		StringBuffer sql_common = new StringBuffer();
		sql_common.append("UPDATE ").append(CommonPage.DB_NAME).append(" SET");
		sql_common.append(" catalog_id=NULL").append(" WHERE catalog_id=?");
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
	
	private void addSelect(StringBuffer sql,List<Object> params,Group select){
		if(select == null){
			return;
		}
		if(!StringUtil.isBlank(select.getName())){
			sql.append(" and name like ?");
			params.add("%" + select.getName() + "%");
		}
	}
	
}
