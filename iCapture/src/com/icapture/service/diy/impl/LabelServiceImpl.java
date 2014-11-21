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

	@Override
	public Page<Label> queryByPage(Page<Label> page) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ").append(Label.DB_NAME).append(" WHERE 1=1 ");
		sql.append("ORDER BY ID DESC");
		
		return DBHandle.query(sql.toString(), new Object[0], page, Base.Mysql);
	}
	
	@Override
	public List<Label> queryAll() throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ").append(Label.DB_NAME);
		
		return DBHandle.query(sql.toString(), new Object[0], Label.class);
	}

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

	@Override
	public boolean update(Label label) throws DBException {
		return false;
	}
	
}
