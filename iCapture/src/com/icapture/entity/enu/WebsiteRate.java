package com.icapture.entity.enu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网站权重枚举
 * 
 * @author huxiaohuan
 *
 */
public enum WebsiteRate {

	QGMH(10,"全国门户"),
	
	QGZY(9,"全国专业网站"),
	
	DFMH(8,"地方门户"),
	
	DFZY(8,"地方专业网站"),
	
	QT(5,"其他");
	
	private Integer key;
	
	private String name;
	
	private WebsiteRate(Integer key,String name){
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
	public static List<WebsiteRate> toList(){
		List<WebsiteRate> list = new ArrayList<WebsiteRate>();
		WebsiteRate[] s = WebsiteRate.values();
		for (WebsiteRate sit : s) {
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
		for (WebsiteRate _s : WebsiteRate.toList()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("key", _s.getKey());
            map.put("name", _s.getName());
            list.add(map);
        }
		return list;
	}
}
