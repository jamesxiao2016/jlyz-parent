package cn.dlbdata.dj.web.controller.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.service.IWorkflowService;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.web.base.BaseController;

@Controller
@RequestMapping("/api/v1/flow")
public class WorkflowController extends BaseController {

	@Autowired
	private IWorkflowService workflowService;

	@PostMapping(value = "/audit")
	@ResponseBody
	public ResultVo<Long> audit(Long id, Integer result, String content) {
		UserVo user = getCurrentUserFromCache();
		ResultVo<Long> resultVo = new ResultVo<>(ResultCode.Forbidden.getCode());
		String rs = workflowService.audit(id, result, content, user);
		if (CoreConst.SUCCESS.equals(rs)) {
			resultVo.setCode(ResultCode.OK.getCode());
		}
		return resultVo;
	}
}
