package cn.dlbdata.dj.web.controller.api.v1;

import cn.dlbdata.dj.service.IStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.service.IActiveService;
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
	private IStudyService studyService;
	@Autowired
	private IActiveService activeService;

//	/**
//	 * 点击支书查询待办列表项进去的查询
//	 *
//	 * @param deptId
//	 *            支部ID
//	 * @param subTypeId
//	 *            活动类型Id
//	 * @return
//	 */
//	@GetMapping("/getPendingListByDeptId")
//	@ResponseBody
//	public ResultVo<Paged<PendingPtMemberVo>> getPendingList(@RequestParam("deptId") Long deptId,
//			@RequestParam("subTypeId") Long subTypeId,
//			@RequestParam(value = "pageNum", required = false) Integer pageNum,
//			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
//		pageNum = Paged.normalizePageIndex(pageNum);
//		pageSize = Paged.normalizePageSize(pageSize);
//		ResultVo<Paged<PendingPtMemberVo>> result = new ResultVo<>(CoreConst.ResultCode.OK.getCode());
//		Paged<PendingPtMemberVo> voList = activeService.getPendingList(deptId, subTypeId,pageNum,pageSize);
//		result.setData(voList);
//		return result;
//	}

	/**
	 * 获取自主学习详情
	 * 
	 * @param applyId 申请Id
	 * @return
	 */
	@GetMapping("/queryById")
	@ResponseBody
	public ResultVo<StudyDetailVo> getStudyDetail(@RequestParam("applyId") Long applyId) {
		ResultVo<StudyDetailVo> result = new ResultVo<>(CoreConst.ResultCode.OK.getCode());
		StudyDetailVo vo = studyService.getStudyDetail(applyId);
		result.setData(vo);
		return result;
	}
}
