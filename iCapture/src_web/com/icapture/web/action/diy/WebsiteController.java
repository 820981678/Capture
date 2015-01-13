package com.icapture.web.action.diy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.page.Page;
import com.icapture.entity.diy.WebSite;
import com.icapture.entity.enu.WebsiteRate;
import com.icapture.service.diy.WebSiteService;
import com.icapture.web.action.CrudController;

/**
 * 门户网站配置控制器
 * 
 * @author hxh
 *
 */
@Controller
@RequestMapping("website")
public class WebsiteController extends CrudController {
	
	public WebsiteController() {
	    super.viewName = "/diy/website/index";
    }

	/**
	 * 数据库服务
	 */
	@Resource
	private WebSiteService webSiteService;
	
	/**
	 * 分页查询舆情级别
	 * 
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(Integer page,Integer rows,String sort,String order){
		if(page == null || rows == null){
			page = 1;
			rows = 20;
		}
		Page<WebSite> p = new Page<WebSite>(page, rows, sort, order);
		Map<String, Object> result = null;
		try {
			Page<WebSite> data = webSiteService.queryByPage(p);
			result =  pageToEasyUi(data,0);
		} catch (DBException e) {
			result =  pageToEasyUi(1);
		} finally {
			DBHandle.release();
		}
		return result;
	}
	
	/**
	 * 查询全部权重枚举
	 * 
	 * @return
	 */
	@RequestMapping("/queryWebSiteRate")
	@ResponseBody
	public Map<String, Object> queryWebSiteRate(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		List<Map<String, Object>> l = WebsiteRate.toMap();
		result.put("data", l);
		result.put("total", l.size());
		return result;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(WebSite website){
		return super._add(webSiteService,website);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(WebSite website){
		return super._update(webSiteService,website);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(WebSite website){
		return super._delete(webSiteService,website);
	}
	
}
