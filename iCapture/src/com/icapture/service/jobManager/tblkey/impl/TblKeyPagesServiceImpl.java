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

	/**
	 * 根据分页信息查询
	 * 
	 * @param page	分页信息
	 * @param id	关键字id
	 * @return
	 * @throws DBException
	 */
	@Override
	public Page<TblKeyPages> queryByPage(Page<TblKeyPages> page, Integer id)
			throws DBException {
		String sql = "SELECT TT.*,MM.ID AS common_pages_id,MM.ITEM0 AS common_page_title,MM.GROUP_GROUPID AS group_groupid,MM.ISSEE AS isSee FROM (SELECT P.* FROM ( SELECT T1.*,T2.topic_name,T3.title,T3.url FROM TBLKEYPAGES T1,TOPIC_GROUPS T2,TOPIC_LISTS T3 WHERE T1.KEYID=? AND T1.GROUPID=T2.ID AND T1.TOPICID=T3.ID AND T3.GROUPID = T2.ID ORDER BY ID ASC ) AS P ORDER BY P.ID DESC) AS TT,COMMON_PAGES AS MM WHERE TT.GROUPID=MM.GROUPID AND TT.TOPICID=MM.TOPICID ORDER BY ISSEE ASC";
		
		Object[] params = {
			id
		};
		
		return DBHandle.query(sql.toString(), params, page, Base.Mysql);
	}
	
}
