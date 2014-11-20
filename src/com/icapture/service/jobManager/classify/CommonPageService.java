package com.icapture.service.jobManager.classify;

import com.connection.db.DBException;
import com.connection.page.Page;
import com.icapture.entity.classify.CommonPage;

/**
 * 分类下所属文章数据库服务
 * 
 * @author huxiahuan
 *
 */
public interface CommonPageService {
	
	/**
	 * 根据分页信息查询文章
	 * 
	 * @return
	 * @throws DBException
	 */
	Page<CommonPage> queryByPage(Page<CommonPage> page,Integer grounpid) throws DBException;
	
}
