/**
 *  <p>Title: ScoreController.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年5月24日 
 */
package cn.dlbdata.dj.web.controller.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjSubType;
import cn.dlbdata.dj.db.pojo.DjType;
import cn.dlbdata.dj.service.IScoreService;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * <p>
 * Title: ScoreController
 * </p>
 * 
 * @author zhouxuan
 *         <p>
 *         Description:
 *         </p>
 * @date 2018年5月24日
 */

@Controller
@RequestMapping("/api/v1/score")
public class ScoreController extends BaseController {
	@Autowired
	private IScoreService scoreService;

	/**
	 * 
	 * <p>
	 * Title: getActiveTypeList
	 * </p>
	 * <p>
	 * Description: 获取活动类型列表
	 * </p>
	 * 
	 * @return
	 */
	@GetMapping(value = "/getActiveTypeList")
	@ResponseBody
	public ResultVo<List<DjSubType>> getActiveTypeList() {
		ResultVo<List<DjSubType>> result = new ResultVo<>();
		List<DjSubType> list = scoreService.getActiveTypeList();
		if (list == null || list.size() == 0) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("没有活动类型");
		} else {
			result.setCode(ResultCode.OK.getCode());
			result.setData(list);
		}
		return result;
	}

	/**
	 * 获取分类信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/getTypeInfo")
	@ResponseBody
	public ResultVo<DjType> getTypeInfo(Long id) {
		ResultVo<DjType> result = new ResultVo<>(ResultCode.OK.getCode());
		DjType data = scoreService.getTypeInfoById(id);
		result.setData(data);
		return result;
	}

	/**
	 * 获取二级分类信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/getSubTypeInfo")
	@ResponseBody
	public ResultVo<DjSubType> getSubTypeInfo(Long id) {
		ResultVo<DjSubType> result = new ResultVo<>(ResultCode.OK.getCode());
		DjSubType data = scoreService.getSubTypeInfoById(id);
		result.setData(data);
		return result;
	}
}
