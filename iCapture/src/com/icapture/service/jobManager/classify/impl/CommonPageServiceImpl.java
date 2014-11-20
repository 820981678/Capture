package com.icapture.service.jobManager.classify.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.db.DBHandle.Base;
import com.connection.page.Page;
import com.icapture.entity.classify.CommonPage;
import com.icapture.service.jobManager.classify.CommonPageService;
import com.util.StringUtil;

/**
 * 分类下所属文章数据库服务实现类
 * 
 * @author huxiahuan
 *
 */
@Component
public class CommonPageServiceImpl implements CommonPageService {

	@Override
	public Page<CommonPage> queryByPage(Page<CommonPage> page,Integer grounpid)
			throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID,item0,item1,item2 FROM ").append(CommonPage.DB_NAME);
		sql.append(" WHERE 1=1 ");
		sql.append(" AND GROUPID=? ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(grounpid);
		
		if(!StringUtil.isEmpty(page.getSort()) && !StringUtil.isEmpty(page.getOrder())){
			sql.append(" ORDER BY ? ");
			params.add(page.getSort());
			if(page.getOrder().equals("desc")){
				sql.append("DESC");
			} else {
				sql.append("ASC");
			}
		}
		return DBHandle.query(sql.toString(), params.toArray(), page, Base.Mysql);
	}
	
}
