package cn.dlbdata.dj.web.controller.admin;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.dlbdata.dj.web.base.BaseController;

@Controller
@RequestMapping("/")
public class AdminViewController extends BaseController {

	/**
	 * 登录页面
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/")
	public void welcome () throws IOException {
		response.sendRedirect("admin/login.html");
	}
	
	@RequestMapping("/login.html")
	public void login () throws IOException {
		response.sendRedirect("admin/login.html");
	}
	
	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping("/admin/login.html")
	public String adminlogin() {
		return "login.html";
	}

	/**
	 * 修改密码页面
	 * @return
	 */
	@RequestMapping("/admin/modifyPwd.html")
	public String modifyPwd() {
		return "modifyPwd.html";
	}
	
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
