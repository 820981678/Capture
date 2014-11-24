package com.icapture.service.diy.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.db.DBHandle.Base;
import com.connection.page.Page;
import com.icapture.entity.diy.Label;
import com.icapture.service.diy.LabelService;

/**
 * label表数据库服务实现
 * 
 * @author huxiaohuan
 *
 */
@Component
public class LabelServiceImpl implements LabelService {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws DBException
	 */
	@Override
	public Page<Label> queryByPage(Page<Label> page) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ").append(Label.DB_NAME).append(" WHERE 1=1 ");
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
	public List<Label> queryAll() throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ").append(Label.DB_NAME);
		sql.append(" ORDER BY ID DESC");
		
		return DBHandle.query(sql.toString(), new Object[0], Label.class);
	}

	/**
	 * 添加
	 * 
	 * @param label
	 * @return
	 * @throws DBException
	 */
	@Override
	public boolean add(Label label) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ").append(Label.DB_NAME);
		sql.append(" (NAME) ");
		sql.append(" VALUES(?)");
		
		Object[] params = {
			label.getName()
		};
		
		return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
	}

	/**
	 * 修改
	 * 
	 * @param label
	 * @return
	 * @throws DBException
	 */
	@Override
	public boolean update(Label label) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ").append(Label.DB_NAME).append(" SET");
		sql.append(" NAME=?");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND ID=?");
		
		Object[] params = {
			label.getName(),label.getId()
		};
		
		return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
	}
	
	/**
	 * 删除标签
	 * 级联删除common——label中的相关数据
	 * 
	 * @param label
	 * @return
	 */
	@Override
	public boolean delete(Label label) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ").append(Label.DB_NAME).append(" WHERE 1=1");
		sql.append(" AND ID=?");
		Object[] params = {label.getId()};
		
		StringBuffer sql_delete = new StringBuffer();
		sql_delete.append("DELETE FROM ").append(Label.TO).append(" WHERE 1=1");
		sql_delete.append(" AND LABEL_ID=?");
		Object[] params_delete = {label.getId()};
		
		try {
			DBHandle.beginTransation();
			
			DBHandle.exceute(sql_delete.toString(), params_delete);
			DBHandle.exceute(sql.toString(), params);
			
			DBHandle.commit();
		} catch (DBException e) {
			DBHandle.rollback();
			throw e;
		}
		
		return true;
	}
	
	/**
	 * 根据commonPage的id查询出对应的标签集合
	 * 
	 * @param common_id
	 * @return
	 */
	@Override
	public List<Label> qyeryLabelByCommon(Integer common_id) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ").append(Label.DB_NAME);
		sql.append(" WHERE 1=1 ").append(" AND ID IN (");
		sql.append("SELECT LABEL_ID FROM ").append(Label.TO).append(" WHERE COMMON_ID = ?)");
		
		Object[] params = {common_id};
		
		return DBHandle.query(sql.toString(), params, Label.class);
	}

}
