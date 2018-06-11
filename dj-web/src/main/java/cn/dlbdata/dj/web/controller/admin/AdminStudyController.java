/**
 *  <p>Title: AdminStudyController.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月11日 
 */
package cn.dlbdata.dj.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.service.IStudyService;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * <p>Title: AdminStudyController</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月11日  
 */
@Controller
@RequestMapping("/admin/study")
public class AdminStudyController  extends BaseController {
	@Autowired
	private IStudyService studyService;

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	@RequestMapping("/list.html")
	public String list() {
		return "study/list.html";
	}

	/**
	 * 详情界面
	 * 
	 * @return
	 */
	@RequestMapping("/detail.html")
	public String detail() {
		return "study/detail.html";
	}

	/**
	 * 添加或修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/add.html")
	public String add() {
		return "study/add.html";
	}
	/**
	 * 
	 * <p>Title: deleteById</p> 
	 * <p>Description: 删除自主学习</p> 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public ResultVo<Long> deleteById(Long id) {
		ResultVo<Long> result = new ResultVo<>();
		Long data = studyService.deleteById(id);
		if (data != null) {
			result.setCode(ResultCode.OK.getCode());
		}
		return result;
	}
}
