package com.icapture.web.action.diy.group;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 分组管理控制器
 * 
 * @author huxiaohuan
 *
 */
@RequestMapping("group")
@Controller
public class GroupController {

	/**
	 * 跳转到分组管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView model = new ModelAndView();
		
		model.setViewName("/diy/group/index");
		return model;
	}
	
}
