package cn.dlbdata.dj.web.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.service.IUserServcie;
import cn.dlbdata.dj.vo.LoginVo;
import cn.dlbdata.dj.vo.UserVo;

@Controller
@RequestMapping("/api/v1")
public class LoginController {
	
	@Autowired
	private IUserServcie userService;
	
	@GetMapping("/login")
	@ResponseBody
	public ResultVo<UserVo> login(LoginVo vo) {
		ResultVo<UserVo> result = userService.login(vo);
		return result;
	}
}
