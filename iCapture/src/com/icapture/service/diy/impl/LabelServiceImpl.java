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
 * 标签表数据库服务实现
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
		sql.append("select * from ").append(Label.DB_NAME).append(" where 1=1 ");
		sql.append("order by id desc");
		
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
		sql.append("select * from ").append(Label.DB_NAME);
		sql.append(" order by id desc");
		
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
		sql.append("insert into ").append(Label.DB_NAME);
		sql.append(" (name) ");
		sql.append(" values(?)");
		
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
		sql.append("update ").append(Label.DB_NAME).append(" set");
		sql.append(" name=?");
		sql.append(" where 1=1 ");
		sql.append(" and id=?");
		
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
		sql.append("delete from ").append(Label.DB_NAME).append(" where 1=1");
		sql.append(" and id=?");
		Object[] params = {label.getId()};
		
		StringBuffer sql_delete = new StringBuffer();
		sql_delete.append("delete from ").append(Label.TO).append(" where 1=1");
		sql_delete.append(" and label_id=?");
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
		sql.append("select * from ").append(Label.DB_NAME);
		sql.append(" where 1=1 ").append(" and id in (");
		sql.append("select label_id from ").append(Label.TO).append(" where common_id = ?)");
		
		Object[] params = {common_id};
		
		return DBHandle.query(sql.toString(), params, Label.class);
	}

}
