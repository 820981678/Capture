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
import com.util.StringUtil;

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
	 * @param url 用于加载页面datagrid数据的url,
	 * 			    页面会需要各种不同的查询方式去加载datagrid
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(String url){
		ModelAndView model = new ModelAndView();
		//这里对欢迎页面的url做处理
		if(url.indexOf("-") != -1){
			//替换掉url中的"-"为"&"
			url = url.replaceAll("-", "&");
		}
		model.addObject("url", url);
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
	 * 分页查询babelid标签下 全部commonpage
	 * 
	 * @param labelid 标签id
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	@RequestMapping("/queryPageByLabel")
	@ResponseBody
	public Map<String, Object> queryPageByLabel(Integer labelid,Integer page,Integer rows,String sort,String order){
		if(page == null || rows == null){
			page = 1;
			rows = 20;
		}
		Page<CommonPage> p = new Page<CommonPage>(page, rows, sort, order);
		Map<String, Object> result = null;
		try {
			Page<CommonPage> data = commonPageService.queryPageByLabel(p, labelid);
			result = pageToEasyUi(data,0);
		} catch (DBException e) {
			result = pageToEasyUi(1);
		} finally {
			DBHandle.release();
		}
		return result;
	}
	
	/**
	 * 保存分组
	 * 
	 * @param commId common_page文章id
	 * @param groupId 分组id
	 * @return
	 */
	@RequestMapping("/playGroup")
	@ResponseBody
	public Map<String, Object> playGroup(Integer commId,Integer groupId){
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			if(commonPageService.playGroup(commId, groupId)){
				map.put("code", 0);
			}
		} catch (DBException e) {
			map.put("code", 1);
			map.put("message", "服务器异常!");
		} finally {
			DBHandle.release();
		}
		
		return map;
	}
	
	/**
	 * 分页查询  根据分组id查询
	 * 
	 * @param group_groupId 分组id
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	@RequestMapping("/queryPageByGroup")
	@ResponseBody
	public Map<String, Object> queryPageByGroup(Integer group_groupId,Integer page,Integer rows,String sort,String order){
		if(page == null || rows == null){
			page = 1;
			rows = 20;
		}
		Page<CommonPage> p = new Page<CommonPage>(page, rows, sort, order);
		Map<String, Object> result = null;
		try {
			Page<CommonPage> data = commonPageService.queryPageByGroup(p, group_groupId);
			result = pageToEasyUi(data,0);
		} catch (DBException e) {
			result = pageToEasyUi(1);
		} finally {
			DBHandle.release();
		}
		return result;
	}
	
	/**
	 * 欢迎页的全局查询
	 * 
	 * @param label	要查询的标签id
	 * @param group	要查询的分组id
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	@RequestMapping("/queryPageByAll")
	@ResponseBody
	public Map<String, Object> queryPageByAll(String label,String group,Integer page,Integer rows,String sort,String order){
		if(page == null || rows == null){
			page = 1;
			rows = 20;
		}
		//将label,group 处理为需要的参数
		List<Integer> labelIds = new ArrayList<Integer>();
		String[] ls = label.split(",");
		for (String s : ls) {
			if(!StringUtil.isBlank(s)){
				labelIds.add(Integer.valueOf(s));
			}
		}
		Integer groupId = null;
		if(!StringUtil.isBlank(group)){
			groupId = Integer.valueOf(group);
		}
		Page<CommonPage> p = new Page<CommonPage>(page, rows, sort, order);
		Map<String, Object> result = null;
		try {
			Page<CommonPage> data = commonPageService.queryPageByAll(p, labelIds, groupId);
			result = pageToEasyUi(data,0);
		} catch (DBException e) {
			result = pageToEasyUi(1);
		} finally {
			DBHandle.release();
		}
		return result;
	}
	
	/**
	 * 已查看
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/see")
	@ResponseBody
	public Map<String, Object> see(Integer id){
		 Map<String, Object> map = new HashMap<String, Object>();
		 
		 try {
			if(commonPageService.see(id)){
				map.put("code", 0);
			}
		} catch (DBException e) {
			map.put("code", 1);
			map.put("message", "服务器异常");
		}
		 
		return map;
	}
	
}
