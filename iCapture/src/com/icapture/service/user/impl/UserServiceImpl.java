package com.icapture.service.user.impl;

import org.springframework.stereotype.Component;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.entity.user.User;
import com.icapture.service.user.UserService;
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
		if(StringUtil.isEmpty(user.getName()) || StringUtil.isEmpty(user.getPassword())){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(User.DB_NAME);
		sql.append(" where name=? and user_password=?");
		
		Object[] params = {
			user.getName(),user.getPassword()
		};
		
		User result = DBHandle.queryFirst(sql.toString(), params, User.class);
		return result;
	}
	
}
