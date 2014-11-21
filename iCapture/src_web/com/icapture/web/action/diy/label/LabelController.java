package com.icapture.web.action.diy.label;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 标签管理控制器
 * 
 * @author huxiaohuan
 *
 */
@RequestMapping("label")
@Controller
public class LabelController {

	/**
	 * 跳转到标签管理页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView model = new ModelAndView();
		
		model.setViewName("/diy/label/index");
		return model;
	}
	
}
