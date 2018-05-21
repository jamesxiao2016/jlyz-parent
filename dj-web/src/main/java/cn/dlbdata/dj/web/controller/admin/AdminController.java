package cn.dlbdata.dj.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.service.IUserServcie;
import cn.dlbdata.dj.vo.LoginVo;
import cn.dlbdata.dj.vo.UserVo;

/**
 * 处理后台管理员登录的登录
 * 
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private IUserServcie userService;
	
	@PostMapping("/login")
	@ResponseBody
	public ResultVo<UserVo> login(LoginVo vo) {
		ResultVo<UserVo> result = userService.login(vo);
		return result;
	}
}
