package com.icapture.entity.menu;

import java.util.List;

/**
 * 菜单主项
 * 
 * @author xiaohuan
 *
 */
public class MenuManager {

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 包含菜单项
	 */
	private List<Menu> menuList;
	
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

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public Integer getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(Integer jurisdiction) {
		this.jurisdiction = jurisdiction;
	}
	
}
