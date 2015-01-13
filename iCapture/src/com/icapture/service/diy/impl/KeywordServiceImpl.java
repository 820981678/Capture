package com.icapture.service.diy.impl;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.db.DBHandle.Base;
import com.connection.page.Page;
import com.icapture.entity.diy.Group;
import com.icapture.entity.diy.Keyword;
import com.icapture.service.diy.KeywordService;
import com.icapture.web.action.CrudEntity;
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
		sql.append("select t.*,t1.name as groupname from ").append(Keyword.DB_NAME).append(" t");
		sql.append(" left join ").append(Group.DB_NAME).append(" t1");
		sql.append(" on t.catalog_id = t1.id ");
		sql.append(" order by t.id desc");
		return DBHandle.query(sql.toString(), new Object[0], Keyword.class, page, Base.Mysql);
	}
	
	/**
	 * 添加关键字
	 * 
	 * @param keyword
	 * @return
	 */
	@Override
	public boolean add(CrudEntity crud) throws DBException {
		if(!(crud instanceof Keyword)){
			throw new DBException();
		}
		Keyword keyword = (Keyword) crud;
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ").append(Keyword.DB_NAME);
		sql.append(" (name,wtype,stype,catalog_id,idate,status,site_rate) values(?,?,?,?,?,?,?)");
		Object[] params = {
			keyword.getName(),keyword.getWtype(),keyword.getStype(),keyword.getCatalog_id(),
			DateUtil.getDatetime(),keyword.getStatus(),keyword.getSite_rate()
		};
		return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
	}

	@Override
    public boolean update(CrudEntity crud) throws DBException {
	    // TODO Auto-generated method stub
	    return false;
    }

	@Override
    public boolean delete(CrudEntity crud) throws DBException {
	    // TODO Auto-generated method stub
	    return false;
    }

}
