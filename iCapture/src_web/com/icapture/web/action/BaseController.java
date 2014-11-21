package com.icapture.web.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.connection.page.Page;
import com.icapture.util.GlobalLogger;

/**
 * 控制器父累，提供控制器中通用方法
 * 
 * @author huxiaohuan
 *
 */
public class BaseController {
	
	/**
	 * 日志记录
	 */
	protected static Logger log = GlobalLogger.controller;

	/**
	 * 将系统中分页map转换为easyui所需要的格式map
	 * 
	 * @param map
	 * @return
	 */
	protected Map<String, Object> pageToEasyUi(Page<?> page,int code){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotal());
		map.put("rows", page.getRows());
		map.put("code", code);
		return map;
	}
	
	/**
	 * 将系统中分页map转换为easyui所需要的格式map
	 * 异常时返回code码
	 * 
	 * @param map
	 * @return
	 */
	protected Map<String, Object> pageToEasyUi(int code){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		return map;
	}
	
}
