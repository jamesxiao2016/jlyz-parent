package cn.dlbdata.dj.web.controller.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.service.IStudyService;
import cn.dlbdata.dj.vo.StudyVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.vo.study.PendingPtMemberVo;
import cn.dlbdata.dj.vo.study.StudyDetailVo;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * 处理自主学习的controller
 * 
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/api/v1/study")
public class StudyController extends BaseController {
	@Autowired
	private IActiveService activeService;
	@Autowired
	private IStudyService studyService;

	/**
	 * 点击支书查询待办列表项进去的查询
	 * 
	 * @param deptId
	 *            支部ID
	 * @param subTypeId
	 *            活动类型Id
	 * @return
	 */
	@GetMapping("/getPendingListByDeptId")
	@ResponseBody
	public ResultVo<List<PendingPtMemberVo>> getPendingList(@RequestParam("deptId") Long deptId,
			@RequestParam("subTypeId") Long subTypeId) {
		ResultVo<List<PendingPtMemberVo>> result = new ResultVo<>(CoreConst.ResultCode.OK.getCode());
		List<PendingPtMemberVo> voList = activeService.getPendingList(deptId, subTypeId);
		result.setData(voList);
		return result;
	}

	/**
	 * 获取自主学习详情
	 * 
	 * @param studyId
	 *            自主学习Id
	 * @return
	 */
	@GetMapping("/queryById")
	@ResponseBody
	public ResultVo<StudyDetailVo> getStudyDetail(@RequestParam("studyId") Long studyId) {
		// TODO
		return null;

	}

	/**
	 * 发起自主学习
	 * 
	 * @param studyVo
	 * @return
	 */
	@PostMapping("/create")
	@ResponseBody
	public ResultVo<Long> createStudy(@RequestBody StudyVo studyVo) {
		ResultVo<Long> result = new ResultVo<>();
		UserVo user = getCurrentUserFromCache();
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		result = studyService.saveStudy(studyVo, user);

		return result;
	}
}
