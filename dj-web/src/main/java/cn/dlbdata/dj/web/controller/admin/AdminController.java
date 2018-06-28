package cn.dlbdata.dj.web.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.db.vo.admin.AdminStatVo;
import cn.dlbdata.dj.db.vo.building.SelectBuildingVo;
import cn.dlbdata.dj.db.vo.dept.SelectTreeVo;
import cn.dlbdata.dj.service.IBuildingService;
import cn.dlbdata.dj.service.IDeptService;
import cn.dlbdata.dj.service.IDictService;
import cn.dlbdata.dj.service.ILogOptService;
import cn.dlbdata.dj.service.IPartyMemberService;
import cn.dlbdata.dj.service.IUserService;
import cn.dlbdata.dj.serviceimpl.PartyMemberService;
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
	@Autowired
	private IDeptService deptService;
	@Autowired
	private IBuildingService buildingService;
	@Autowired
	private ILogOptService logOptService;
	@Autowired
	private IPartyMemberService partyMemberService;

	/**
	 * 获取当前管理员信息
	 * @return
	 */
	@RequestMapping("/getAdminUserInfo")
	@ResponseBody
	public ResultVo<UserVo> getAdminUserInfo() {
		ResultVo<UserVo> result = new ResultVo<>();
		UserVo data = getCurrentAdminUserFromCache();
		result.setData(data);
		return result;
	}

	/**
	 * 修改密码
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	@RequestMapping("/modifyPwd")
	@ResponseBody
	public ResultVo<String> modifyPwd(String oldPwd, String newPwd) {
		UserVo user = getCurrentAdminUserFromCache();
		ResultVo<String> result = userService.modifyPwd(user, oldPwd, newPwd);
		return result;
	}

	/**
	 * 修改用户信息
	 * @param userName
	 * @param email
	 * @param telphone
	 * @return
	 */
	@RequestMapping("/modifyUser")
	@ResponseBody
	public ResultVo<String> modifyUser(String userName, String email, String telphone) {
		UserVo user = getCurrentAdminUserFromCache();
		ResultVo<String> result = userService.modifyUser(user, userName, email, telphone);
		return result;
	}

	/**
	 * 获取所有的字典信息
	 * @return
	 */
	@RequestMapping("/getAllDictList")
	@ResponseBody
	public ResultVo<List<SelectVo>> getAllDictList() {
		ResultVo<List<SelectVo>> result = new ResultVo<>();
		List<SelectVo> data = dictService.getAllDictList();
		result.setData(data);
		return result;
	}
	
	/**
	 * 根据字典类型获取字典列表
	 * @param dictType
	 * @return
	 */
	@RequestMapping("/getDictListByDictType")
	@ResponseBody
	public ResultVo<List<SelectVo>> getDictListByDictType(String dictType) {
		ResultVo<List<SelectVo>> result = new ResultVo<>();
		List<SelectVo> data = dictService.getDictListByDictType(dictType);
		result.setData(data);
		return result;
	}
	
	/**
	 * 获取片区及党支部数据（树形）
	 * @return
	 */
	@RequestMapping("/getSectionAndDeptTree")
	@ResponseBody
	public List<SelectTreeVo> getSectionAndDeptTree() {
		List<SelectTreeVo> data = deptService.getSectionAndDeptTree();
		return data;
	}

	/**
	 * 获取片区及楼宇数据（树形）
	 * @return
	 */
	@RequestMapping("/getSectionAndBuildingTree")
	@ResponseBody
	public List<SelectBuildingVo> getSectionAndBuildingTree() {
		List<SelectBuildingVo> data = buildingService.getSectionAndBuilding();
		return data;
	}
	
	/**
	 * 获取统计信息
	 * @param year
	 * @param month
	 * @return
	 */
	@GetMapping("/getAdminStat")
	@ResponseBody
	public ResultVo<AdminStatVo> getAdminStat(int year,int month) {
		ResultVo<AdminStatVo> result = new ResultVo<>();
		AdminStatVo adminStatVo = new AdminStatVo();
		adminStatVo = deptService.getAdminStat(year,month);
		if(adminStatVo == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("没有相关初始化数据");
			return result;
		}
		result.setCode(ResultCode.OK.getCode());
		result.setData(adminStatVo);
		return result;
	}
	
	/**
	 * 获取使用排名前5的党支部
	 * @param year
	 * @param month
	 * @return
	 */
	@GetMapping("/getTop5PartyBanch")
	@ResponseBody
	public ResultVo<Object> getTop5PartyBanch(Integer year,Integer month) {
		ResultVo<Object> result = new ResultVo<>();
		List<Map<String, Object>> list = logOptService.getTop5PartyBanch(year);
		if(list == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("没有相关数据");
			return result;
		}
		result.setCode(ResultCode.OK.getCode());
		result.setData(list);
		return result;
	}
	
	/**
	 * 获取积分最多的前5名
	 * @param year
	 * @param month
	 * @return
	 */
	@GetMapping("/getTop5Score")
	@ResponseBody
	public ResultVo<Object> getTop5Score(Integer year,Integer month) {
		ResultVo<Object> result = new ResultVo<>();
		List<Map<String, Object>> list = partyMemberService.getTop5Score(year);
		if(list == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("没有相关数据");
			return result;
		}
		result.setCode(ResultCode.OK.getCode());
		result.setData(list);
		return result;
	}
	
	/**
	 * 获取活动参与情况
	 * @param year
	 * @param month
	 * @return
	 */
	@GetMapping("/getActiveJoin")
	@ResponseBody
	public ResultVo<Object> getActiveJoin(Integer year,Integer month) {
		ResultVo<Object> result = new ResultVo<>();
		List<Map<String, Object>> list = logOptService.getActiveJoin(year,month);
		if(list == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("没有相关数据");
			return result;
		}
		result.setCode(ResultCode.OK.getCode());
		result.setData(list);
		return result;
	}
}
