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

	@Override
	public User query_login(User user) throws DBException {
		if(StringUtil.isEmpty(user.getName()) || StringUtil.isEmpty(user.getPassword())){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ").append(User.DB_NAME);
		sql.append(" WHERE NAME=? AND PASSWORD=?");
		
		Object[] params = {
			user.getName(),user.getPassword()
		};
		
		User result = DBHandle.queryFirst(sql.toString(), params, User.class);
		return result;
	}
	
}
