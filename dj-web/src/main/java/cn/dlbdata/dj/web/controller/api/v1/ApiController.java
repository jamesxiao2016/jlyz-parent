/**
 *  <p>Title: ApiController.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月6日 
 */
package cn.dlbdata.dj.web.controller.api.v1;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.vo.DjPartyMemberVo;
import cn.dlbdata.dj.db.vo.ScoreActiveVo;
import cn.dlbdata.dj.db.vo.apply.ScoreTypeVo;
import cn.dlbdata.dj.service.IPartyMemberService;
import cn.dlbdata.dj.service.IScoreService;
import cn.dlbdata.dj.web.base.BaseController;
import cn.dlbdata.dj.web.vo.TokenVo;
/**
 * <p>Title: ApiController</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月6日  
 */
@Controller
@RequestMapping("/api/v1/api")
public class ApiController extends BaseController{
	@Autowired
	private IPartyMemberService partyMemberService;
	@Autowired
	private IScoreService scoreService;
    
    /**
     * 支部党员信息
     * @param departmentid
     * @return
     */
    @GetMapping(value="/selectPartymemberByDeptId")
    @ResponseBody
    public ResultVo<List<DjPartyMemberVo>> selectPartymemberByDeptId(Integer deptId){
    	ResultVo<List<DjPartyMemberVo>> result = new ResultVo<>();
    	if(deptId == null) {
    		result.setCode(ResultCode.NOT_LOGIN.getCode());
    		result.setMsg("查询失败");
    		return result;
    	}
        List<DjPartyMemberVo> list = partyMemberService.selectPartymemberByDeptId(deptId);
       if(list.size() == 0 || list == null) {
    	result.setCode(ResultCode.NOT_LOGIN.getCode());
   		result.setMsg("查询失败");
   		return result;
       }
       result.setCode(ResultCode.OK.getCode());
  		result.setData(list);
        return result;
    }
    /**
     * 根据用户id获取雷达图
     * @param userId
     * @param year
     * @return
     */
	@GetMapping("/getRadarChartByUserId")
	@ResponseBody
	public ResultVo<List<ScoreTypeVo>> getRadarChartByUserId(Long userId, Integer year) {
		ResultVo<List<ScoreTypeVo>> result = new ResultVo<>(ResultCode.OK.getCode());
//		TokenVo vo = getTokenUserInfo();
//		if (vo == null) {
//			logger.error("用户未登录");
//			result.setCode(ResultCode.NOT_LOGIN.getCode());
//			return result;
//		}
		if (userId == null) {
			logger.error("用户未登录");
			result.setCode(ResultCode.NOT_LOGIN.getCode());
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
     * @param userId
     * @return
    */
    @GetMapping(value="/getScoreAndActiveList")
    @ResponseBody
    public ResultVo<List<ScoreActiveVo>> getScoreAndActiveList(Integer userId){
    	ResultVo<List<ScoreActiveVo>> result = new ResultVo<>();
    	if(userId == null ) {
    		result.setMsg("查询失败！！！");
    		result.setCode(ResultCode.NOT_LOGIN.getCode());
    		return result;
    	}
    	List<ScoreActiveVo> list = scoreService.getScoreAndActiveList(userId);
    	if(list == null || list.size() == 0) {
    		result.setMsg("查询为空，没有相关的信息");
    		result.setCode(ResultCode.NOT_LOGIN.getCode());
    		return result;
    	}
    	result.setData(list);
    	result.setCode(ResultCode.OK.getCode());
    	return result;
    }
	
	
}
