package com.icapture.web.action.diy.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.page.Page;
import com.icapture.entity.diy.Group;
import com.icapture.entity.diy.Label;
import com.icapture.service.diy.GroupService;
import com.icapture.web.action.BaseController;

/**
 * 分组管理控制器
 * 
 * @author huxiaohuan
 *
 */
@RequestMapping("group")
@Controller
public class GroupController extends BaseController {
	
	/**
	 * 数据库服务
	 */
	@Resource
	private GroupService groupService;

	/**
	 * 跳转到分组管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView model = new ModelAndView();
		
		model.setViewName("/diy/group/index");
		return model;
	}
	
	/**
	 * 分页查询全部分组
	 * 
	 * @return 返回json用于页面easyui绘制
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(Integer page,Integer rows,String sort,String order) throws DBException {
		if(page == null || rows == null){
			page = 1;
			rows = 20;
		}
		Page<Group> p = new Page<Group>(page, rows, sort, order);
		Map<String, Object> result = null;
		try {
			Page<Group> data = groupService.queryByPage(p);
			result =  pageToEasyUi(data,0);
		} catch (DBException e) {
			result =  pageToEasyUi(1);
		} finally {
			DBHandle.release();
		}
		return result;
	}
	
	/**
	 * 查询全部分组
	 * 
	 * @return
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(){
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			List<Group> result = groupService.queryAll();
			map.put("code", 0);
			map.put("total", result.size());
			map.put("rows", result);
		} catch (DBException e) {
			map.put("code", 1);
			map.put("message", "服务器异常!");
		} finally {
			DBHandle.release();
		}
		return map;
	}
	
	/**
	 * 添加分组
	 * 
	 * @param label
	 * @return 成功返回code=0,其他code请见code码含义表
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(Group group){
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			if(groupService.add(group)){
				map.put("code", 0);
			}
		} catch (DBException e) {
			map.put("code", 1);
			map.put("message", "服务器异常!");
		} finally {
			DBHandle.release();
		}
		return map;
	}
	
	/**
	 * 修改分组
	 * 
	 * @param label
	 * @return 成功返回code=0,其他code请见code码含义表
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(Group group){
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			if(groupService.update(group)){
				map.put("code", 0);
			}
		} catch (DBException e) {
			map.put("code", 1);
			map.put("message", "服务器异常!");
		} finally {
			DBHandle.release();
		}
		return map;
	}
	
	/**
	 * 删除分组
	 * 
	 * @param label
	 * @return 成功返回code=0,其他code请见code码含义表
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(Label label){
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO 未实现
		map.put("code", 0);
		return map;
	}
	
}
