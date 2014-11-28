package com.icapture.web.action.jobManager.classify;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
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
import com.icapture.entity.classify.Classify;
import com.icapture.service.jobManager.classify.ClassifyService;

/**
 * 任务分类控制器
 * 
 * @author huxiaohuan
 *
 */
@RequestMapping("classify")
@Controller
public class ClassifyController {
	
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
			List<Classify> data = classifyService.queryAll();
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
	public void querySee(HttpServletResponse response){
		String s = "{\"see\":[{\"count\":7,\"id\":1},{\"count\":0,\"id\":3}]}";
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(s);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
