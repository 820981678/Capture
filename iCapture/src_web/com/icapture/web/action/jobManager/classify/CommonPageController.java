package com.icapture.web.action.jobManager.classify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	@RequestMapping("/query")
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
	
	/**
	 * 打标签
	 * 
	 * @param id	文章id
	 * @param labels	所有标签id的集合
	 * @return
	 */
	@RequestMapping("/playLabel")
	@ResponseBody
	public Map<String, Object> playLabel(Integer id,String labels){
		Map<String, Object> map = new HashMap<String, Object>();
		String[] s = labels.split(",");
		List<Integer> list = new ArrayList<Integer>();
		for (String string : s) {
			list.add(Integer.valueOf(string));
		}
		
		try {
			commonPageService.playLabel(id, list);
			map.put("code", 0);
		} catch (DBException e) {
			map.put("code", 1);
			map.put("message", "服务器异常!");
		}
		return map;
	}
	
	/**
	 * 查询文章对应的标签
	 * 
	 * @param common_id 文章id
	 * @return
	 */
	public Map<String, Object> queryLabel(Integer common_id){
		
		return null;
	}
	
}
