package com.icapture.service.diy.impl;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.entity.diy.WarnUser;
import com.icapture.service.diy.WarnUserService;

/**
 * 舆情处理人数据库服务实现
 * 
 * @author huxiaohuan
 *
 */
@Component
public class WarnUserServiceImpl implements WarnUserService {

	/**
	 * 添加舆情处理人
	 * 
	 * @param warn
	 * @return
	 */
	@Override
    public boolean add(WarnUser warn) throws DBException {
	    StringBuffer sql = new StringBuffer();
	    sql.append("insert into ").append(WarnUser.DB_NAME);
	    sql.append(" (`user_id`,`warn_level_id`)");
	    sql.append(" values(?,?)");
	    Object[] params = new Object[]{
	    	warn.getUser_id(),warn.getWarn_level_id()
	    };
	    return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
    }

	/**
	 * 删除舆情处理人
	 * 
	 * @param warn
	 * @return
	 */
	@Override
    public boolean delete(WarnUser warn) throws DBException {
	    StringBuffer sql = new StringBuffer();
	    sql.append("delete from ").append(WarnUser.DB_NAME);
	    sql.append(" where 1=1");
	    sql.append(" and user_id=? and warn_level_id=?");
	    Object[] params = new Object[]{
	    	warn.getUser_id(),warn.getWarn_level_id()
	    };
	    return DBHandle.exceute(sql.toString(), params) > 0 ? true : false; 
    }

}
