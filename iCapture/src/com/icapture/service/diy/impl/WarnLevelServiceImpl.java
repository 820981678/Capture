package com.icapture.service.diy.impl;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.db.DBHandle.Base;
import com.connection.page.Page;
import com.icapture.entity.diy.WarnLevel;
import com.icapture.service.diy.WarnLevelService;

/**
 * 舆情级别数据库服务实现
 * 
 * @author huxiaohuan
 *
 */
@Component
public class WarnLevelServiceImpl implements WarnLevelService {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws DBException
	 */
	@Override
    public Page<WarnLevel> queryByPage(Page<WarnLevel> page) throws DBException {
	    StringBuffer sql = new StringBuffer();
	    sql.append("select * from ").append(WarnLevel.DB_NAME);
	    sql.append(" where 1=1 order by id");
	    return DBHandle.query(sql.toString(), new Object[0], page, Base.Mysql);
    }

	/**
	 * 添加舆情级别
	 * 
	 * @param warn
	 * @return
	 */
	@Override
    public boolean add(WarnLevel warn) throws DBException {
	    StringBuffer sql = new StringBuffer();
	    sql.append("insert into ").append(WarnLevel.DB_NAME);
	    sql.append(" (`name`,`min_rate`,`max_rate`,`description`,`send_msg`,`need_handle`,`msg_tpl`) ");
	    sql.append(" values(?,?,?,?,?,?,?)");
	    
	    Object[] params = new Object[]{
	    	warn.getName(),warn.getMin_rate(),warn.getMax_rate(),
	    	warn.getDescription(),warn.getSend_msg(),warn.getNeed_handle(),
	    	warn.getMsg_tpl()
	    };
	    
	    return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
    }

	/**
	 * 修改舆情级别
	 * 
	 * @param warn
	 * @return
	 */
	@Override
    public boolean update(WarnLevel warn) throws DBException {
	    StringBuffer sql = new StringBuffer();
	    sql.append("update ").append(WarnLevel.DB_NAME);
	    sql.append(" set name=?,min_rate=?,max_rate=?,description=?,send_msg=?,need_handle=?,msg_tpl=? ");
	    sql.append(" where id=?");
	    Object[] params = new Object[]{
	    	warn.getName(),warn.getMin_rate(),warn.getMax_rate(),
	    	warn.getDescription(),warn.getSend_msg(),warn.getNeed_handle(),warn.getMsg_tpl(),
	    	warn.getId()
	    };
	   
	    return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
    }

	/**
	 * 删除舆情级别
	 * 
	 * @param warn
	 * @return
	 */
	@Override
    public boolean delete(WarnLevel warn) throws DBException {
	    StringBuffer sql = new StringBuffer();
	    sql.append("delete from ").append(WarnLevel.DB_NAME);
	    sql.append(" where id=?");
	    Object[] params = new Object[]{
	    	warn.getId()
	    };
	    return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
    }

}
