package cn.dlbdata.dj.web.controller.api.v1;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.JwtTokenUtil;
import cn.dlbdata.dj.common.core.util.cache.CacheManager;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.DlbConstant.ResultCode;
import cn.dlbdata.dj.service.IUserService;
import cn.dlbdata.dj.vo.LoginVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * 处理用户登录的controller
 * 
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/api/v1")
public class LoginController extends BaseController {

	@Autowired
	private IUserService userService;

	/**
	 * 用户登录
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@PostMapping("/login")
	public ResultVo<UserVo> login(@RequestBody LoginVo vo) {
		ResultVo<UserVo> result = userService.login(vo);
		return result;
	}

	/**
	 * 获取当前登录用户信息
	 * 
	 * @return
	 */
	@GetMapping("/getCurrentUser")
	@ResponseBody
	public ResultVo<UserVo> getCurrentUser() {
		ResultVo<UserVo> result = new ResultVo<UserVo>();
		UserVo data = getCurrentUserFromCache();
		if (data != null) {
			result.setCode(ResultCode.OK.getCode());
			result.setData(data);
		}
		return result;
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 */
	@PostMapping("/logout")
	@ResponseBody
	public ResultVo<String> logout() {
		ResultVo<String> result = new ResultVo<>(ResultCode.OK.getCode());
		// 将缓存中token移除
		// 从header中获取token
		String token = getHeader("token");
		Map<String, String> tokenMap = JwtTokenUtil.getTokenInfo(token);
		if (tokenMap != null) {
			CacheManager.getInstance().remove(tokenMap.get(JwtTokenUtil.KEY_UID));
		}
		return result;
	}
	
	@ResponseBody
	@PostMapping("/updatePwd")
	public ResultVo<String> updatePwd(@RequestBody LoginVo vo) {
		ResultVo<String> result = userService.updatePwd(vo);
		return result;
	}
}
