package cn.dlbdata.dj.web.controller.api.v1;

import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.db.vo.vo.apply.ScoreApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.PageVo;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjApply;
import cn.dlbdata.dj.service.IStudyService;
import cn.dlbdata.dj.service.IWorkflowService;
import cn.dlbdata.dj.vo.AuditVo;
import cn.dlbdata.dj.vo.DisciplineVo;
import cn.dlbdata.dj.vo.StudyVo;
import cn.dlbdata.dj.vo.ThoughtsVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.vo.VanguardVo;
import cn.dlbdata.dj.web.base.BaseController;

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
	 * 遵章守纪申请
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
	 * @param vo
	 * @return
	 */
	@PostMapping(value = "/applyVanguard")
	@ResponseBody
	public ResultVo<Long> applyVanguard(@RequestBody VanguardVo[] params) {
		ResultVo<Long> result = new ResultVo<>();
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}

		result = workflowService.doApplyVanguard(params, user);
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
	 * @param auditVo
	 * @return
	 */
	@PostMapping(value = "/audit")
	@ResponseBody
	public ResultVo<String> audit(@RequestBody AuditVo auditVo) {
		UserVo user = getCurrentUserFromCache();
		ResultVo<String> resultVo = new ResultVo<>(ResultCode.ParameterError.getCode());
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
	public PageVo<DjApply> getPendingList(Long typeId, Long deptId, Integer pageNum, Integer pageSize) {
		PageVo<DjApply> result = new PageVo<DjApply>();
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		result = workflowService.getPendingList(user.getUserId(), deptId, typeId, null, pageNum, pageSize);
		return result;
	}

	/**
	 *积分审核列表
	 * @param status 审核状态
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/getScoreAuditList")
	@ResponseBody
	public ResultVo<Paged<ScoreApplyVo>> getScoreAuditList(
			@RequestParam(value = "status",required = false) Integer status,
			@RequestParam(value = "deptId",required = false) Long deptId,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		UserVo user = getCurrentUserFromCache();
		pageNum = Paged.normalizePageIndex(pageNum);
		pageSize = Paged.normalizePageSize(pageSize);
		Paged<ScoreApplyVo> paged = workflowService.getScoreAuditList(user,status,pageNum,pageSize,deptId);
		ResultVo<Paged<ScoreApplyVo>> result = new ResultVo<>(ResultCode.OK.getCode());
		result.setData(paged);
		return  result;
	}
}
