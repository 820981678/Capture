package com.icapture.service.diy.impl;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.db.DBHandle.Base;
import com.connection.page.Page;
import com.icapture.entity.diy.Group;
import com.icapture.entity.diy.Keyword;
import com.icapture.service.diy.KeywordService;
import com.util.DateUtil;

/**
 * 关键字数据库服务实现
 * 
 * @author huxiaohuan
 *
 */
@Component
public class KeywordServiceImpl implements KeywordService {

	/**
	 * 查询全部关键字
	 * 
	 * @return
	 * @throws DBException
	 */
	@Override
	public Page<Keyword> queryPageAll(Page<Keyword> page) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.*,T1.NAME AS groupName FROM ").append(Keyword.DB_NAME).append(" T");
		sql.append(" LEFT JOIN ").append(Group.DB_NAME).append(" T1");
		sql.append(" ON T.GROUPID = T1.ID ");
		sql.append(" ORDER BY T.ID DESC");
		return DBHandle.query(sql.toString(), new Object[0], Keyword.class, page, Base.Mysql);
	}
	
	/**
	 * 添加关键字
	 * 
	 * @param keyword
	 * @return
	 */
	@Override
	public boolean add(Keyword keyword) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ").append(Keyword.DB_NAME);
		sql.append(" (NAME,WTYPE,STYPE,IDATE,STATUS,GROUPID) VALUES(?,?,?,?,?,?)");
		Object[] params = {
			keyword.getName(),keyword.getWtype(),keyword.getStype(),
			DateUtil.getDatetime(),keyword.getStatus(),keyword.getGroupid()
		};
		return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
	}

}
