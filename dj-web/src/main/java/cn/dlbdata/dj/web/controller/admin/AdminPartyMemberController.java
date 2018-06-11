package cn.dlbdata.dj.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.dlbdata.dj.web.base.BaseController;

/**
 * 党员管理Controller
 * 
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/admin/partymember")
public class AdminPartyMemberController extends BaseController {
	/**
	 * 查询列表党员列表
	 * 
	 * @return
	 */
	@RequestMapping("partymember_list.html")
	public String list() {
		return "partymember/partymember_list.html";
	}

	/**
	 * 查询积分列表
	 *
	 * @return
	 */
	@RequestMapping("scorehistory_list.html")
	public String scorehistoryList() {
		return "partymember/scorehistory_list.html";
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
