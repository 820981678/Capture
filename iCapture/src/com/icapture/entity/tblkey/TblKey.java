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
	
	private String idate;
	
	private Integer status;

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
