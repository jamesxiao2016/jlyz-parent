package cn.dlbdata.dj.web.controller.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.DlbConstant.ResultCode;
import cn.dlbdata.dj.service.IPartyMemberService;
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

	@GetMapping("/getScoreAndNum")
	@ResponseBody
	public ResultVo<PartyVo> getScoreAndNumByMemberId(Long memberId) {
		ResultVo<PartyVo> result = new ResultVo<>(ResultCode.OK.getCode());
		PartyVo data = partyMemberService.getScoreAndNumByMemberId(memberId);
		result.setData(data);
		return result;
	}
}
