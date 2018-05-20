package cn.dlbdata.dj.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.vo.LoginVo;

@Controller
@RequestMapping("/dj")
public class LoginController {
	
	@GetMapping("/login")
	public String login(String name)
	{
		return "hello";
	}
	
	@GetMapping("/login2")
	@ResponseBody
	public LoginVo login2(String name)
	{
		LoginVo result = new LoginVo();
		
		return result;
	}
}
