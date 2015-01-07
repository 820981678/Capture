package com.icapture.service.jobManager.classify.impl;

import java.util.ArrayList;
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
 * 网站所属文章数据库服务实现类
 * 
 * @author huxiahuan
 *
 */
@Component
public class CommonPageServiceImpl implements CommonPageService {

	/**
	 * 根据网站id分页查询新闻
	 * 
	 * @param grounpid 网站id
	 * @return
	 * @throws DBException
	 */
	@Override
	public Page<CommonPage> queryByPage(Page<CommonPage> page,Integer grounpid)
			throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.* from (select t1.*,t2.url as url from ");
		sql.append(CommonPage.DB_NAME).append(" as t1,");
		sql.append("topic_lists as t2 where t1.groupid=? and t1.topicid=t2.id order by id asc) as p order by p.has_read asc, p.id desc");
//		String sql = "select p.* from (select t1.*,t2.url as url from common_pages as t1,topic_lists as t2 where t1.groupid=? and t1.topicid=t2.id order by id asc) as p order by p.issee asc, p.id desc";
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
		sql.append("insert into ").append(Label.TO);
		sql.append(" values(?,?)");
		
		try{
			DBHandle.beginTransation();
			
			String delete = "delete from " + Label.TO + " where common_id=?";
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
		//String sql = "select p.* from ( select t1.*,t2.url as url from common_pages as t1,topic_lists as t2 where t1.topicid=t2.id and t1.id in( select common_id from commontolabel where label_id=? ) order by id asc ) as p order by p.id desc";
		StringBuffer sql = new StringBuffer();
		sql.append("select p.* from ( select t1.*,t2.url as url from ").append(CommonPage.DB_NAME).append(" as t1,");
		sql.append("topic_lists as t2 where t1.topicid=t2.id and t1.id in( select common_id from ");
		sql.append(Label.TO).append(" where label_id=? ) order by id asc ) as p order by p.id desc");
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
		sql.append("update ").append(CommonPage.DB_NAME).append(" set");
		sql.append(" catalog_id=?").append(" where id=?");
		Object[] params = {
				groupId,commId
		};
		
		return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
	}
	
	/**
	 * 分页查询  根据分组id查询
	 * 
	 * @param page
	 * @param group_groupId 分组id
	 * @return
	 * @throws DBException
	 */
	public Page<CommonPage> queryPageByGroup(Page<CommonPage> page,Integer group_groupId) throws DBException {
		String sql = "select p.* from ( select t1.*,t2.url as url from common_pages as t1,topic_lists as t2 where t1.topicid=t2.id and t1.group_groupid=? order by id asc ) as p order by p.id desc";
		Object[] params = { group_groupId};
		return DBHandle.query(sql.toString(), params, page, Base.Mysql);
	}
	
	/**
	 * 分页查询 根据标签,分组查询
	 * 
	 * @param page
	 * @param labelIds	标签的id数组
	 * @param groupId	分组的id
	 * @return
	 */
	public Page<CommonPage> queryPageByAll(Page<CommonPage> page,List<Integer> labelIds,Integer groupId) throws DBException {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select t.*,t1.url from (");
			sql.append(" select * from ").append(CommonPage.DB_NAME).append(" where 1=1");
			if(labelIds != null && labelIds.size() != 0){
				sql.append(" and id in (");
					sql.append(" select common_id from ").append(Label.TO);
					sql.append(" where label_id in (");
					for (int i : labelIds) {
						sql.append("?,");
						params.add(i);
					}
					sql.deleteCharAt(sql.length() - 1);
					sql.append(")");
					sql.append(" group by common_id having count(*) >= ").append(labelIds.size());
				sql.append(" )");
			}
			if(groupId != null){
				sql.append(" and group_groupid=?");
				params.add(groupId);
			}
		sql.append(" order by id desc ) as t, topic_lists as t1 where t.topicid=t1.id");
		
		return DBHandle.query(sql.toString(), params.toArray(), page, Base.Mysql);
	}
	
	/**
	 * 将未查看的新闻 改为已查看
	 * 
	 * @param id
	 * @return
	 */
	public boolean see(Integer id) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(CommonPage.DB_NAME);
		sql.append(" set has_read=?").append(" where id=?");
		
		Object[] params = {
			1,id	
		};
		
		return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
	}
	
}
