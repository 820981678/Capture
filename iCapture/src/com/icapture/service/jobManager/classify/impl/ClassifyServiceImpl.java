package com.icapture.service.jobManager.classify.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.entity.classify.CommonPage;
import com.icapture.entity.diy.WebSite;
import com.icapture.service.jobManager.classify.ClassifyService;

@Component
public class ClassifyServiceImpl implements ClassifyService {

	/**
	 * 查询全部任务分类
	 * 
	 * @return
	 */
	@Override
	public List<WebSite> queryAll() throws DBException {
		//SELECT COUNT(b.groupid),a.* from ( SELECT * from common_pages where to_days(item2) = to_days(now()) ) as b RIGHT JOIN topic_groups a ON b.groupid = a.id GROUP BY a.id;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(b.groupid) as todaycount,a.* from ( select * from ").append(CommonPage.DB_NAME);
		sql.append(" where to_days(item2)=to_days(now()) )as b");
		sql.append(" right join ").append(WebSite.DB_NAME).append(" as a");
		sql.append(" on b.groupid=a.id");
		sql.append(" group by a.id");
		
		List<WebSite> result = DBHandle.query(sql.toString(), new Object[0], WebSite.class);
		return result;
	}
	
	/**
	 * 查询全部网站的未读新闻条数
	 * 
	 * @return 
	 */
	public List<Map<String, Object>> queryUnread() throws DBException {
		String sql = "select count(b.groupid) as count,a.id,a.name from ( select * from common_pages where has_read=0 ) as b right join topic_groups a on b.groupid=a.id group by a.id";
		return DBHandle.query(sql, new Object[0]);
	}

}
