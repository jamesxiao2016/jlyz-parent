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
@RequestMapping("/admin/party")
public class PartyMemberController extends BaseController {
	/**
	 * 查询列表
	 * 
	 * @return
	 */
	@RequestMapping("/list.html")
	public String list() {
		return "list.html";
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
