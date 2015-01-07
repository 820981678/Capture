package com.icapture.service.diy.impl;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.db.DBHandle.Base;
import com.connection.page.Page;
import com.icapture.entity.diy.WarnLevel;
import com.icapture.entity.diy.WarnUser;
import com.icapture.entity.user.User;
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
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws DBException
	 */
	@Override
    public Page<WarnUser> queryByPage(Page<WarnUser> page) throws DBException {
	    StringBuffer sql = new StringBuffer();
	    sql.append("select t.*,t1.name as user_name,t2.name as warn_name from ").append(WarnUser.DB_NAME).append(" t");
	    sql.append(" LEFT JOIN ").append(User.DB_NAME).append(" t1 on t.user_id = t1.id");
	    sql.append(" LEFT JOIN ").append(WarnLevel.DB_NAME).append(" t2 on t.warn_level_id = t2.id");
	    return DBHandle.query(sql.toString(), new Object[0], page, Base.Mysql);
    }

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
	 * 修改舆情处理人
	 * 
	 * @param warn
	 * @return
	 */
	@Override
    public boolean update(WarnUser warn) throws DBException {
	    StringBuffer sql = new StringBuffer();
	    sql.append("update ").append(WarnUser.DB_NAME);
	    sql.append(" set user_id=?,warn_level_id=?");
	    sql.append(" where id=?");
	    Object[] params = new Object[]{
    		warn.getUser_id(),warn.getWarn_level_id(),
    		warn.getId()
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
	    sql.append(" and id=?");
	    Object[] params = new Object[]{
	    	warn.getId()
	    };
	    return DBHandle.exceute(sql.toString(), params) > 0 ? true : false; 
    }

}
