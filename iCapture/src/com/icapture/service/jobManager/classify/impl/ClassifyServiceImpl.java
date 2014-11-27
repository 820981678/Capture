package com.icapture.service.jobManager.classify.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.entity.classify.Classify;
import com.icapture.entity.classify.CommonPage;
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
		//SELECT COUNT(b.groupid),a.* from ( SELECT * from common_pages where to_days(item2) = to_days(now()) ) as b RIGHT JOIN topic_groups a ON b.groupid = a.id GROUP BY a.id;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(B.GROUPID) AS TODAYCOUNT,A.* FROM ( SELECT * FROM ").append(CommonPage.DB_NAME);
		sql.append(" WHERE TO_DAYS(ITEM2)=TO_DAYS(NOW()) )AS B");
		sql.append(" RIGHT JOIN ").append(Classify.DB_NAME).append(" AS A");
		sql.append(" ON B.GROUPID=A.ID");
		sql.append(" GROUP BY A.ID");
		
		List<Classify> result = DBHandle.query(sql.toString(), new Object[0], Classify.class);
		return result;
	}

}
