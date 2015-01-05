package com.icapture.entity.enu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关键字权重枚举
 * @author huxiaohuan
 *
 */
public enum SiteRate {

	FCMG(10,"非常敏感"),
	
	HMG(8,"很敏感"),
	
	YBGZ(6,"一般关注"),
	
	YB(4,"一般"),
	
	QT(0,"其它");
	
	private int key;
	
	private String name;
	
	/**
	* 构造方法
	*
	* @param key
	* @param name
	*/
	private SiteRate(int key,String name){
		this.key = key;
		this.name = name;
	}
	
	public Integer getKey(){
		return this.key;
	}
	
	public String getName(){
		return this.name;
	}
	
	/**
	* 返回所有的类型list
	*
	* @return
	*/
	public static List<SiteRate> toList(){
		List<SiteRate> list = new ArrayList<SiteRate>();
		SiteRate[] s = SiteRate.values();
		for (SiteRate sit : s) {
	                list.add(sit);
                }
		return list;
	}
	
	/**
	* 返回所有的类型map
	*
	* @return
	*/
	public static List<Map<String, Object>> toMap(){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (SiteRate _s : SiteRate.toList()) {
	                Map<String, Object> map = new HashMap<String, Object>();
	                map.put("key", _s.getKey());
	                map.put("name", _s.getName());
	                list.add(map);
                }
		return list;
	}
	
}
