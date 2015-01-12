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
import com.icapture.entity.diy.Keyword;
import com.icapture.entity.enu.SiteRate;
import com.icapture.service.diy.KeywordService;
import com.icapture.web.action.CrudController;

/**
 * 关键字管理控制器
 * 
 * @author huxiaohuan
 *
 */
@Controller
@RequestMapping("keyword")
public class KeywordController extends CrudController {
	
	public KeywordController(){
		super.viewName = "/diy/keyword/index";
	}
	
	/**
	 * 数据库服务
	 */
	@Resource
	private KeywordService keywordService;

	/**
	 * 查询全部关键字 返回easyui json
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(Integer page,Integer rows,String sort,String order){
		if(page == null || rows == null){
			page = 1;
			rows = 20;
		}
		Page<Keyword> p = new Page<Keyword>(page, rows, sort, order);
		Map<String, Object> result = null;
		try {
			Page<Keyword> data = keywordService.queryPageAll(p);
			result =  pageToEasyUi(data,0);
		} catch (DBException e) {
			result =  pageToEasyUi(1);
		} finally {
			DBHandle.release();
		}
		return result;
	}
	
	/**
	 * 添加关键字
	 * 
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(Keyword keyword){
		return _add(keyword);
	}
	
	/**
	 * 查询全部权重枚举
	 * 
	 * @return
	 */
	@RequestMapping("/querySiteRate")
	@ResponseBody
	public Map<String, Object> querySiteRate(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", 0);
		List<Map<String, Object>> l = SiteRate.toMap();
		result.put("data", l);
		result.put("total", l.size());
		return result;
	}
	
}
