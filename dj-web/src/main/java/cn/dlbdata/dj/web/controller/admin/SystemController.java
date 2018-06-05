package cn.dlbdata.dj.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.dlbdata.dj.web.base.BaseController;

/**
 * 系统管理Controller
 * 
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/admin/system")
public class SystemController extends BaseController {
	/**
	 * 角色查询列表
	 * 
	 * @return
	 */
	@RequestMapping("/role/list.html")
	public String list() {
		return "role/list.html";
	}

	/**
	 * 角色详情界面
	 * 
	 * @return
	 */
	@RequestMapping("/role/detail.html")
	public String detail() {
		return "role/detail.html";
	}

	/**
	 * 角色添加或修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/role/add.html")
	public String add() {
		return "role/add.html";
	}
}
