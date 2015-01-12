package com.icapture.web.action.jobManager.classify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.entity.diy.WebSite;
import com.icapture.service.jobManager.classify.ClassifyService;
import com.icapture.web.action.BaseController;

/**
 * 任务分类控制器
 * 
 * @author huxiaohuan
 *
 */
@RequestMapping("classify")
@Controller
public class ClassifyController extends BaseController {
	
	/**
	 * 数据库服务
	 */
	@Resource
	private ClassifyService classifyService;

	/**
	 * 跳转到任务分类页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView model = new ModelAndView();
		
		model.setViewName("/jobManager/classify/index");
		return model;
	}
	
	/**
	 * 加载全部classify
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<WebSite> data = classifyService.queryAll();
			map.put("code", 0);
			map.put("rows", data);
			map.put("total", data.size());
		} catch (DBException e) {
			map.put("code", 1);
			map.put("message", "服务器异常");
		} finally {
			DBHandle.release();
		}
		return map;
	}
	
	/**
	 * 查询全部网站未查看的新闻条数
	 * 
	 * @return
	 */
	@RequestMapping("/querySee")
	@ResponseBody
	public Map<String, Object> querySee(HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list;
		try {
			list = classifyService.queryUnread();
			map.put("code", 0);
			map.put("see", list);
		} catch (DBException e) {
			return mapError();
		}
		
		return map;
	}
	
}
