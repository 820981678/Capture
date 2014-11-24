package com.icapture.service.jobManager.classify;

import java.util.List;

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
	
	/**
	 * 打标签
	 * 
	 * @param common_id	文章id
	 * @param labels	标签id集合
	 * @return
	 * @throws DBException
	 */
	boolean playLabel(Integer common_id, List<Integer> labels) throws DBException;
	
	/**
	 * 分页查询babelid标签下 全部commonpage
	 * 
	 * @param babelid
	 * @return
	 */
	Page<CommonPage> queryPageByLabel(Page<CommonPage> page,Integer labelid) throws DBException;
	
}
