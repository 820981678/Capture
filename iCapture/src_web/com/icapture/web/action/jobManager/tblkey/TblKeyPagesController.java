package com.icapture.web.action.jobManager.tblkey;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.connection.page.Page;
import com.icapture.entity.tblkey.TblKeyPages;
import com.icapture.service.jobManager.tblkey.TblKeyPagesService;
import com.icapture.web.action.BaseController;

/**
 * 关键字对应信息控制器
 * 
 * @author huxiaohuan
 *
 */
@Controller
@RequestMapping("tblkeypage")
public class TblKeyPagesController extends BaseController {
	
	@Resource
	private TblKeyPagesService tblKeyPagesServiceImpl;

	/**
	 * 跳转到页面
	 * 
	 * @param id 关键字id
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView index(Integer id){
		ModelAndView model = new ModelAndView();
		
		model.addObject("id", id);
		model.setViewName("jobManager/tblkeyPages/index");
		return model;
	}
	
	/**
	 * 分页方式查询
	 * 
	 * @param id 关键字id
	 * @return 返回json用于页面easyui绘制
	 */
	@RequestMapping("query")
	@ResponseBody
	public Map<String, Object> queryByPage(Integer id,Integer page,Integer rows,String sort,String order) throws DBException {
		if(page == null || rows == null){
			page = 1;
			rows = 20;
		}
		Page<TblKeyPages> p = new Page<TblKeyPages>(page, rows, sort, order);
		Map<String, Object> result = null;
		try {
			Page<TblKeyPages> data = tblKeyPagesServiceImpl.queryByPage(p, id);
			result = pageToEasyUi(data,0);
		} catch (DBException e) {
			result = pageToEasyUi(1);
		} finally {
			DBHandle.release();
		}
		return result;
	}
	
}
