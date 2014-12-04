package com.icapture.web.action.main;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.entity.diy.Group;
import com.icapture.entity.diy.Label;
import com.icapture.entity.user.User;
import com.icapture.init.cache.GlobalCache;
import com.icapture.init.configure.MenuConfig;
import com.icapture.service.user.UserService;
import com.icapture.util.PublicKey;

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
	 * 用户数据库服务
	 */
	@Resource
	private UserService userService;
	
	/**
	 * 进入主页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView main(HttpSession session){
		ModelAndView model = new ModelAndView();
		User user = (User) session.getAttribute(PublicKey.SESSION_USER_KEY);
		model.addObject("menus", MenuConfig.getMenu(user));
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
	 * 查询出所有的label group
	 * 
	 * @return
	 */
	@RequestMapping("/select")
	public ModelAndView select(){
		ModelAndView model = new ModelAndView();
		model.addObject("label", GlobalCache.getCache(GlobalCache.getLabel(), Label.class));
		model.addObject("group", GlobalCache.getCache(GlobalCache.getGroup(), Group.class));
		model.setViewName("select");
		return model;
	}
	
	/**
	 * 系统对接登录地址
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(User user,HttpSession session){
		try {
			User u = userService.query_login(user);
			if(u != null){
				//设置session
				session.setAttribute(PublicKey.SESSION_USER_KEY, user);
				return main(session);
			}
		} catch (DBException e) {
			
		} finally {
			DBHandle.release();
		}
		ModelAndView model = new ModelAndView();
		model.setViewName("/login/index");
		return model;
	}
	
}
