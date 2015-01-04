package com.icapture.entity.diy;

/**
 * 用户自定义分组实体
 * 
 * @author huxiaohuan
 *
 */
public class Group {

	/**
	 * 对应数据库
	 */
	public static final String DB_NAME = "tbl_catalog";
	
	private Integer id;
	
	/**
	 * 分组名称
	 */
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
