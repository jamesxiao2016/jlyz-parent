/**
 *  <p>Title: AdminRoleController.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月13日 
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
import cn.dlbdata.dj.service.IRoleService;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * <p>Title: AdminRoleController</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月13日  
 */
@Controller
@RequestMapping("/admin/role")
public class AdminRoleController extends BaseController{
	
	@Autowired
	private IRoleService roleService;
	
	/**
	 * 查询列表
	 * 
	 * @return
	 */
	@RequestMapping("/list.html")
	public String list() {
		return "role/list.html";
	}

	/**
	 * 详情界面
	 * 
	 * @return
	 */
	@RequestMapping("/detail.html")
	public String detail() {
		return "role/detail.html";
	}

	/**
	 * 添加或修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/update.html")
	public ModelAndView update(Long id) {
		ModelAndView view = new ModelAndView("role/update.html");
		DjRole role = roleService.getRoleInfoById(id);
		view.addObject("role", role);
		return view;
	}
	
	/**
	 * 添加或修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/add.html")
	public String add() {
		return "role/add.html";
	}

	@RequestMapping("/save")
	@ResponseBody
	public ResultVo<Long> saveOrUpdate(DjRole djRole) {
		ResultVo<Long> result = new ResultVo<>();
		Long data = roleService.saveOrUpdate(djRole);
		if (data != null) {
			result.setCode(ResultCode.OK.getCode());
		}
		return result;
	}

	@RequestMapping("/deleteById")
	@ResponseBody
	public ResultVo<Long> deleteById(Long id) {
		ResultVo<Long> result = new ResultVo<>();
		Long data = roleService.deleteById(id);
		if (data != null) {
			result.setCode(ResultCode.OK.getCode());
		}
		return result;
	}
}
