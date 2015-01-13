package com.icapture.web.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.init.cache.GlobalCache;

public class CrudController extends BaseController {
	
	/**
	 * 首页跳转视图
	 */
	protected String viewName;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView model =  new ModelAndView();
		model.setViewName(viewName);
		return model;
	}

	public Map<String, Object> _add(CrudInterface crud,CrudEntity obj){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(crud.add(obj)){
				map.put("code", 0);
			}
			GlobalCache.init(GlobalCache.getLabel());
		} catch (DBException e) {
			map.put("code", 1);
			map.put("message", "服务器异常!");
		} finally {
			DBHandle.release();
		}
		return map;
	}
	
	public Map<String, Object> _update(CrudInterface crud,CrudEntity obj){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(crud.update(obj)){
				map.put("code", 0);
			}
			GlobalCache.init(GlobalCache.getLabel());
		} catch (DBException e) {
			map.put("code", 1);
			map.put("message", "服务器异常!");
		} finally {
			DBHandle.release();
		}
		return map;
	}
	
	public Map<String, Object> _delete(CrudInterface crud,CrudEntity obj){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(crud.delete(obj)){
				map.put("code", 0);
			}
			GlobalCache.init(GlobalCache.getLabel());
		} catch (DBException e) {
			map.put("code", 1);
			map.put("message", "服务器异常!");
		} finally {
			DBHandle.release();
		}
		return map;
	}
	
}
