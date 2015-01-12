package com.icapture.web.action.diy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 门户网站配置控制器
 * 
 * @author hxh
 *
 */
@Controller
@RequestMapping("website")
public class WebsiteController {

	/**
	 * 跳转到网站配置页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView model =  new ModelAndView();
		model.setViewName("diy/website/index");
		return model;
	}
	
}
