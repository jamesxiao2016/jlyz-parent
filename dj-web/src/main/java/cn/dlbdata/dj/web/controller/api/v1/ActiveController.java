package cn.dlbdata.dj.web.controller.api.v1;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.vo.ActiveVo;
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
	
	@GetMapping(value = "/getParticipateActive")
	@ResponseBody
	public ResultVo<List<Map<String,Object>>> getParticipateActive(@RequestBody ActiveVo vo) {
/*		ResultVo<List<Map<String,Object>>> result = new ResultVo<>();
		UserVo data = getCurrentUserFromCache();
		vo.setUserId(data.getUserId());
		vo.setDepartmentId(data.getDeptId());
		result = activeService.getParticipateActive(vo);*/
		return null;
	}
	
	
}
