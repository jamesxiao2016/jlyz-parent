package cn.dlbdata.dj.web.controller.api.v1;

import java.util.List;

import cn.dlbdata.dj.service.IActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.vo.study.ReviewScheduleListVo;
import cn.dlbdata.dj.service.IStudyService;
import cn.dlbdata.dj.vo.UserVo;
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
	
	
	/**
	 * 
	 * <p>Title: getReviewScheduleList</p> 
	 * <p>Description: 获取审核进度列表</p> 
	 * @param subTypeId
	 * @return
	 */
	@GetMapping("/getReviewScheduleList")
	@ResponseBody
	public ResultVo<List<ReviewScheduleListVo>> getReviewScheduleList(Long subTypeId){
		ResultVo<List<ReviewScheduleListVo>> result = new ResultVo<>();
		UserVo user = getCurrentUserFromCache();
		if(user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("请重新登陆！");
			return result;
		}
		List<ReviewScheduleListVo> list = studyService.getReviewScheduleList(subTypeId, user.getUserId());
		if(list == null || list.size() == 0) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("获取审核进度列表失败！");
			return result;
		}
		result.setCode(ResultCode.OK.getCode());
		result.setData(list);
		return result;
	}
	
}
