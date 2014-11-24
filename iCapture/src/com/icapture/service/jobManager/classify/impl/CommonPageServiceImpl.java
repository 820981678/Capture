package com.icapture.service.jobManager.classify.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.db.DBHandle.Base;
import com.connection.page.Page;
import com.icapture.entity.classify.CommonPage;
import com.icapture.entity.diy.Label;
import com.icapture.service.jobManager.classify.CommonPageService;

/**
 * 分类下所属文章数据库服务实现类
 * 
 * @author huxiahuan
 *
 */
@Component
public class CommonPageServiceImpl implements CommonPageService {

	/**
	 * 根据分页信息查询文章
	 * 
	 * @return
	 * @throws DBException
	 */
	@Override
	public Page<CommonPage> queryByPage(Page<CommonPage> page,Integer grounpid)
			throws DBException {
		String sql = "select p.* from (select t1.*,t2.url as url from common_pages as t1,topic_lists as t2 where t1.groupid=? and t1.topicid=t2.id order by id asc) as p order by p.id desc";
		Object[] params = {
			grounpid	
		};
		return DBHandle.query(sql.toString(), params, page, Base.Mysql);
	}
	
	/**
	 * 打标签
	 * 
	 * @param common_id	文章id
	 * @param labels	标签id集合
	 * @return
	 * @throws DBException
	 */
	@Override
	public boolean playLabel(Integer common_id, List<Integer> labels)
			throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ").append(Label.TO);
		sql.append(" VALUES(?,?)");
		
		try{
			DBHandle.beginTransation();
			
			String delete = "DELETE FROM " + Label.TO + " WHERE COMMON_ID=?";
			Object[] pa = { common_id};
			DBHandle.exceute(delete,pa);
			
			for (int i = 0; i < labels.size(); i++) {
				Object[] params = {
					common_id,labels.get(i)
				};
				DBHandle.exceute(sql.toString(), params);
			}
			DBHandle.commit();
		} catch(DBException e){
			DBHandle.rollback();
			throw e;
		}
		
		return true;
	}
	
	/**
	 * 分页查询babelid标签下 全部commonpage
	 * 
	 * @param babelid
	 * @return
	 */
	@Override
	public Page<CommonPage> queryPageByLabel(Page<CommonPage> page,Integer labelid) throws DBException {
		String sql = "select p.* from ( select t1.*,t2.url as url from common_pages as t1,topic_lists as t2 where t1.topicid=t2.id and t1.id in( select common_id from commontolabel where label_id=? ) order by id asc ) as p order by p.id desc";
		Object[] params = {labelid};
		return DBHandle.query(sql.toString(), params, page, Base.Mysql);
	}
	
	/**
	 * 保存分组
	 * 
	 * @param commId common_page文章id
	 * @param groupId 分组id
	 * @return
	 */
	public boolean playGroup(Integer commId,Integer groupId) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ").append(CommonPage.DB_NAME).append(" SET");
		sql.append(" GROUP_GROUPID=?").append(" WHERE ID=?");
		Object[] params = {
				groupId,commId
		};
		
		return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
		
	}
	
}
