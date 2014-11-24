package com.icapture.service.jobManager.classify.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.entity.classify.Classify;
import com.icapture.service.jobManager.classify.ClassifyService;

@Component
public class ClassifyServiceImpl implements ClassifyService {

	/**
	 * 查询全部任务分类
	 * 
	 * @return
	 */
	@Override
	public List<Classify> queryAll() throws DBException {
		String sql = "SELECT * FROM " + Classify.DB_NAME;
		
		List<Classify> result = DBHandle.query(sql, new Object[0], Classify.class);
		
		return result;
	}

}
