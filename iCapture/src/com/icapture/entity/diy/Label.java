package com.icapture.entity.diy;

/**
 * 标签实体
 * 
 * @author huxiaohuan
 *
 */
public class Label {
	
	/**
	 * 实体对应数据库
	 */
	public static final String DB_NAME = "label";

	private Integer id;
	
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}