package cn.dlbdata.dj.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.service.IDictService;
import cn.dlbdata.dj.service.IUserService;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * 处理后台管理员登录的登录
 * 
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

	@Autowired
	private IDictService dictService;
	@Autowired
	private IUserService userService;

	@RequestMapping("/getAdminUserInfo")
	@ResponseBody
	public ResultVo<UserVo> getAdminUserInfo() {
		ResultVo<UserVo> result = new ResultVo<>();
		UserVo data = getCurrentAdminUserFromCache();
		result.setData(data);
		return result;
	}

	@RequestMapping("/modifyPwd")
	@ResponseBody
	public ResultVo<String> modifyPwd(String oldPwd, String newPwd) {
		UserVo user = getCurrentAdminUserFromCache();
		ResultVo<String> result = userService.modifyPwd(user, oldPwd, newPwd);
		return result;
	}

	@RequestMapping("/modifyUser")
	@ResponseBody
	public ResultVo<String> modifyUser(String userName, String email, String telphone) {
		UserVo user = getCurrentAdminUserFromCache();
		ResultVo<String> result = userService.modifyUser(user, userName, email, telphone);
		return result;
	}

	@RequestMapping("/getAllDictList")
	@ResponseBody
	public ResultVo<List<SelectVo>> getAllDictList() {
		ResultVo<List<SelectVo>> result = new ResultVo<>();
		List<SelectVo> data = dictService.getAllDictList();
		result.setData(data);
		return result;
	}

	@RequestMapping("/getDictListByDictType")
	@ResponseBody
	public ResultVo<List<SelectVo>> getDictListByDictType(String dictType) {
		ResultVo<List<SelectVo>> result = new ResultVo<>();
		List<SelectVo> data = dictService.getDictListByDictType(dictType);
		result.setData(data);
		return result;
	}
}
