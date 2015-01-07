package com.icapture.entity.diy;

/**
 * 舆情处理人实体
 * 
 * @author huxiaohuan
 *
 */
public class WarnUser {
	
	/**
	 * 对应数据库名称
	 */
	public static final String DB_NAME = "tbl_user_and_level";
	
	private Integer id;

	/**
	 * 处理人id
	 */
	private Integer user_id;
	
	/**
	 * 对应级别id
	 */
	private Integer warn_level_id;
	
	private Integer sortflag;
	
	private Integer status;
	
	/**
	 * 临时字段,保存查询结果中的处理人名称
	 */
	private String user_name;
	
	/**
	 * 临时字段,保存查询结果中的级别名称
	 */
	private String warn_name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getWarn_level_id() {
		return warn_level_id;
	}

	public void setWarn_level_id(Integer warn_level_id) {
		this.warn_level_id = warn_level_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getWarn_name() {
		return warn_name;
	}

	public void setWarn_name(String warn_name) {
		this.warn_name = warn_name;
	}

	public Integer getSortflag() {
		return sortflag;
	}

	public void setSortflag(Integer sortflag) {
		this.sortflag = sortflag;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
