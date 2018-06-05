package cn.dlbdata.dj.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.dlbdata.dj.web.base.BaseController;

@Controller
@RequestMapping("/")
public class ViewController extends BaseController {

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/admin/index.html")
	public String index() {
		return "index.html";
	}
	
	/**
	 * 主页
	 * @return
	 */
	@RequestMapping("/admin/main.html")
	public String main() {
		return "main.html";
	}
}
