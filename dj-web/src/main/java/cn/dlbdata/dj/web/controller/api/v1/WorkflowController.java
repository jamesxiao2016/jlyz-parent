package cn.dlbdata.dj.web.controller.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.service.IStudyService;
import cn.dlbdata.dj.service.IWorkflowService;
import cn.dlbdata.dj.vo.ApplyVo;
import cn.dlbdata.dj.vo.DisciplineVo;
import cn.dlbdata.dj.vo.PageVo;
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
	@PostMapping("/create")
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
	public ResultVo<Long> applyDiscipline(DisciplineVo vo) {
		ResultVo<Long> result = new ResultVo<>();
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}

		result = workflowService.applyDiscipline(vo, user);
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
	public ResultVo<Long> applyVanguard(VanguardVo[] params) {
		ResultVo<Long> result = new ResultVo<>();
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}

		result = workflowService.applyVanguard(params, user);
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
	public ResultVo<Long> applyThoughts(ThoughtsVo vo) {
		ResultVo<Long> result = new ResultVo<>();
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}

		result = workflowService.applyThoughts(vo, user);
		return result;
	}

	@PostMapping(value = "/audit")
	@ResponseBody
	public ResultVo<String> audit(Long id, Integer result, String content) {
		UserVo user = getCurrentUserFromCache();
		ResultVo<String> resultVo = new ResultVo<>(ResultCode.ParameterError.getCode());
		if (id == null || result == null) {
			resultVo.setMsg("参数不完整");
			return resultVo;
		}
		resultVo = workflowService.audit(id, result, content, user);

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
	public ResultVo<PageVo<ApplyVo>> getPendingList(Long deptId, Integer pageNum, Integer pageSize) {
		ResultVo<PageVo<ApplyVo>> result = new ResultVo<>();
		
		return result;
	}
}
