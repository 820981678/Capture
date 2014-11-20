package com.icapture.service.jobManager.tblkey.impl;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.db.DBHandle.Base;
import com.connection.page.Page;
import com.icapture.entity.tblkey.TblKeyPages;
import com.icapture.service.jobManager.tblkey.TblKeyPagesService;

/**
 * 关键字 对应的页面信息数据库服务实现类
 * 
 * @author huxiaohuan
 *
 */
@Component("tblKeyPagesServiceImpl")
public class TblKeyPagesServiceImpl implements TblKeyPagesService {

	@Override
	public Page<TblKeyPages> queryByPage(Page<TblKeyPages> page, Integer id)
			throws DBException {
		String sql = "select p.* from (select t1.*,t2.topic_name,t3.title from tblkeypages t1,topic_groups t2,topic_lists t3 where t1.keyid=? and t1.groupid=t2.id and t1.topicid=t3.id and t3.groupid = t2.id order by id asc) as p order by p.id desc";
		
		Object[] params = {
			id
		};
		
		return DBHandle.query(sql.toString(), params, page, Base.Mysql);
	}
	
}
