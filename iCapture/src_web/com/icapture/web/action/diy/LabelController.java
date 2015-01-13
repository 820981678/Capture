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
import com.icapture.entity.diy.Label;
import com.icapture.init.cache.GlobalCache;
import com.icapture.service.diy.LabelService;
import com.icapture.web.action.CrudController;

/**
 * 标签管理控制器
 * 
 * @author huxiaohuan
 *
 */
@RequestMapping("label")
@Controller
public class LabelController extends CrudController {
	
	public LabelController(){
		super.viewName = "/diy/label/index";
	}
	
	/**
	 * 数据库服务
	 */
	@Resource
	private LabelService labelService;

	/**
	 * 分页查询全部标签
	 * 
	 * @return 返回json用于页面easyui绘制
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(Integer page,Integer rows,String sort,String order,Label select){
		if(page == null || rows == null){
			page = 1;
			rows = 20;
		}
		Page<Label> p = new Page<Label>(page, rows, sort, order);
		Map<String, Object> result = null;
		try {
			Page<Label> data = labelService.queryByPage(p,select);
			result =  pageToEasyUi(data,0);
		} catch (DBException e) {
			result =  pageToEasyUi(1);
		} finally {
			DBHandle.release();
		}
		return result;
	}
	
	/**
	 * 查询全部标签
	 * 
	 * @return
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public Map<String, Object> queryAll(){
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Label> result = GlobalCache.getCache(GlobalCache.getLabel(), Label.class);
		map.put("code", 0);
		map.put("total", result.size());
		map.put("rows", result);
		/*
		try {
			List<Label> result = labelService.queryAll();
			
		} catch (DBException e) {
			map.put("code", 1);
			map.put("message", "服务器异常!");
		} finally {
			DBHandle.release();
		}
		*/
		return map;
	}
	
	/**
	 * 添加标签
	 * 
	 * @param label
	 * @return 成功返回code=0,其他code请见code码含义表
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(Label label){
		Map<String, Object> map = _add(labelService, label);
		if((Integer)map.get("code") == 0){
			GlobalCache.init(GlobalCache.getLabel());
		}
		return map;
	}
	
	/**
	 * 修改标签
	 * 
	 * @param label
	 * @return 成功返回code=0,其他code请见code码含义表
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(Label label){
		Map<String, Object> map = _update(labelService, label);
		if((Integer)map.get("code") == 0){
			GlobalCache.init(GlobalCache.getLabel());
		}
		return map;
	}
	
	/**
	 * 删除标签
	 * 
	 * @param label
	 * @return 成功返回code=0,其他code请见code码含义表
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(Label label){
		Map<String, Object> map = _delete(labelService, label);
		if((Integer)map.get("code") == 0){
			GlobalCache.init(GlobalCache.getLabel());
		}
		return map;
	}
	
	/**
	 * 根据common_id 查询出对应的标签
	 * 
	 * @param common_id
	 * @return
	 */
	@RequestMapping("/qyeryLabelByCommon")
	@ResponseBody
	public Map<String, Object> qyeryLabelByCommon(Integer common_id){
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			List<Label> list = labelService.qyeryLabelByCommon(common_id);
			map.put("code", 0);
			map.put("data", list);
		} catch (DBException e) {
			map.put("code", 1);
			map.put("message", "服务器异常!");
		} finally {
			DBHandle.release();
		}
		return map;
	}
	
}
