package com.icapture.web.action.login;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.entity.user.User;
import com.icapture.service.user.UserService;
import com.icapture.util.PublicKey;

/**
 * 用户登录控制器
 * 
 * @author huxiaohuan
 *
 */
@Controller
@RequestMapping("login")
public class LoginController {
	
	/**
	 * 用户数据库服务
	 */
	@Resource
	private UserService userService;
	
	/**
	 * 跳转到登录页面
	 * 
	 * @return
	 */
	@RequestMapping("/toLogin")
	public ModelAndView toLogin(){
		ModelAndView model = new ModelAndView();
		model.setViewName("/login/login");
		return model;
	}
	
	/**
	 * 执行登录操作
	 * 
	 * @return json中code值为0则登录成功，1表示用户名或密码错误
	 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public Map<String, Object> doLogin(User user,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			User u = userService.query_login(user);
			if(u != null){
				//设置session
				session.setAttribute(PublicKey.SESSION_USER_KEY, user);
				map.put("code", 0);
			}
		} catch (DBException e) {
			map.put("code", 1);
		} finally {
			DBHandle.release();
		}
		
		return map;
	}
	
}
