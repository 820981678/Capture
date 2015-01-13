package com.icapture.web.action.diy;

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
import com.icapture.entity.diy.WarnLevel;
import com.icapture.service.diy.WarnLevelService;
import com.icapture.web.action.CrudController;

/**
 * 舆情级别配置控制器
 * 
 * @author huxiaohuan
 *
 */
@Controller
@RequestMapping("warnLevel")
public class WarnLevelController extends CrudController {
	
	public WarnLevelController(){
		super.viewName = "/diy/warnLevel/index";
	}
	
	/**
	 * 数据库服务
	 */
	@Resource
	private WarnLevelService warnLevelService;
	
	/**
	 * 分页查询舆情级别
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
		Page<WarnLevel> p = new Page<WarnLevel>(page, rows, sort, order);
		Map<String, Object> result = null;
		try {
			Page<WarnLevel> data = warnLevelService.queryByPage(p);
			result =  pageToEasyUi(data,0);
		} catch (DBException e) {
			result =  pageToEasyUi(1);
		} finally {
			DBHandle.release();
		}
		return result;
	}
	
	/**
	 * 查询全部
	 * 
	 * @return
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<WarnLevel> list = warnLevelService.queryAll();
			map.put("code", 0);
			map.put("data", list);
		} catch (DBException e) {
			map.put("code", 1);
		} finally {
			DBHandle.release();
		}
		return map;
	}
	
	
	/**
	 * 添加舆情级别
	 * 
	 * @param warn
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(WarnLevel warn){
		return _add(warnLevelService, warn);
	}
	
	/**
	 * 修改舆情级别
	 * 
	 * @param warn
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(WarnLevel warn){
		return _update(warnLevelService, warn);
	}
	
	/**
	 * 删除舆情级别
	 * 
	 * @param warn
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(WarnLevel warn){
		return _delete(warnLevelService, warn);
	}
}
