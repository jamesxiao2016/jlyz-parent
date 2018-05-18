package cn.dlbdata.dangjian.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dangjian.common.core.web.vo.ResultVo;
import cn.dlbdata.dangjian.web.vo.LoginVo;

@Controller
@RequestMapping("/")
public class LoginController {
	
	@RequestMapping(value = "/login")
	@ResponseBody
	public ResultVo login(LoginVo loginVo)
	{
		ResultVo result = new ResultVo(200);
		
		return result;
	}
}
