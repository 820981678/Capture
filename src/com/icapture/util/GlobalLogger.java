package com.icapture.util;

import org.apache.log4j.Logger;

public class GlobalLogger {
	
	public static Logger init_filter = Logger.getLogger("init.filter");
	
	public static Logger init_global = Logger.getLogger("init.global");
	
	public static Logger init_properties = Logger.getLogger("init.properties");
	
	public static Logger interceptor = Logger.getLogger("interceptor");
	
	public static Logger controller = Logger.getLogger("controller");
	
	public static Logger task_checknum = Logger.getLogger("task.checknum");
	
}
