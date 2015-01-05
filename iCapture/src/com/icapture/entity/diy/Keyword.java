package com.icapture.entity.diy;

/**
 * 关键字实体
 * 
 * @author huxiaohuan
 *
 */
public class Keyword {
	
	/**
	 * 对应数据库
	 */
	public static final String DB_NAME = "tblkeyword";

	private Integer id;
	
	/**
	 * 关键字定义
	 */
	private String name;
	
	/**
	 * 是正面还是负面的，1 正面，2负面,0不定
	 */
	private Integer wtype;
	
	/**
	 * 关键字处理方式，0 是全词匹配，1是分隔后全部匹配，2是分隔后匹配任何一个
	 */
	private Integer stype;
	
	/**
	 * 创建的时间
	 */
	private String idate;
	
	/**
	 * 状态，1 是无效，0是有效
	 */
	private Integer status;
	
	/**
	 * 该关键字的默认分组
	 */
	private Integer catalog_id;
	
	/**
	 * 关键词权重
	 */
	private Double site_rate;
	

	/**
	 * 临时字段用于页面显示
	 * 表示该关键字默认分组的名称
	 */
	private String groupName;
	
	public Integer getCatalog_id() {
		return catalog_id;
	}
	
	public void setCatalog_id(Integer catalog_id) {
		this.catalog_id = catalog_id;
	}
	
	public Double getSite_rate() {
		return site_rate;
	}
	
	public void setSite_rate(Double site_rate) {
		this.site_rate = site_rate;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

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

	public Integer getWtype() {
		return wtype;
	}

	public void setWtype(Integer wtype) {
		this.wtype = wtype;
	}

	public Integer getStype() {
		return stype;
	}

	public void setStype(Integer stype) {
		this.stype = stype;
	}

	public String getIdate() {
		return idate;
	}

	public void setIdate(String idate) {
		this.idate = idate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
