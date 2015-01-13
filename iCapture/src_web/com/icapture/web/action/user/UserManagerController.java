package com.icapture.web.action.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.page.Page;
import com.icapture.entity.user.User;
import com.icapture.service.user.UserService;
import com.icapture.web.action.CrudController;

/**
 * 用户管理控制器
 * 
 * @author huxiaohuan
 *
 */
@Controller
@RequestMapping("admin/user")
public class UserManagerController extends CrudController {
	
	public UserManagerController(){
		super.viewName = "/user/manager/index";
	}
	
	/**
	 * 数据库服务
	 */
	@Resource
	private UserService userService;
	
	/**
	 * 分页查询全部账户
	 * 
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(Integer page,Integer rows,String sort,String order){
		if(page == null || rows == null){
			page = 1;
			rows = 20;
		}
		Page<User> p = new Page<User>(page, rows, sort, order);
		Map<String, Object> result = null;
		try {
			Page<User> data = userService.queryByPage(p);
			result =  pageToEasyUi(data,0);
		} catch (DBException e) {
			result =  pageToEasyUi(1);
		} finally {
			DBHandle.release();
		}
		return result;
	}
	
	/**
	 * 添加标签
	 * 
	 * @param label
	 * @return 成功返回code=0,其他code请见code码含义表
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(User user){
		return _add(userService,user);
	}
	
	/**
	 * 修改标签
	 * 
	 * @param label
	 * @return 成功返回code=0,其他code请见code码含义表
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(User user){
		return _update(userService,user);
	}
	
	/**
	 * 删除标签
	 * 
	 * @param label
	 * @return 成功返回code=0,其他code请见code码含义表
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(User user){
		return _delete(userService,user);
	}
	
	/**
	 * 分页查询，根据舆情级别id查询关联的全部用户
	 * 
	 * @param warnLevelId 舆情级别id
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	@RequestMapping("/queryByWarnLevelId")
	@ResponseBody
	public Map<String, Object> queryByWarnLevelId(Integer warnLevelId,Integer page,Integer rows,String sort,String order){
		if(page == null || rows == null){
			page = 1;
			rows = 20;
		}
		Page<User> p = new Page<User>(page, rows, sort, order);
		Map<String, Object> result = null;
		try {
			Page<User> data = userService.queryByWarnLevelId(p,warnLevelId);
			result =  pageToEasyUi(data,0);
		} catch (DBException e) {
			result =  pageToEasyUi(1);
		} finally {
			DBHandle.release();
		}
		return result;
	}
	
	/**
	 * 根据舆情级别id查询，不属于该舆情级别下的全部用户
	 * 
	 * @return
	 */
	@RequestMapping("/queryAddWarnByWarnLevelId")
	@ResponseBody
	public Map<String, Object> queryAddWarnByWarnLevelId(Integer warnLevelId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<User> l = userService.queryAddWarnByWarnLevelId(warnLevelId);
			map.put("code", 0);
			map.put("rows", l);
			map.put("total", l.size());
		} catch (DBException e) {
			map = mapError();
		} finally {
			DBHandle.release();
		}
		return map;
	}
	
}
