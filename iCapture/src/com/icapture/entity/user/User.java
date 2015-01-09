package com.icapture.entity.user;

import com.icapture.entity.menu.Jurisdiction;

/**
 * 用户实体类
 * 
 * @author huxiaohuan
 *
 */
public class User implements Jurisdiction {
	
	/**
	 * 对应的数据库表名
	 */
	public static final String DB_NAME = "tbl_user";
	
	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 用户名
	 */
	private String name;
	
	private String user_title;
	
	/**
	 * 密码
	 */
	private String user_password;
	
	private String phone;
	
	private String warn_phone;
	
	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 获取用户id
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置用户id
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取用户名
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置用户名
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUser_title() {
		return user_title;
	}

	public void setUser_title(String user_title) {
		this.user_title = user_title;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWarn_phone() {
		return warn_phone;
	}

	public void setWarn_phone(String warn_phone) {
		this.warn_phone = warn_phone;
	}

	@Override
	public Integer getJur() {
		return 0;
	}
	
}
