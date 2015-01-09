package com.icapture.init.configure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.InitializingBean;

import com.icapture.entity.menu.Jurisdiction;
import com.icapture.entity.menu.Menu;
import com.icapture.entity.menu.MenuManager;
import com.util.FileUtil;

/**
 * 菜单项加载服务
 * 
 * @author huxiaohuan
 *
 */
public class MenuConfig extends TestCase implements InitializingBean {

	/**
	 * 配置文件路径
	 */
	private String configPath;
	
	/**
	 * 保存所有初始化的菜单项
	 */
	private static List<MenuManager> ml = new ArrayList<MenuManager>();

	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println(FileUtil.BASE_PATH);
		System.out.println(FileUtil.CLASS_PATH);
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new File(FileUtil.BASE_PATH + configPath));
		
		Element root = document.getRootElement();
		
		List<Element> menuM = root.elements("MenuManager");
		
		for (Element e : menuM) {
			MenuManager manager = new MenuManager();
			manager.setName(e.elementText("name"));
			manager.setJurisdiction(Integer.valueOf(e.elementText("jurisdiction")));
			
			List<Element> menus = e.elements("Menu");
			List<Menu> menuList = new ArrayList<Menu>();
			for (Element m : menus) {
				Menu menu = new Menu();
				menu.setName(m.elementText("name"));
				menu.setTabName(m.elementText("tabName"));
				menu.setUrl(m.elementText("url"));
				menu.setJurisdiction(Integer.valueOf(m.elementText("jurisdiction")));
				menuList.add(menu);
			}
			manager.setMenuList(menuList);
			ml.add(manager);
		}
		
	}
	
	/**
	 * 根据用户权限获取对应的菜单项
	 * 
	 * @param jur
	 * @return
	 */
	public static List<MenuManager> getMenu(Jurisdiction jur){
		return ml;
	}

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}
	
}
