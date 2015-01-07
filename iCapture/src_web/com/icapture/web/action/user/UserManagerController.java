package com.icapture.web.action.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户管理控制器
 * 
 * @author huxiaohuan
 *
 */
@Controller
@RequestMapping("user")
public class UserManagerController {

	/**
	 * 跳转到用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView model = new ModelAndView();
		
		model.setViewName("/user/manager/index");
		return model;
	}
	
}
