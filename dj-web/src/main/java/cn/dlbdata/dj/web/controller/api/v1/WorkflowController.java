package cn.dlbdata.dj.web.controller.api.v1;

import cn.dlbdata.dj.db.vo.ToDoVo;
import cn.dlbdata.dj.db.vo.study.PendingPtMemberVo;
import cn.dlbdata.dj.dto.vangard.VanguardParamVo;
import cn.dlbdata.dj.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.PageUtils;
import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjApply;
import cn.dlbdata.dj.db.vo.apply.PioneeringApplyDetailVo;
import cn.dlbdata.dj.db.vo.apply.ScoreApplyVo;
import cn.dlbdata.dj.service.IStudyService;
import cn.dlbdata.dj.service.IWorkflowService;
import cn.dlbdata.dj.web.base.BaseController;

import java.util.List;

@Controller
@RequestMapping("/api/v1/flow")
public class WorkflowController extends BaseController {

	@Autowired
	private IWorkflowService workflowService;
	@Autowired
	private IStudyService studyService;

	/**
	 * 发起自主学习申请
	 * 
	 * @param studyVo
	 * @return
	 */
	@PostMapping("/applyStudy")
	@ResponseBody
	public ResultVo<Long> applyStudy(@RequestBody StudyVo studyVo) {
		ResultVo<Long> result = new ResultVo<>();
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		result = studyService.saveStudy(studyVo, user);

		return result;
	}

	/**
	 * 驿站生活违纪违规扣分申请
	 * 
	 * @param vo
	 * @return
	 */
	@PostMapping(value = "/applyDiscipline")
	@ResponseBody
	public ResultVo<Long> applyDiscipline(@RequestBody DisciplineVo vo) {
		ResultVo<Long> result = new ResultVo<>();
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}

		result = workflowService.doApplyDiscipline(vo, user);
		return result;
	}

	/**
	 * 先锋作用申请
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/applyVanguard")
	@ResponseBody
	public ResultVo<Long> applyVanguard(@RequestBody VanguardParamVo param) {
		ResultVo<Long> result = new ResultVo<>();
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}

		result = workflowService.doApplyVanguard(param, user);
		return result;
	}

	/**
	 * 思想汇报申请
	 * 
	 * @param vo
	 * @return
	 */
	@PostMapping(value = "/applyThoughts")
	@ResponseBody
	public ResultVo<Long> applyThoughts(@RequestBody ThoughtsVo vo) {
		ResultVo<Long> result = new ResultVo<>();
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}

		result = workflowService.doApplyThoughts(vo, user);
		return result;
	}

	/**
	 * 业务处理
	 * 
	 * @param auditVo
	 * @return
	 */
	@PostMapping(value = "/audit")
	@ResponseBody
	public ResultVo<String> audit(@RequestBody AuditVo auditVo) {
		UserVo user = getCurrentUserFromCache();
		ResultVo<String> resultVo = new ResultVo<>(ResultCode.ParameterError.getCode());
		if (user == null) {
			logger.error("用户未登录");
			resultVo.setCode(ResultCode.Forbidden.getCode());
			resultVo.setMsg("用户未登录或用户已退出");
			return resultVo;
		}
		if (auditVo == null || auditVo.getId() == null || auditVo.getResult() == null) {
			resultVo.setMsg("参数不完整");
			return resultVo;
		}
		resultVo = workflowService.doAudit(auditVo, user);

		return resultVo;
	}

	/**
	 * 获取待办列表
	 * 
	 * @param deptId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping(value = "/getPendingList")
	@ResponseBody
	public ResultVo<Paged<PendingPtMemberVo>> getPendingList(Long subTypeId, Long deptId, Integer pageNum, Integer pageSize) {
		ResultVo<Paged<PendingPtMemberVo>> result = new ResultVo<>();
		pageNum = PageUtils.normalizePageIndex(pageNum);
		pageSize = PageUtils.normalizePageSize(pageSize);
		Paged<PendingPtMemberVo> data = workflowService.getPendingList(deptId,subTypeId, pageNum, pageSize);
		result.setCode(ResultCode.OK.getCode());
		result.setData(data);
		return result;
	}

	/**
	 * 积分审核列表
	 * 
	 * @param status
	 *            审核状态
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/getScoreAuditList")
	@ResponseBody
	public ResultVo<Paged<ScoreApplyVo>> getScoreAuditList(@RequestParam(value = "status") Integer status,
			@RequestParam(value = "deptId", required = false) Long deptId,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		UserVo user = getCurrentUserFromCache();
		pageNum = PageUtils.normalizePageIndex(pageNum);
		pageSize = PageUtils.normalizePageSize(pageSize);
		Paged<ScoreApplyVo> paged = workflowService.getScoreAuditList(user, status, pageNum, pageSize, deptId);
		ResultVo<Paged<ScoreApplyVo>> result = new ResultVo<>(ResultCode.OK.getCode());
		result.setData(paged);
		return result;
	}

	/**
	 * 查询积分审核详情(先锋作用的三个)
	 * 
	 * @param partyMemberId
	 *            党员Id
	 * @return
	 */
	@GetMapping("/getPioneeringApplyDetail")
	@ResponseBody
	public ResultVo<PioneeringApplyDetailVo> getPioneeringApplyDetail(
			@RequestParam("partyMemberId") Long partyMemberId) {
		PioneeringApplyDetailVo vo = workflowService.getPioneeringApplyDetail(partyMemberId);
		ResultVo<PioneeringApplyDetailVo> result = new ResultVo<>(ResultCode.OK.getCode());
		result.setData(vo);
		return result;
	}

	/**
	 * 片区负责人/书记代办列表
	 * @return
	 */
	@GetMapping("/todoList")
	@ResponseBody
	public ResultVo<List<ToDoVo>> getTodoList() {
		ResultVo<List<ToDoVo>> result = new ResultVo<>();
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		List<ToDoVo> toDoVos = workflowService.getTodoList(user);
		result.setData(toDoVos);
		return result;
	}

	/**
	 * 获取自主活动流程中的积分
	 * @return
	 */
	@GetMapping("/sumScoreInProcess")
	@ResponseBody
	public ResultVo<Float> sumScoreInProcess() {
		UserVo user = getCurrentUserFromCache();
		ResultVo<Float> result = new ResultVo<>();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		Float score = workflowService.sumScoreInProcess(user);
		result.setData(score);
		return result;
	}
}
