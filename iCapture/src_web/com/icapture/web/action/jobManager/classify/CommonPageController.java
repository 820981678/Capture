package com.icapture.web.action.jobManager.classify;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.page.Page;
import com.icapture.entity.classify.CommonPage;
import com.icapture.service.jobManager.classify.CommonPageService;
import com.icapture.web.action.BaseController;

/**
 * 分类下属文章控制器
 * 
 * @author huxiaohuan
 *
 */
@Controller
@RequestMapping("common")
public class CommonPageController extends BaseController {
	
	/**
	 * 数据库服务
	 */
	@Resource
	private CommonPageService commonPageService;

	/**
	 * 跳转到文章展示页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(Integer groupid){
		ModelAndView model = new ModelAndView();
		
		model.addObject("groupid", groupid);
		model.setViewName("jobManager/commonPage/index");
		return model;
	}
	
	/**
	 * 分页方式查询文章
	 * 
	 * @return 返回json用于页面easyui绘制
	 */
	@RequestMapping("query")
	@ResponseBody
	public Map<String, Object> queryByPage(Integer groupid,Integer page,Integer rows,String sort,String order) throws DBException {
		if(page == null || rows == null){
			page = 1;
			rows = 20;
		}
		Page<CommonPage> p = new Page<CommonPage>(page, rows, sort, order);
		Map<String, Object> result = null;
		try {
			Page<CommonPage> data = commonPageService.queryByPage(p, groupid);
			result = pageToEasyUi(data,0);
		} catch (DBException e) {
			result = pageToEasyUi(1);
		} finally {
			DBHandle.release();
		}
		return result;
	}
	
}
