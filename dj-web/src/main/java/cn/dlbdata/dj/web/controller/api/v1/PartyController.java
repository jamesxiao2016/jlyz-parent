package cn.dlbdata.dj.web.controller.api.v1;

import java.util.Calendar;
import java.util.List;

import cn.dlbdata.dj.db.vo.party.*;
import cn.dlbdata.dj.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.PageUtils;
import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.vo.apply.ScoreTypeVo;
import cn.dlbdata.dj.db.vo.score.ScoreVo;
import cn.dlbdata.dj.service.IPartyMemberService;
import cn.dlbdata.dj.service.IUserService;
import cn.dlbdata.dj.vo.PartyVo;
import cn.dlbdata.dj.web.base.BaseController;
import cn.dlbdata.dj.web.vo.TokenVo;

/**
 * 处理党员相关的controller
 *
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/api/v1/party")
public class PartyController extends BaseController {
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
	@GetMapping("/getActiveNumByUserId")
	@ResponseBody
	public ResultVo<PartyVo> getActiveNumByUserId(Long userId, Integer year) {
		ResultVo<PartyVo> result = new ResultVo<>(ResultCode.OK.getCode());
		TokenVo tokenVo = getTokenUserInfo();
		if (tokenVo == null) {
			logger.error("用户未登录");
			result.setCode(ResultCode.NOT_LOGIN.getCode());
			result.setMsg("用户未报名或登录已过期");
			return result;
		}
		if (userId == null) {
			userId = tokenVo.getUserId();
		}
		if (year == null) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		PartyVo data = partyMemberService.getScoreAndNumByMemberId(userId, year);
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
		TokenVo vo = getTokenUserInfo();
		if (vo == null) {
			logger.error("用户未登录");
			result.setCode(ResultCode.NOT_LOGIN.getCode());
			return result;
		}
		if (userId == null) {
			userId = vo.getUserId();
		}
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
	public ResultVo<List<ScoreVo>> getScoreListByUserId(Long userId, Integer year) {
		ResultVo<List<ScoreVo>> result = new ResultVo<>(ResultCode.OK.getCode());
		TokenVo vo = getTokenUserInfo();
		if (vo == null) {
			logger.error("用户未登录");
			result.setCode(ResultCode.NOT_LOGIN.getCode());
			return result;
		}
		if (userId == null) {
			userId = vo.getUserId();
		}
		if (year == null) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		List<ScoreVo> data = partyMemberService.getScoreListByUserId(userId, year);
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
	public ResultVo<List<ScoreTypeVo>> getTypeScoreListByUserId(Long userId, Integer year) {
		ResultVo<List<ScoreTypeVo>> result = new ResultVo<>(ResultCode.OK.getCode());
		TokenVo vo = getTokenUserInfo();
		if (vo == null) {
			logger.error("用户未登录");
			result.setCode(ResultCode.NOT_LOGIN.getCode());
			return result;
		}
		if (userId == null) {
			userId = vo.getUserId();
		}
		if (year == null) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		List<ScoreTypeVo> data = partyMemberService.getTypeScoreListByUserId(userId, year);
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
	public ResultVo<Paged<ReportPartyMemberVo>> getReportPartyMember(@RequestParam(value = "deptId") Long deptId,
			@RequestParam("subTypeId") Long subTypeId,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		ResultVo<Paged<ReportPartyMemberVo>> result = new ResultVo<>(ResultCode.OK.getCode());

		TokenVo tokenVo = getTokenUserInfo();
		if (tokenVo == null) {
			result.setCode(ResultCode.NOT_LOGIN.getCode());
			return result;
		}
		if (deptId == null) {
			deptId = tokenVo.getDeptId();
		}

		pageNum = PageUtils.normalizePageIndex(pageNum);
		pageSize = PageUtils.normalizePageSize(pageSize);
		Paged<ReportPartyMemberVo> voList = partyMemberService.getReportPartyMember(deptId, subTypeId, pageNum,
				pageSize);
		result.setData(voList);
		return result;
	}

	/**
	 * 思想汇报详情
	 * 
	 * @param id
	 *            党员ID
	 * @param subTypeId
	 *            活动二级分类ID
	 * @return
	 */
	@GetMapping("/getReportDetail")
	@ResponseBody
	public ResultVo<ReportDetailVo> getReportDetail(@RequestParam("id") Long id,
			@RequestParam("subTypeId") Long subTypeId) {
		ResultVo<ReportDetailVo> result = new ResultVo<>(ResultCode.OK.getCode());
		ReportDetailVo detailVo = partyMemberService.getReportDetail(id, subTypeId);
		result.setData(detailVo);
		return result;
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
		ResultVo<Paged<PioneeringPartyMemberVo>> result = new ResultVo<>(ResultCode.OK.getCode());
		TokenVo tokenVo = getTokenUserInfo();
		if (tokenVo == null) {
			result.setCode(ResultCode.NOT_LOGIN.getCode());
			return result;
		}
		if (deptId == null) {
			deptId = tokenVo.getDeptId();
		}
		pageNum = PageUtils.normalizePageIndex(pageNum);
		pageSize = PageUtils.normalizePageSize(pageSize);
		Paged<PioneeringPartyMemberVo> voList = partyMemberService.getPioneeringPartyMembers(deptId, pageNum, pageSize);

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
	public ResultVo<List<AllPartyMemberVo>> queryAllPartyMembersByDeptId(Long deptId) {
		ResultVo<List<AllPartyMemberVo>> result = new ResultVo<>();
		TokenVo vo = getTokenUserInfo();
		if (vo == null) {
			logger.error("用户未登录");
			result.setCode(ResultCode.NOT_LOGIN.getCode());
			return result;
		}
		if (deptId == null || deptId == 0) {
			deptId = vo.getDeptId();
		}
		List<AllPartyMemberVo> list = partyMemberService.queryAllPartyMembersByDeptId(deptId);
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
		ResultVo<Paged<ObserveLowPartyMemberVo>> result = new ResultVo<>(ResultCode.OK.getCode());
		TokenVo tokenVo = getTokenUserInfo();
		if (tokenVo == null) {
			result.setCode(ResultCode.NOT_LOGIN.getCode());
			return result;
		}
		if (deptId == null) {
			deptId = tokenVo.getDeptId();
		}

		pageNum = PageUtils.normalizePageIndex(pageNum);
		pageSize = PageUtils.normalizePageSize(pageSize);

		Paged<ObserveLowPartyMemberVo> paged = partyMemberService.getObserveLowPartyMember(deptId, pageNum, pageSize);

		result.setData(paged);
		return result;
	}

	/**
	 * 遵章守纪详情 支部书记使用
	 *
	 * @param partyMemberId
	 *            党员Id
	 * @return
	 */
	@GetMapping("/getObserveLowDetailForDept")
	@ResponseBody
	public ResultVo<ObserveLowDetailVo> getObserveLowDetailForDept(@RequestParam("partyMemberId") Long partyMemberId) {
		ResultVo<ObserveLowDetailVo> result = new ResultVo<>(ResultCode.OK.getCode());
		ObserveLowDetailVo vo = partyMemberService.getObserveLowDetailForDept(partyMemberId);
		result.setData(vo);
		return result;
	}

	/**
	 * 根据身份证号获取党员积分
	 * 
	 * @param idCard
	 * @return
	 */
	@GetMapping("/getSumScoreByIdCard")
	@ResponseBody
	public ResultVo<Float> getSumScoreByIdCard(@RequestParam("idCard") String idCard) {
		ResultVo<Float> result = new ResultVo<>();
		result = partyMemberService.getSumScoreByIdCard(idCard);
		if (result.getData() == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("获取积分是失败");
		}
		result.setCode(ResultCode.OK.getCode());
		return result;
	}

	/**
	 * 获取党员年度活动信息
	 * 
	 * @return
	 */
	@GetMapping("/getAnnualActiveInfo")
	@ResponseBody
	public ResultVo<AnnualActiveInfo> getAnnualActiveInfo() {
		UserVo user = getCurrentUserFromCache();
		ResultVo<AnnualActiveInfo> resultVo = new ResultVo<>(ResultCode.OK.getCode());
		if (user == null) {
			logger.error("用户未登录");
			resultVo.setCode(ResultCode.NOT_LOGIN.getCode());
			resultVo.setMsg("用户未登录或用户已退出");
			return resultVo;
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		AnnualActiveInfo activeInfo = partyMemberService.getAnnualActiveInfo(user, year);
		resultVo.setData(activeInfo);
		return resultVo;
	}
}
