package com.icapture.web.action.jobManager.tblkey;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.connection.db.DBException;
import com.connection.db.DBHandle;
import com.icapture.entity.diy.Keyword;
import com.icapture.service.jobManager.tblkey.TblKeyService;
import com.icapture.web.action.BaseController;

/**
 * 关键字控制器
 * 
 * @author huxiaohuan
 *
 */
@Controller
@RequestMapping("tblkey")
public class TblKeyController extends BaseController {
	
	@Resource
	private TblKeyService TblKeyService;
	
	/**
	 * 跳转到关键字页面，查询出全部关键字
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView model = new ModelAndView();
		
		try {
			List<Keyword> list = TblKeyService.queryAll();
			model.addObject("data", list);
		} catch (DBException e) {
			// TODO 完成异常处理
			e.printStackTrace();
		} finally {
			DBHandle.release();
		}
		
		model.setViewName("/jobManager/tblkey/index");
		return model;
	}
	
}
