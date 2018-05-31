package cn.dlbdata.dj.web.controller.api.v1;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.ImageUtil;
import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.db.pojo.DjActiveUser;
import cn.dlbdata.dj.dto.ActiveSignUpRequest;
import cn.dlbdata.dj.dto.PartyMemberLifeNotice;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.service.IActiveUserService;
import cn.dlbdata.dj.vo.ActiveVo;
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
	 * 
	 * <p>Title: getParticipateActive</p>
	 * <p>Description:党员生活通知接口</p>
	 * @param partyMemberLifeNotice
	 * @return
	 */
	@GetMapping(value = "/getParticipateActive")
	@ResponseBody
	public ResultVo<Paged<Map<String, Object>>> getParticipateActive(PartyMemberLifeNotice partyMemberLifeNotice) {
		ResultVo<Paged<Map<String, Object>>> result = new ResultVo<>();
		UserVo data = getCurrentUserFromCache();
		if(data == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("请重新登录");
			return result;
		}
		partyMemberLifeNotice.setUserId(data.getUserId());
		partyMemberLifeNotice.setDepartmentId(data.getDeptId());
		Paged<Map<String, Object>> page = activeService.getParticipateActive(partyMemberLifeNotice);
		result.setCode(ResultCode.OK.getCode());
		result.setData(page);
		return result;
	}
	
	
	/**
	 * 党员生活通知接口获取第一条
	 * <p>
	 * Title: getParticipateActive
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param partyMemberLifeNotice
	 * @return
	 */
	@GetMapping(value = "/getParticipateActiveOne")
	@ResponseBody
	public ResultVo<Map<String, Object>> getParticipateActiveOne(PartyMemberLifeNotice partyMemberLifeNotice) {
		ResultVo<Map<String, Object>> result = new ResultVo<>();
		UserVo data = getCurrentUserFromCache();
		if(data == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("请重新登录");
			return result;
		}
		partyMemberLifeNotice.setUserId(data.getUserId());
		partyMemberLifeNotice.setDepartmentId(data.getDeptId());
		result = activeService.getParticipateActiveOne(partyMemberLifeNotice);
		if (result.getData() == null || result.getData().isEmpty()) {
			result.setCode(ResultCode.Forbidden.getCode());
		} else {
			result.setCode(ResultCode.OK.getCode());
		}
		return result;
	}
	
	

	/**
	 * 金领驿站活动报名
	 * <p>
	 * Title: participate
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param activeId
	 * @return
	 */
	@PostMapping(value = "/participate")
	@ResponseBody
	public ResultVo<String> participate(@RequestBody ActiveSignUpRequest activeSignUpRequest) {
		ResultVo<String> result = new ResultVo<>();
		UserVo data = getCurrentUserFromCache();
		if(data == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("请重新登录");
			return result;
		}
		activeSignUpRequest.setUserId(data.getUserId());
		result = activeUserService.insertActiveSignUp(activeSignUpRequest,data);
		return result;
	}

	/**
	 * 
	 * <p>
	 * Title: getEnjoyActiveByUserId
	 * </p>
	 * <p>
	 * Description:查看自己参加的活动
	 * </p>
	 * 
	 * @param userId
	 * @param all
	 * @return
	 */
	@GetMapping(value = "/getEnjoyActiveByUserId")
	@ResponseBody
	public ResultVo<Paged<DjActive>> getEnjoyActiveByUserId(Integer pageNum, Integer pageSize) {
		ResultVo<Paged<DjActive>> result = new ResultVo<>();
		UserVo data = getCurrentUserFromCache();
		if (data == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("请先登录");
			return result;
		}
		DjActiveUser djActiveUser = new DjActiveUser();
		djActiveUser.setStatus(1);
		Paged<DjActive> page = activeUserService.getMyJoinActive(data.getUserId(), djActiveUser.getStatus(), pageNum, pageSize);
		result.setCode(ResultCode.OK.getCode());
		result.setData(page);
		return result;
	}

	/**
	 * 
	 * <p>
	 * Title: showQrCode
	 * </p>
	 * <p>
	 * Description:显示活动二维码
	 * </p>
	 * 
	 * @param activeId
	 * @param response
	 */
	@GetMapping(value = "/showQrCode")
	public void showQrCode(Long activeId, HttpServletResponse response) {
		String content = "http://dj.dlbdata.cn/#/active/activeSign/" + activeId;
		OutputStream out = null;
		BufferedImage image;
		try {
			image = ImageUtil.genPic(content);
			response.setContentType("image/jpeg");
			// 设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			out = response.getOutputStream();// 取得响应输出流
			ImageIO.write(image, "JPEG", out);
			out.flush();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {

				}
			}
		}

	}

	/**
	 * 发起活动
	 * 
	 * @param vo
	 * @return
	 */
	@PostMapping(value = "/create")
	@ResponseBody
	public ResultVo<Long> createActive(@RequestBody ActiveVo vo) {
		ResultVo<Long> result = new ResultVo<>();

		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		result = activeService.createActive(vo, user);

		return result;
	}

	/**
	 * 活动签到
	 * 
	 * @param activeId
	 * @return
	 */
	@PostMapping(value = "/signIn")
	@ResponseBody
	public ResultVo<String> signIn(Long activeId) {
		ResultVo<String> result = new ResultVo<>();
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		result = activeService.signIn(activeId, user);

		return result;
	}

	/**
	 * 活动报名
	 * 
	 * @param activeId
	 * @return
	 */
	@PostMapping(value = "/signUp")
	@ResponseBody
	public ResultVo<String> signUp(Long activeId) {
		ResultVo<String> result = new ResultVo<>();
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		result = activeService.signUp(activeId, user);

		return result;
	}

	@GetMapping(value = "/queryActiveById")
	@ResponseBody
	public ResultVo<Map<String, Object>> queryActiveById(Long activeId) {
		ResultVo<Map<String, Object>> result = new ResultVo<>();
		UserVo data = getCurrentUserFromCache();
		if (data == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("请先登录");
			return result;
		}
		ResultVo<Map<String, Object>> res = activeService.queryActiveById(activeId, data.getUserId());
		if (res == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("没有相关的活动");
			return result;
		}
		result.setCode(ResultCode.OK.getCode());
		result.setData(res.getData());
		return result;
	}

}
