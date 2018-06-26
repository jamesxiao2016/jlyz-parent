/**
 *  <p>Title: ApiController.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月6日 
 */
package cn.dlbdata.dj.web.controller.api.v1;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.ConfigUtil;
import cn.dlbdata.dj.common.core.util.JwtTokenUtil;
import cn.dlbdata.dj.common.core.util.StringUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.SourceTypeEnum;
import cn.dlbdata.dj.db.pojo.DjLogLogin;
import cn.dlbdata.dj.db.vo.DjPartyMemberVo;
import cn.dlbdata.dj.db.vo.ScoreActiveVo;
import cn.dlbdata.dj.db.vo.UserResVo;
import cn.dlbdata.dj.db.vo.apply.ScoreTypeVo;
import cn.dlbdata.dj.service.ILogLoginService;
import cn.dlbdata.dj.service.IPartyMemberService;
import cn.dlbdata.dj.service.IScoreService;
import cn.dlbdata.dj.service.IUserService;
import cn.dlbdata.dj.web.base.BaseController;
import cn.dlbdata.dj.web.util.TokenUtil;

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
@RequestMapping("/api/v1/auth")
public class ApiController extends BaseController {

	private String THIRD_KEJIANG = ConfigUtil.get("third_kejiang");

	@Autowired
	private IUserService userService;
	@Autowired
	private IPartyMemberService partyMemberService;
	@Autowired
	private IScoreService scoreService;
	@Autowired
	private ILogLoginService logLoginService;

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

	/**
	 * 
	 * <p>
	 * Title: getToken
	 * </p>
	 * <p>
	 * Description: 第三方获取token
	 * </p>
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getToken")
	@ResponseBody
	public ResultVo<String> getToken(String code) {
		logger.info("third-getToken->" + code);
		ResultVo<String> result = new ResultVo<>();
		if (!THIRD_KEJIANG.equals(code)) {
			result.setCode(ResultCode.INVALID_TOKEN.getCode());
			result.setMsg("无效标识");
			return result;
		}

		String token = JwtTokenUtil.createToken(code, "", "", "", 0);
		result.setCode(ResultCode.OK.getCode());
		result.setData(token);
		TokenUtil.TOKEN_MAP.put(token, token);
		return result;
	}

	/**
	 * 
	 * <p>
	 * Title: login
	 * </p>
	 * <p>
	 * Description: 第三方登录逻辑
	 * </p>
	 * 
	 * @param token
	 * @param account
	 * @param password
	 * @param miandeng
	 * @param phoneType
	 * @return
	 */
	@PostMapping(value = "/login")
	@ResponseBody
	public ResultVo<UserResVo> login(String token, String account, String password, String miandeng, String phoneType) {
		logger.info("third-login->" + token + "->" + account + "->" + password + "->" + miandeng + "->" + phoneType);
		ResultVo<UserResVo> result = new ResultVo<>();
		if (StringUtils.isEmpty(token)) {
			result.setCode(ResultCode.NotFound.getCode());
			result.setMsg("token不能为空");
			return result;
		}
		// 判断token是否有
		if (!token.equals(TokenUtil.TOKEN_MAP.get(token))) {
			result.setCode(ResultCode.INVALID_TOKEN.getCode());
			result.setMsg("token无效");
			return result;
		}
		// 移除token
		TokenUtil.TOKEN_MAP.remove(token);
		// 登录逻辑处理
		result = userService.thirdLogin(account, password, miandeng, phoneType);
		DjLogLogin djLogLogin = new DjLogLogin();
		if (result.getData() != null) {
			djLogLogin.setDjUserId(result.getData().getUserId());
			djLogLogin.setErrorMsg(result.getMsg());
			djLogLogin.setUserName(result.getData().getUserName());
			djLogLogin.setDjDeptId(result.getData().getDeptId());
			djLogLogin.setCreateTime(new Date());
			djLogLogin.setStatus(result.getCode());
			djLogLogin.setSourceType(SourceTypeEnum.THIRD_LOGIN.getId());
		} else {
			djLogLogin.setErrorMsg(result.getMsg());
			djLogLogin.setCreateTime(new Date());
			djLogLogin.setStatus(result.getCode());
			djLogLogin.setSourceType(SourceTypeEnum.THIRD_LOGIN.getId());
		}
		logLoginService.insertLoginLogger(djLogLogin);
		result.getData().setDeptId(0L);
		result.getData().setUserId(0L);
		return result;
	}

}
