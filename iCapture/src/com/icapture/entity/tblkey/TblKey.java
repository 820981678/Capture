package com.icapture.entity.tblkey;

/**
 * 关键字实体
 * 
 * @author huxiaohuan
 *
 */
public class TblKey {
	
	public static final String DB_NAME = "tblkeyword";

	private Integer id;
	
	private String name;
	
	private Integer wtype;
	
	private Integer stype;
	
	/*
	 * 分类id
	 */
	private Integer catalog_id;
	
	private String idate;
	
	private Integer status;
	
	/*
	 * 关键词权重:10非常敏感,8很敏感,6一般关注,4一般,0其它
	 */
	private double site_rate;
	
	public Integer getCatalog_id() {
		return catalog_id;
	}

	public void setCatalog_id(Integer catalog_id) {
		this.catalog_id = catalog_id;
	}

	public double getSite_rate() {
		return site_rate;
	}

	public void setSite_rate(double site_rate) {
		this.site_rate = site_rate;
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
