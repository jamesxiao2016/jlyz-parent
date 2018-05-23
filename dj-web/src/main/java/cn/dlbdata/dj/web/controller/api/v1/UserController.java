package cn.dlbdata.dj.web.controller.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.service.IUserService;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.web.base.BaseController;

@Controller
@RequestMapping("/api/v1/user")
public class UserController extends BaseController {
	@Autowired
	private IUserService userService;

	/**
	 * 获取用户详细信息
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/getUserInfoById")
	@ResponseBody
	public ResultVo<UserVo> getUserInfoById(Long userId) {
		ResultVo<UserVo> result = new ResultVo<>(ResultCode.Forbidden.getCode());
		UserVo data = userService.getUserDetailById(userId);
		if (data != null) {
			result.setCode(ResultCode.OK.getCode());
		}
		return result;
	}
}
