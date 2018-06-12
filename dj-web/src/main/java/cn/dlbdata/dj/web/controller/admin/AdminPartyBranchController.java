package cn.dlbdata.dj.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.dlbdata.dj.web.base.BaseController;

/**
 * 党支部管理Controller
 * 
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/admin/branch")
public class AdminPartyBranchController extends BaseController {
	/**
	 * 查询列表
	 * 
	 * @return
	 */
	@RequestMapping("/branchInfo.html")
	public String list() {
		return "/branch/branchInfo.html";
	}

	/**
	 * 详情界面
	 * 
	 * @return
	 */
	@RequestMapping("/detail.html")
	public String detail() {
		return "detail.html";
	}

	/**
	 * 添加或修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/add.html")
	public String add() {
		return "add.html";
	}
}
