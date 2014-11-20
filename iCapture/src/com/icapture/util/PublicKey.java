package com.icapture.util;

/**
 * 记录公共使用的字符变量
 */
public class PublicKey {
	
	/**
	 * request中记录请求开始时间
	 */
	public static final String ACTION_STARTTIME = "startTime";
	
	/**
	 * request中记录请求结束时间
	 */
	public static final String ACTION_ENDTIME = "endTime";
	
	/**
	 * request中记录请求路径
	 */
	public static final String ACTION_PATH = "actionPath";
	
	/**
	 * 请求日志记录  入
	 */
	public static final String LOG_ACTION_IN = "<<<  ";
	
	/**
	 * 请求日志记录  出
	 */
	public static final String LOG_ACTION_OUT = ">>>  ";
	
	/**
	 * 日志开始
	 */
	public static final String LOG_TITLE = "|---------------------------------------------------------";
	
	/**
	 * 放行html请求 配置文件key
	 */
	public static final String DISCHARGED_HTML = "discharged.html";
	
	/**
	 * session中存放 登陆用户信息 key
	 */
	public static final String SESSION_USER_KEY = "user";
	
	/**
	 * cookie中存储 登陆后的信息的key
	 */
	public static final String COOKIE_USER_KEY = "user";
	
	/**
	 * 需要登陆验证的请求 在配置文件中key
	 */
	public static final String MUST_LOGIN_URL = "must.login.url";
	
	/**
	 * 不需要登录的url
	 */
	public static final String URL_LOGIN_NO = "url_login_no";
	
}
