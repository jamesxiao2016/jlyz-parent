package cn.dlbdata.dj.web.controller.api.v1;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.JwtTokenUtil;
import cn.dlbdata.dj.common.core.util.cache.CacheManager;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.util.security.MD5Util;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.common.util.StringUtil;
import cn.dlbdata.dj.db.pojo.DjLogLogin;
import cn.dlbdata.dj.service.ILogLoginService;
import cn.dlbdata.dj.service.IUserService;
import cn.dlbdata.dj.serviceimpl.LogLoginServiceImpl;
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
	@Autowired
	private ILogLoginService LogLoginService;

	/**
	 * 用户登录
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@PostMapping("/login")
	public ResultVo<UserVo> login(@RequestBody LoginVo vo) {
		long start = System.currentTimeMillis();
		vo.setPwd(StringUtil.getMD5Digest32(vo.getPwd()));
		ResultVo<UserVo> result = userService.login(vo);
		DjLogLogin djLogLogin = new DjLogLogin();
		if (result.getData() != null) {
			djLogLogin.setDjUserId(result.getData().getUserId());
			djLogLogin.setErrorMsg(result.getMsg());
			djLogLogin.setUserName(result.getData().getUserName());
			djLogLogin.setDjDeptId(result.getData().getDeptId());
			djLogLogin.setUserAccount(result.getData().getName());
			djLogLogin.setCreateTime(new Date());
			djLogLogin.setStatus(result.getCode());
		} else {
			djLogLogin.setErrorMsg(result.getMsg());
			djLogLogin.setUserAccount(vo.getName());
			djLogLogin.setCreateTime(new Date());
			djLogLogin.setStatus(result.getCode());
		}
		LogLoginService.insertLoginLogger(djLogLogin);
		logger.info("登录耗时->" + (System.currentTimeMillis() - start));
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
		} else {
			result.setCode(ResultCode.NOT_LOGIN.getCode());
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
		if (StringUtils.isNotEmpty(token)) {
			JwtTokenUtil.USER_TOKEN_CACHE.invalidate(MD5Util.encode(token));
		}
		return result;
	}

	@ResponseBody
	@PostMapping("/updatePwd")
	public ResultVo<String> updatePwd(@RequestBody LoginVo vo) {
		ResultVo<String> result = userService.updatePwd(vo);
		return result;
	}

	@ResponseBody
	@GetMapping("/getAllList")
	public ResultVo<String> getAllList() {
		userService.getALlUser();
		return new ResultVo<>(ResultCode.OK.getCode());
	}
}
