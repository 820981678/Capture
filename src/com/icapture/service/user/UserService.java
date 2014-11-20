package com.icapture.service.user;

import com.connection.db.DBException;
import com.icapture.entity.user.User;

/**
 * 用户数据库服务接口
 * 
 * @author huxiaohuan
 *
 */
public interface UserService {

	/**
	 * 根据用户名，密码获取用户
	 * 
	 * @param user
	 * @return 于用户名，密码匹配的用户对象<br/>
	 * 		       没有匹配则返回null
	 * @throws DBException
	 */
	User query_login(User user) throws DBException;
	
}
