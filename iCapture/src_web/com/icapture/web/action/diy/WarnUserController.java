package com.icapture.web.action.diy;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.page.Page;
import com.icapture.entity.diy.WarnUser;
import com.icapture.service.diy.WarnUserService;
import com.icapture.web.action.BaseController;

/**
 * 舆情处理人配置控制器
 * 
 * @author huxiaohuan
 *
 */
@Controller
@RequestMapping("warnUser")
public class WarnUserController extends BaseController {
	
	/**
	 * 数据库服务
	 */
	@Resource
	private WarnUserService warnUserService;

	/**
	 * 跳转到舆情处理人配置页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView model = new ModelAndView();
		
		model.setViewName("/diy/warnUser/index");
		return model;
	}
	
	/**
	 * 分页查询
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
		Page<WarnUser> p = new Page<WarnUser>(page, rows, sort, order);
		Map<String, Object> result = null;
		try {
			Page<WarnUser> data = warnUserService.queryByPage(p);
			result =  pageToEasyUi(data,0);
		} catch (DBException e) {
			result =  pageToEasyUi(1);
		} finally {
			DBHandle.release();
		}
		return result;
	}
	
}
