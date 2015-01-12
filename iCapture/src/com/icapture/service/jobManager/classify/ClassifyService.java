package com.icapture.service.jobManager.classify;

import java.util.List;
import java.util.Map;

import com.connection.db.DBException;
import com.icapture.entity.diy.WebSite;

/**
 * 任务分类服务接口
 * 
 * @author huxiaohuan
 *
 */
public interface ClassifyService {

	/**
	 * 查询全部任务分类
	 * 
	 * @return
	 */
	List<WebSite> queryAll() throws DBException;

	/**
	 * 查询全部网站的未读新闻条数
	 * 
	 * @return
	 */
	List<Map<String, Object>> queryUnread() throws DBException;

}
