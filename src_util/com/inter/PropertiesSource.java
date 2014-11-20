package com.inter;

import java.util.Properties;

/**
 * DBhandle获取配置文件接口
 * 
 * @ClassName: DbProperties 
 * @author huxiaohuan 
 * @date 2014年9月28日 下午3:14:04 
 * @version V1.0
 */
public interface PropertiesSource {

	/**
	 * 获取dbhandle所需要的properties配置文件对象
	 * 
	 * @return
	 */
	Properties getProperties();
	
}
