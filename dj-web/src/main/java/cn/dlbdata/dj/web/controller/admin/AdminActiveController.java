/**
 *  <p>Title: AdminActiveController.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月11日 
 */
package cn.dlbdata.dj.web.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * <p>Title: AdminActiveController</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月11日  
 */
@Controller
@RequestMapping("/admin/active")
public class AdminActiveController  extends BaseController {
	@Autowired 
	private IActiveService activeService;
	

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	@RequestMapping("/list.html")
	public String list() {
		return "active/list.html";
	}

	/**
	 * 详情界面
	 * 
	 * @return
	 */
	@RequestMapping("/detail.html")
	public ModelAndView detail(Long id,Long roleId) {
		ModelAndView view = new ModelAndView("active/detail.html");
		ResultVo<Map<String, Object>> active = activeService.queryAdminActiveById(id,roleId);
		view.addObject("active", active.getData());
		return view;
	}

	/**
	 * 添加或修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/add.html")
	public String add() {
		return "active/add.html";
	}


	@RequestMapping("/deleteById")
	@ResponseBody
	public ResultVo<Long> deleteById(Long id) {
		ResultVo<Long> result = new ResultVo<>();
		Long data = activeService.deleteById(id);
		if (data != null) {
			result.setCode(ResultCode.OK.getCode());
		}
		return result;
	}
}
