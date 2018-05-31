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
import cn.dlbdata.dj.web.vo.TokenVo;

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
	public ResultVo<UserVo> getUserInfoById(Long userId, Integer isShowScore) {
		ResultVo<UserVo> result = new ResultVo<>(ResultCode.Forbidden.getCode());
		TokenVo vo = getTokenUserInfo();
		if (vo == null) {
			logger.error("用户未登录");
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		if (userId == null) {
			userId = vo.getUserId();
		}
		UserVo data = userService.getUserDetailById(userId, isShowScore);
		if (data != null) {
			result.setCode(ResultCode.OK.getCode());
			result.setData(data);
		}
		return result;
	}
}
