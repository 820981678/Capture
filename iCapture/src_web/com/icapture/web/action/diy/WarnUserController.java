package com.icapture.web.action.diy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.entity.diy.WarnUser;
import com.icapture.service.diy.WarnUserService;
import com.icapture.web.action.BaseController;

/**
 * 舆情处理人配置控制器
 * 
 * @author huxiaohuan
 *
 */
@Lazy
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
	 * 添加舆情处理人
	 * 
	 * @param warn_level_id 舆情级别id
	 * @param user_ids 处理人id 拼接字符串
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(Integer warn_level_id,String user_ids){
		Map<String, Object> map;
		String[] users = user_ids.split(",");
		List<WarnUser> warnUserList = new ArrayList<WarnUser>();
		for (String id : users) {
			WarnUser u = new WarnUser();
			u.setUser_id(Integer.valueOf(id));
			u.setWarn_level_id(warn_level_id);
			warnUserList.add(u);
		}
		try {
			DBHandle.beginTransation();
			for (WarnUser warnuser : warnUserList) {
				warnUserService.add(warnuser);
			}
			DBHandle.commit();
			map = mapSuccess();
		} catch (DBException e) {
			map = mapError();
			try {
				DBHandle.rollback();
			} catch (DBException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBHandle.release();
		}
		return map;
	}
	
	/**
	 * 删除舆情处理人
	 * 
	 * @param warn
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(WarnUser warn){
		Map<String, Object> map = null;
		try {
			if(warnUserService.delete(warn)){
				map = mapSuccess();
			}
		} catch (DBException e) {
			map = mapError();
		} finally {
			DBHandle.release();
		}
		return map;
	}
	
}
