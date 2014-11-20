package com.icapture.web.interceptor;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.icapture.util.GlobalLogger;
import com.icapture.util.PublicKey;
import com.util.StringUtil;

/**
 * 主要记录所有的请求信息
 */
public class HttpInterceptor implements HandlerInterceptor {
	
	private static Logger log = GlobalLogger.interceptor;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		
		//记录业务开始执行的时间
		long startTime = System.currentTimeMillis();
		request.setAttribute(PublicKey.ACTION_STARTTIME, startTime);
		
		//记录请求的路径
		String actionPath = request.getServletPath();
		request.setAttribute(PublicKey.ACTION_PATH, actionPath);
		if (log.isInfoEnabled()) {
			log.info(PublicKey.LOG_ACTION_IN + "actionPath: " + actionPath + ", at: " + startTime);
		}
		
		return true;
	}
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView modelAndView) throws Exception {
		String viewName;
        if (null != modelAndView && log.isDebugEnabled()) {
            Map<String, Object> map = modelAndView.getModel();
            for (Entry<String, Object> entry : map.entrySet()) {
                log.info(entry.getKey() + ":" + entry.getValue());
            }
            // 调试模型中所有的变量参等信息.
            if (StringUtil.hasText((viewName = modelAndView.getViewName()))) {
                log.info("render view by template:  " + viewName);
            }
        }
	}
	
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		//记录请求结束时间
		long endTime = System.currentTimeMillis();
		long startTime = (Long) request.getAttribute(PublicKey.ACTION_STARTTIME);
		long useTime = endTime - startTime;
		if (log.isInfoEnabled()) {
            log.info(PublicKey.LOG_ACTION_OUT + "at:" + endTime + ".ms, useTime:" + useTime + ".ms");
        }
	}
	
}
