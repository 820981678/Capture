package com.icapture.web.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.icapture.entity.user.User;
import com.icapture.init.properties.PropertiesConfigurer;
import com.icapture.util.GlobalLogger;
import com.icapture.util.PublicKey;
import com.util.LogsUtil;

public class LoginInterceptor extends HandlerInterceptorAdapter implements InitializingBean {
	
	/**
	 * 所有不需要登录操作的url
	 */
	private static Set<String> url_login_no = new HashSet<String>();
	
	private static Logger log = GlobalLogger.interceptor;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String servletPath = request.getServletPath();
		//放行不需要进行登录拦截
		if(!discharged(servletPath)){
			return true;
		}
		
		HttpSession session = request.getSession();
		Object u = session.getAttribute("user");
		
		if(u instanceof User){
			return true;
		}
		response.sendRedirect(getBaseUrl(request) + "login/toLogin");
		return false;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info(LogsUtil.LINE);
		log.info(LogsUtil.PREFIX2 + "LoginInterceptor init ...");
		
		//初始化要拦截的url集合
		String[] urls = PropertiesConfigurer.getStringValueByKey(PublicKey.URL_LOGIN_NO).split(",");
		for (String s : urls) {
			url_login_no.add(s);
		}
		log.info(LogsUtil.PREFIX3 + "value: " + url_login_no);
        log.info(LogsUtil.PREFIX3 + "LoginInterceptor init... is complete");
	}
	
	/**
	 * 判断是否需要拦截
	 * 
	 * @param requestPath
	 * @return 需要拦截返回 true
	 */
	private boolean discharged(String requestPath){
		if(url_login_no.contains(requestPath)){
			return false;
		}
		return true;
	}
	
	/**
	 * 获取请求的根路径
	 * 
	 * @param request
	 * @return
	 */
	private String getBaseUrl(HttpServletRequest request){
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		return basePath;
	}
	
}
