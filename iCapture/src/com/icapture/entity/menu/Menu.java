package com.icapture.entity.menu;

/**
 * 菜单实体
 * 
 * @author huxiaohuan
 *
 */
public class Menu {

	/**
	 * 菜单名称
	 */
	private String name;
	
	/**
	 * 菜单tab页显示名称
	 */
	private String tabName;
	
	/**
	 * 跳转url
	 */
	private String url;
	
	/**
	 * 权限等级
	 */
	private Integer jurisdiction;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(Integer jurisdiction) {
		this.jurisdiction = jurisdiction;
	}
	
}
