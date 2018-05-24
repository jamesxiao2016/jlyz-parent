package cn.dlbdata.dj.web.controller.api.v1;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.db.pojo.DjActiveUser;
import cn.dlbdata.dj.dto.ActiveSignUpRequest;
import cn.dlbdata.dj.dto.PartyMemberLifeNotice;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.service.IActiveUserService;
import cn.dlbdata.dj.vo.PageVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * 处理活动相关的controller
 * 
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/api/v1/active")
public class ActiveController extends BaseController {
	
	@Autowired
	private IActiveService activeService;
	@Autowired
	private IActiveUserService activeUserService;
	
	/**
	 * 党员生活通知接口
	 * <p>Title: getParticipateActive</p> 
	 * <p>Description: </p> 
	 * @param partyMemberLifeNotice
	 * @return
	 */
	@GetMapping(value = "/getParticipateActive")
	@ResponseBody
	public PageVo<List<Map<String, Object>>> getParticipateActive(PartyMemberLifeNotice partyMemberLifeNotice) {
		PageVo<List<Map<String, Object>>> result = new PageVo<>();
		UserVo data = getCurrentUserFromCache();
		partyMemberLifeNotice.setUserId(data.getUserId());
		partyMemberLifeNotice.setDepartmentId(data.getDeptId());
		result = activeService.getParticipateActive(partyMemberLifeNotice);
		if(result.getData() == null || result.getData().isEmpty())
		{
			result.setCode(ResultCode.Forbidden.getCode());
		}
		else
		{	
			result.setCode(ResultCode.OK.getCode());
		}
			return result;
	}
	/**
	 * 金领驿站活动报名
	 * <p>Title: participate</p> 
	 * <p>Description: </p> 
	 * @param activeId
	 * @return
	 */
	@PostMapping(value = "/participate")
	@ResponseBody
	public ResultVo<String> participate(@RequestBody ActiveSignUpRequest activeSignUpRequest) {
		ResultVo<String> result = new ResultVo<>();
		UserVo data = getCurrentUserFromCache();
		activeSignUpRequest.setUserId(data.getUserId());
		result = activeUserService.insertActiveSignUp(activeSignUpRequest);
		return result;
	}
	

	/**
	 * 
	 * <p>Title: getEnjoyActiveByUserId</p> 
	 * <p>Description:查看自己参加的活动 </p> 
	 * @param userId
	 * @param all
	 * @return
	 */
	@GetMapping(value = "/getEnjoyActiveByUserId")
	@ResponseBody
	public ResultVo<List<DjActive>> getEnjoyActiveByUserId() {
		ResultVo<List<DjActive>> result = new ResultVo<>();
		UserVo data = getCurrentUserFromCache();
		DjActiveUser djActiveUser = new DjActiveUser();
		djActiveUser.setStatus(1);
		List<DjActive> list = activeUserService.getMyJoinActive(data.getUserId(), djActiveUser.getStatus());
		if(list == null || list.size() ==0) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("数据为空");
			return result;
		}
		result.setCode(ResultCode.OK.getCode());
		result.setData(list);
		return result;
	}
	
	
	
	
}
