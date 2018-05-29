package cn.dlbdata.dj.web.controller.api.v1;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.pojo.DjScore;
import cn.dlbdata.dj.db.vo.party.ObserveLowPartyMemberVo;
import cn.dlbdata.dj.dto.active.ReportAddScoreRequest;
import cn.dlbdata.dj.service.IPartyMemberService;
import cn.dlbdata.dj.service.IUserService;
import cn.dlbdata.dj.vo.PartyVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.db.vo.party.PioneeringPartyMemberVo;
import cn.dlbdata.dj.db.vo.party.ReportPartyMemberVo;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * 处理党员相关的controller
 *
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/api/v1/party")
public class PartyMemberController extends BaseController {
	@Autowired
	private IPartyMemberService partyMemberService;
	@Autowired
	private IUserService userService;

	/**
	 * 获取党员参与活动次数
	 *
	 * @param memberId
	 * @return
	 */
	@GetMapping("/getScoreAndNum")
	@ResponseBody
	public ResultVo<PartyVo> getScoreAndNumByMemberId(Long memberId) {
		ResultVo<PartyVo> result = new ResultVo<>(ResultCode.OK.getCode());
		PartyVo data = partyMemberService.getScoreAndNumByMemberId(memberId);
		result.setData(data);
		return result;
	}

	/**
	 * 获取用户积分总数
	 *
	 * @param userId
	 * @param year
	 * @return
	 */
	@GetMapping("/getSumScoreByUserId")
	@ResponseBody
	public ResultVo<Float> getSumScoreByUserId(Long userId, Integer year) {
		ResultVo<Float> result = new ResultVo<>(ResultCode.OK.getCode());
		if (year == null) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		Float score = userService.getSumScoreByUserId(userId, year);
		if (score == null) {
			score = 0F;
		}
		result.setData(score);
		return result;
	}

	/**
	 * 获取积分明细
	 *
	 * @param userId
	 * @param year
	 * @return
	 */
	@GetMapping("/getScoreListByUserId")
	@ResponseBody
	public ResultVo<List<DjScore>> getScoreListByUserId(Long userId, Integer year) {
		ResultVo<List<DjScore>> result = new ResultVo<>(ResultCode.OK.getCode());
		if (year == null) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		List<DjScore> data = partyMemberService.getScoreListByUserId(userId, year);
		result.setData(data);
		return result;
	}

	/**
	 * 获取各个类型的积分总数及最大分数（雷达图、积分进度列表）
	 *
	 * @param userId
	 * @param year
	 * @return
	 */
	@GetMapping("/getTypeScoreListByUserId")
	@ResponseBody
	public ResultVo<List<DjScore>> getTypeScoreListByUserId(Long userId, Integer year) {
		ResultVo<List<DjScore>> result = new ResultVo<>(ResultCode.OK.getCode());
		if (year == null) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		List<DjScore> data = partyMemberService.getTypeScoreListByUserId(userId, year);
		result.setData(data);
		return result;
	}

	/**
	 *
	 * @param deptId
	 *            支部ID
	 * @param subTypeId
	 *            子活动ID 11："思想汇报自主汇报"，12："思想汇报书面汇报"
	 * @return 思想汇报评分查询党员列表
	 */
	@GetMapping("/getReportByDeptId")
	@ResponseBody
	public ResultVo<Paged<ReportPartyMemberVo>> getReportPartyMember(
												@RequestParam(value = "deptId") Long deptId,
												@RequestParam("subTypeId") Long subTypeId,
												@RequestParam(value = "pageNum", required = false) Integer pageNum,
												@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		pageNum = Paged.normalizePageIndex(pageNum);
		pageSize = Paged.normalizePageSize(pageSize);
		ResultVo<Paged<ReportPartyMemberVo>> result = new ResultVo<>(ResultCode.OK.getCode());
		Paged<ReportPartyMemberVo> voList = partyMemberService.getReportPartyMember(deptId, subTypeId,pageNum,pageSize);
		result.setData(voList);
		return result;
	}

	@PostMapping("/scoreCustom")
	@ResponseBody
	public ResultVo<String> reportAddScore(@RequestBody ReportAddScoreRequest request) {
		String token = getHeader("token");

		// 从缓存中获取当前用户的信息
		UserVo currUser = getCacheUserByToken(token);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		partyMemberService.reportAddScore(request, year, currUser);
		return new ResultVo<String>(CoreConst.ResultCode.OK.getCode(), "加分成功!");
	}

	/**
	 * 支书查询先锋评定党员列表
	 * 
	 * @param deptId
	 * @return
	 */
	@GetMapping("/getPartymembersByDeptIdForPioneering")
	@ResponseBody
	public ResultVo<Paged<PioneeringPartyMemberVo>> getPioneeringPartyMembers(
			@RequestParam(value = "deptId") Long deptId,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		pageNum = Paged.normalizePageIndex(pageNum);
		pageSize = Paged.normalizePageSize(pageSize);
		Paged<PioneeringPartyMemberVo> voList = partyMemberService.getPioneeringPartyMembers(deptId,pageNum,pageSize);
		ResultVo<Paged<PioneeringPartyMemberVo>> result = new ResultVo<>(ResultCode.OK.getCode());
		result.setData(voList);
		return result;
	}

	/**
	 * 
	 * <p>
	 * Title: getPartyMemberListByDeptId
	 * </p>
	 * <p>
	 * Description: 根据部门ID获取党员列表
	 * </p>
	 * 
	 * @return
	 */
	@GetMapping("/queryAllPartyMembersByDeptId")
	@ResponseBody
	public ResultVo<List<DjPartymember>> queryAllPartyMembersByDeptId() {
		ResultVo<List<DjPartymember>> result = new ResultVo<>();
		// UserVo data = getCurrentUserFromCache();
		List<DjPartymember> list = partyMemberService.queryAllPartyMembersByDeptId(0L);
		if (list == null || list.size() == 0) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("该党支部没有信息");
		} else {
			result.setCode(ResultCode.OK.getCode());
			result.setData(list);
		}
		return result;
	}

	/**
	 * 违章守纪评分党员列表
	 * 
	 * @param deptId
	 *            支部Id
	 * @return
	 */
	@GetMapping("/getDakDetialByDeptId")
	@ResponseBody
	public ResultVo<Paged<ObserveLowPartyMemberVo>> getObserveLowPartyMember(@RequestParam("deptId") Long deptId,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		pageNum = Paged.normalizePageIndex(pageNum);
		pageSize = Paged.normalizePageSize(pageSize);

		Paged<ObserveLowPartyMemberVo> paged = partyMemberService.getObserveLowPartyMember(deptId, pageNum, pageSize);
		ResultVo<Paged<ObserveLowPartyMemberVo>> result = new ResultVo<>(ResultCode.OK.getCode());
		result.setData(paged);
		return result;
	}

}
