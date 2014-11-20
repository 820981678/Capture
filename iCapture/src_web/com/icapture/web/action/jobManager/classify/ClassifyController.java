package com.icapture.web.action.jobManager.classify;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 * 跳转到任务分类页面，查询出全部分类
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView query(){
		ModelAndView model = new ModelAndView();
		
		try {
			List<Classify> data = classifyService.queryAll();
			model.addObject("data", data);
		} catch (DBException e) {
			// TODO 完成异常处理
			e.printStackTrace();
		} finally {
			DBHandle.release();
		}
		
		model.setViewName("/jobManager/classify/index");
		return model;
	}
	
}
