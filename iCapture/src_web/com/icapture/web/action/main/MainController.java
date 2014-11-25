package com.icapture.web.action.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 主页面控制器
 * 
 * @author huxiaohuan
 *
 */
@Controller
@RequestMapping("main")
public class MainController {
	
	/**
	 * 进入主页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView main(){
		ModelAndView model = new ModelAndView();
		model.setViewName("main");
		return model;
	}
	
	/**
	 * 进入top页
	 * 
	 * @return
	 */
	@RequestMapping("/top")
	public ModelAndView top(){
		ModelAndView model = new ModelAndView();
		model.setViewName("top");
		return model;
	}
	
	/**
	 * 进入到select页
	 * 
	 * @return
	 */
	@RequestMapping("/select")
	public ModelAndView select(){
		ModelAndView model = new ModelAndView();
		model.setViewName("select");
		return model;
	}
	
}
