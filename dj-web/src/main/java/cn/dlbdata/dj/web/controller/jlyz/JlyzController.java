/**
 *  <p>Title: ApiController.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月6日 
 */
package cn.dlbdata.dj.web.controller.jlyz;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.vo.DjPartyMemberVo;
import cn.dlbdata.dj.db.vo.ScoreActiveVo;
import cn.dlbdata.dj.db.vo.apply.ScoreTypeVo;
import cn.dlbdata.dj.db.vo.jlyz.BuildingVo;
import cn.dlbdata.dj.db.vo.jlyz.MemberStatVo;
import cn.dlbdata.dj.db.vo.jlyz.PartyBranchVo;
import cn.dlbdata.dj.db.vo.jlyz.SectionResVo;
import cn.dlbdata.dj.db.vo.jlyz.SectionVo;
import cn.dlbdata.dj.service.IJlyzService;
import cn.dlbdata.dj.service.IPartyMemberService;
import cn.dlbdata.dj.service.IScoreService;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * <p>
 * Title: ApiController
 * </p>
 * 
 * @author zhouxuan
 *         <p>
 *         Description:
 *         </p>
 * @date 2018年6月6日
 */
@Controller
@RequestMapping("/api/v1/jlyz")
public class JlyzController extends BaseController {

	@Autowired
	private IPartyMemberService partyMemberService;
	@Autowired
	private IScoreService scoreService;
	@Autowired
	private IJlyzService jlyzService;

	@GetMapping(value = "/querySection/{id}.json")
	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResultVo<SectionVo> querySection(@PathVariable Long id) {
		ResultVo<SectionVo> result = new ResultVo<>();
		SectionVo data = jlyzService.querySection(id);
		result.setData(data);
		return result;
	}

	@GetMapping(value = "/queryBuilding/{id}.json")
	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResultVo<BuildingVo> queryBuilding(@PathVariable String id) {
		ResultVo<BuildingVo> result = new ResultVo<>();
		BuildingVo data = jlyzService.queryBuilding(id, getRootUrl());

		result.setData(data);
		return result;
	}

	@GetMapping(value = "/queryPartyBranches/{id}.json")
	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResultVo<List<PartyBranchVo>> queryPartyBranches(@PathVariable String id) {
		ResultVo<List<PartyBranchVo>> result = new ResultVo<>();
		List<PartyBranchVo> data = jlyzService.queryPartyBranches(id);
		if (data != null && data.size() > 0) {
			result.setData(data);
		}
		return result;
	}

	@GetMapping(value = "/queryMemberStatistic/{id}.json")
	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResultVo<MemberStatVo> queryMemberStatistic(@PathVariable String id) {
		ResultVo<MemberStatVo> result = new ResultVo<>();
		MemberStatVo data = jlyzService.queryMemberStatistic(id);
		result.setData(data);
		return result;
	}
	
	/**
	 *  
	 * <p>Title: queryMemberStatistic</p> 
	 * <p>Description: 获取所有片区</p> 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/query_all_sections.json")
	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResultVo<List<SectionResVo>> queryAllSections() {
		ResultVo<List<SectionResVo>> result = new ResultVo<>();
		List<SectionResVo> list = jlyzService.queryAllSections();
		if(list != null && list.size() > 0) {
			result.setCode(ResultCode.OK.getCode());
			result.setData(list);
			return result;
		}else {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("没有相应的片区");
			return result;
		}
		
	}
	
	/**
	 * 根据支部ID获取支部名称获取党员信息
	 * 
	 * @param deptId
	 *            支部ID
	 * @param deptName
	 *            支部名称
	 * @return
	 */
	@GetMapping(value = "/selectPartymemberByDeptId")
	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResultVo<List<DjPartyMemberVo>> selectPartymemberByDeptId(Long deptId, String deptName) {
		ResultVo<List<DjPartyMemberVo>> result = new ResultVo<>();
		if (deptId == null && StringUtils.isEmpty(deptName)) {
			result.setCode(ResultCode.NOT_LOGIN.getCode());
			result.setMsg("查询失败");
			return result;
		}
		List<DjPartyMemberVo> list = partyMemberService.selectPartymemberByDeptId(deptId, deptName);
		if (list.size() == 0 || list == null) {
			result.setCode(ResultCode.NotFound.getCode());
			result.setMsg("查询失败");
			return result;
		}
		result.setCode(ResultCode.OK.getCode());
		result.setData(list);
		return result;
	}

	/**
	 * 根据用户id获取雷达图
	 * 
	 * @param userId
	 * @param year
	 * @return
	 */
	@GetMapping("/getRadarChartByUserId")
	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResultVo<List<ScoreTypeVo>> getRadarChartByUserId(Long userId, Integer year) {
		ResultVo<List<ScoreTypeVo>> result = new ResultVo<>(ResultCode.OK.getCode());
		if (userId == null) {
			logger.error("参数错误");
			result.setCode(ResultCode.ParameterError.getCode());
			return result;
		}
		if (year == null) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		List<ScoreTypeVo> data = partyMemberService.getRadarChartByUserId(userId, year);
		result.setData(data);
		return result;
	}

	/**
	 * 获取党员参加活动的积分明细
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping(value = "/getScoreAndActiveList")
	@ResponseBody
	@CrossOrigin(origins = "*", maxAge = 3600)
	public ResultVo<List<ScoreActiveVo>> getScoreAndActiveList(Long userId) {
		ResultVo<List<ScoreActiveVo>> result = new ResultVo<>();
		if (userId == null) {
			result.setMsg("用户ID为空");
			result.setCode(ResultCode.ParameterError.getCode());
			return result;
		}
		List<ScoreActiveVo> list = scoreService.getScoreAndActiveList(userId);
		if (list == null || list.size() == 0) {
			result.setMsg("查询为空，没有相关的信息");
			result.setCode(ResultCode.NotFound.getCode());
			return result;
		}
		result.setData(list);
		result.setCode(ResultCode.OK.getCode());
		return result;
	}

}
