package cn.dlbdata.dj.web.controller.api.v1;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjScore;
import cn.dlbdata.dj.service.IPartyMemberService;
import cn.dlbdata.dj.service.IUserService;
import cn.dlbdata.dj.vo.PartyVo;
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
	public ResultVo<Double> getSumScoreByUserId(Long userId, Integer year) {
		ResultVo<Double> result = new ResultVo<>(ResultCode.OK.getCode());
		if (year == null) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		Double score = userService.getSumScoreByUserId(userId, year);
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
}
