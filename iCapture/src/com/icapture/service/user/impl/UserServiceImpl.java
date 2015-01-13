package com.icapture.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.db.DBHandle.Base;
import com.connection.page.Page;
import com.icapture.entity.diy.WarnLevel;
import com.icapture.entity.user.User;
import com.icapture.service.user.UserService;
import com.icapture.web.action.CrudEntity;
import com.util.StringUtil;

/**
 * 用户数据库服务实现类
 * 
 * @author huxiaohuan
 *
 */
@Component
public class UserServiceImpl implements UserService {

	/**
	 * 根据用户名，密码获取用户
	 * 
	 * @param user
	 * @return 于用户名，密码匹配的用户对象<br/>
	 * 		       没有匹配则返回null
	 * @throws DBException
	 */
	@Override
	public User query_login(User user) throws DBException {
		if(StringUtil.isEmpty(user.getName()) || StringUtil.isEmpty(user.getUser_password())){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(User.DB_NAME);
		sql.append(" where name=? and user_password=?");
		
		Object[] params = {
			user.getName(),user.getUser_password()
		};
		
		User result = DBHandle.queryFirst(sql.toString(), params, User.class);
		return result;
	}
	
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws DBException
	 */
	@Override
	public Page<User> queryByPage(Page<User> page,User select) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,name,user_title,phone,warn_phone,status from ").append(User.DB_NAME).append(" where 1=1");
		
		List<Object> params = new ArrayList<Object>();
		addSelect(sql, params, select);
		
		sql.append(" order by id desc");
		
		return DBHandle.query(sql.toString(), params.toArray(), page, Base.Mysql);
	}

	/**
	 * 添加一个用户
	 * 
	 * @param user
	 * @return
	 * @throws DBException
	 */
	@Override
	public boolean add(CrudEntity crud) throws DBException {
		if(!(crud instanceof User)){
			throw new DBException();
		}
		User user = (User) crud;
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ").append(User.DB_NAME);
		sql.append(" (name,user_title,user_password,phone,warn_phone,status)");
		sql.append(" values(?,?,?,?,?,?)");
		Object[] params = new Object[]{
			user.getName(),user.getUser_title(),user.getUser_password(),
			user.getPhone(),user.getWarn_phone(),user.getStatus()
		};
		return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 * @throws DBException
	 */
	@Override
	public boolean update(CrudEntity crud) throws DBException {
		if(!(crud instanceof User)){
			throw new DBException();
		}
		User user = (User) crud;
		
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(User.DB_NAME);
		sql.append(" set name=?,user_title=?,phone=?,warn_phone=?,status=?");
		sql.append(" where id=?");
		Object[] params = new Object[]{
			user.getName(),user.getUser_title(),
			user.getPhone(),user.getWarn_phone(),user.getStatus(),
			user.getId()
		};
		return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
	}

	/**
	 * 删除用户
	 * 
	 * @param user
	 * @return
	 * @throws DBException
	 */
	@Override
	public boolean delete(CrudEntity crud) throws DBException {
		if(!(crud instanceof User)){
			throw new DBException();
		}
		User user = (User) crud;
		
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ").append(User.DB_NAME);
		sql.append(" where 1=1 and id=?");
		Object[] params = new Object[]{
			user.getId()
		};
		return DBHandle.exceute(sql.toString(), params) > 0 ? true : false;
	}

	/**
	 * 根据舆情级别id查询对应的用户
	 * 
	 * @param warnLevelId
	 * @return
	 */
	@Override
	public Page<User> queryByWarnLevelId(Page<User> page,Integer warnLevelId)
			throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(User.DB_NAME).append(" where 1=1");
		sql.append(" and id in (");
		sql.append(" select user_id from ").append(WarnLevel.TO);
		sql.append("  where warn_level_id = ?)");
		Object[] params = new Object[]{
			warnLevelId
		};
		return DBHandle.query(sql.toString(), params, page, Base.Mysql);
	}

	/**
	 * 根据舆情级别id查询，不属于该舆情级别下的全部用户
	 * 
	 * @param warnLevelId
	 * @return
	 * @throws DBException
	 */
	@Override
	public List<User> queryAddWarnByWarnLevelId(Integer warnLevelId)
			throws DBException {
		//select * from tbl_user where id not in(select user_id from tbl_user_and_level where warn_level_id = 1)
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(User.DB_NAME);
		sql.append(" where id not in(");
		sql.append(" select user_id from ").append(WarnLevel.TO).append(" where warn_level_id = ?)");
		Object[] params = new Object[]{
			warnLevelId
		};
		return DBHandle.query(sql.toString(), params, User.class);
	}
	
	private void addSelect(StringBuffer sql,List<Object> params,User select){
		if(select == null){
			return;
		}
		if(!StringUtil.isBlank(select.getName())){
			sql.append(" and name like ?");
			params.add("%" + select.getName() + "%");
		}
		if(!StringUtil.isBlank(select.getUser_title())){
			sql.append(" and user_title like ?");
			params.add("%" + select.getUser_title() + "%");
		}
		if(select.getStatus() != null && select.getStatus() != -1){
			sql.append(" and status=? ");
			params.add(select.getStatus());
		}
	}

}
