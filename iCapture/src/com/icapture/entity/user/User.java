package com.icapture.entity.user;

/**
 * 用户实体类
 * 
 * @author huxiaohuan
 *
 */
public class User {
	
	/**
	 * 对应的数据库表名
	 */
	public static final String DB_NAME = "users";
	
	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 用户名
	 */
	private String name;
	
	/**
	 * 密码
	 */
	private String password;
	
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

	/**
	 * 获取用户密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置用户密码
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
