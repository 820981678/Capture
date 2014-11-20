package com.icapture.service.jobManager.classify;

import java.util.List;

import com.connection.db.DBException;
import com.icapture.entity.classify.Classify;

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
	 List<Classify> queryAll() throws DBException;
	
}
