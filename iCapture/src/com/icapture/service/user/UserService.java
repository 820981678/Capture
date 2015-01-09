package com.icapture.service.user;

import com.connection.db.DBException;
import com.connection.page.Page;
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
	
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 * @throws DBException
	 */
	Page<User> queryByPage(Page<User> page) throws DBException;
	
	/**
	 * 添加一个用户
	 * 
	 * @param user
	 * @return
	 * @throws DBException
	 */
	boolean add(User user) throws DBException;
	
	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 * @throws DBException
	 */
	boolean update(User user) throws DBException;
	
	/**
	 * 删除用户
	 * 
	 * @param user
	 * @return
	 * @throws DBException
	 */
	boolean delete(User user) throws DBException;
	
}
