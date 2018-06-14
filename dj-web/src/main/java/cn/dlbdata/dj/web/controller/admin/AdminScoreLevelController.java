/**
 *  <p>Title: AdminScoreLevelController.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月12日 
 */
package cn.dlbdata.dj.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjRole;
import cn.dlbdata.dj.db.pojo.DjScoreLevel;
import cn.dlbdata.dj.service.IScoreLevelService;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * <p>Title: AdminScoreLevelController</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月12日  
 */
@Controller
@RequestMapping("/admin/scorelevel")
public class AdminScoreLevelController extends BaseController {
	
	@Autowired
	private IScoreLevelService scoreLevelService;

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	@RequestMapping("/list.html")
	public String list() {
		return "scorelevel/list.html";
	}

	/**
	 * 详情界面
	 * 
	 * @return
	 */
	@RequestMapping("/detail.html")
	public String detail() {
		return "scorelevel/detail.html";
	}

	/**
	 * 添加或修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/add.html")
	public String add() {
		return "scorelevel/add.html";
	}
	
	/**
	 * 添加或修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/update.html")
	public ModelAndView update(Long id) {
		ModelAndView view = new ModelAndView("scorelevel/update.html");
		DjScoreLevel scoreLevel = scoreLevelService.getScoreLevelInfoById(id);
		view.addObject("scoreLevel", scoreLevel);
		return view;
	}
	

	@RequestMapping("/save")
	@ResponseBody
	public ResultVo<Long> saveOrUpdate(DjScoreLevel djScoreLevel) {
		ResultVo<Long> result = new ResultVo<>();
		Long data = scoreLevelService.saveOrUpdate(djScoreLevel);
		if (data != null) {
			result.setCode(ResultCode.OK.getCode());
		}
		return result;
	}

	@RequestMapping("/deleteById")
	@ResponseBody
	public ResultVo<Long> deleteById(Long id) {
		ResultVo<Long> result = new ResultVo<>();
		Long data = scoreLevelService.deleteById(id);
		if (data != null) {
			result.setCode(ResultCode.OK.getCode());
		}
		return result;
	}
	
}
