package com.icapture.service.diy.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.db.DBHandle.Base;
import com.connection.page.Page;
import com.icapture.entity.diy.WebSite;
import com.icapture.service.diy.WebSiteService;
import com.icapture.web.action.CrudEntity;
import com.util.StringUtil;

/**
 * 门户网站数据库服务实现
 * 
 * @author huxiaohuan
 *
 */
@Component
public class WebSiteServiceImpl implements WebSiteService {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws DBException
	 */
	@Override
    public Page<WebSite> queryByPage(Page<WebSite> page,WebSite select) throws DBException {
	    StringBuffer sql = new StringBuffer();
	    sql.append("select * from ").append(WebSite.DB_NAME).append(" where 1=1");
	    
	    List<Object> params = new ArrayList<Object>();
	    addSelect(sql, params, select);
	    
	    sql.append(" order by id");
	    return DBHandle.query(sql.toString(), params.toArray(), WebSite.class, page, Base.Mysql);
    }

	/**
	 * 添加门户网站
	 * 
	 * @param website
	 * @return
	 * @throws DBException
	 */
	@Override
    public boolean add(CrudEntity crud) throws DBException {
		WebSite website = (WebSite) crud;
	    StringBuffer sql = new StringBuffer();
	    sql.append("insert into ").append(WebSite.DB_NAME);
	    sql.append(" (name,topic_name,url,site_rate)").append(" values(?,?,?,?)");
	    Object[] params = new Object[]{
    		website.getName(),"not null",website.getUrl(),
    		website.getSite_rate()
	    };
	    return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
    }

	/**
	 * 修改门户网站
	 * 
	 * @param website
	 * @return
	 * @throws DBException
	 */
	@Override
    public boolean update(CrudEntity crud) throws DBException {
		WebSite website = (WebSite) crud;
	    StringBuffer sql = new StringBuffer();
	    sql.append("update ").append(WebSite.DB_NAME).append(" set");
	    sql.append(" name=?,url=?,site_rate=?");
	    sql.append(" where id=?");
	    Object params = new Object[]{
    		website.getName(),website.getUrl(),website.getSite_rate(),
    		website.getId()
	    };
	    return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
    }

	/**
	 * 删除门户网站
	 * 
	 * @param website
	 * @return
	 * @throws DBException
	 */
	@Override
    public boolean delete(CrudEntity crud) throws DBException {
		WebSite website = (WebSite) crud;
	    StringBuffer sql = new StringBuffer();
	    sql.append("delete from ").append(WebSite.DB_NAME);
	    sql.append(" where id=?");
	    Object[] params = new Object[]{
    		website.getId()
	    };
	    return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
    }
	
	private void addSelect(StringBuffer sql,List<Object> params,WebSite select){
		if(select == null){
			return;
		}
		if(!StringUtil.isBlank(select.getName())){
			sql.append(" and name like ?");
			params.add("%" + select.getName() + "%");
		}
		if(select.getSite_rate() != null && select.getSite_rate() != -1){
			sql.append(" and site_rate=?");
			params.add(select.getSite_rate());
		}
	}

}
