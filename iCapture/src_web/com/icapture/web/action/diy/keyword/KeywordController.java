package com.icapture.web.action.diy.keyword;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.page.Page;
import com.icapture.entity.diy.Keyword;
import com.icapture.service.diy.KeywordService;
import com.icapture.web.action.BaseController;

/**
 * 关键字管理控制器
 * 
 * @author huxiaohuan
 *
 */
@Controller
@RequestMapping("keyword")
public class KeywordController extends BaseController {
	
	/**
	 * 数据库服务
	 */
	@Resource
	private KeywordService keywordService;

	/**
	 * 跳转到关键字管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView model = new ModelAndView();
		
		model.setViewName("/diy/keyword/index");
		return model;
	}
	
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
	
}
