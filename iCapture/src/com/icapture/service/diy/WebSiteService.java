package com.icapture.service.diy;

import com.connection.db.DBException;
import com.connection.page.Page;
import com.icapture.entity.diy.WebSite;
import com.icapture.web.action.CrudInterface;

/**
 * 门户网站数据库服务接口
 * 
 * @author hxh
 *
 */
public interface WebSiteService extends CrudInterface {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws DBException
	 */
	Page<WebSite> queryByPage(Page<WebSite> page,WebSite select) throws DBException;
	
}
