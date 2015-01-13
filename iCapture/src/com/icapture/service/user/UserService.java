package com.icapture.service.user;

import java.util.List;

import com.connection.db.DBException;
import com.connection.page.Page;
import com.icapture.entity.user.User;
import com.icapture.web.action.CrudInterface;

/**
 * 用户数据库服务接口
 * 
 * @author huxiaohuan
 *
 */
public interface UserService extends CrudInterface {

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
	Page<User> queryByPage(Page<User> page,User select) throws DBException;
	
	/**
	 * 根据舆情级别id查询对应的用户
	 * 
	 * @param warnLevelId
	 * @return
	 */
	Page<User> queryByWarnLevelId(Page<User> page,Integer warnLevelId) throws DBException;
	
	/**
	 * 根据舆情级别id查询，不属于该舆情级别下的全部用户
	 * 
	 * @param warnLevelId
	 * @return
	 * @throws DBException
	 */
	List<User> queryAddWarnByWarnLevelId(Integer warnLevelId) throws DBException;
	
}
