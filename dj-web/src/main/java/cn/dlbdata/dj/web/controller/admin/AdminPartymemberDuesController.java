/**
 *  <p>Title: AdminPartymemberDuesController.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月12日 
 */
package cn.dlbdata.dj.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.dlbdata.dj.web.base.BaseController;

/**
 * <p>Title: AdminPartymemberDuesController</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月12日  
 */
@Controller
@RequestMapping("/admin/partymemberdues")
public class AdminPartymemberDuesController extends BaseController {
	
	
	/**
	 * 查询列表
	 * 
	 * @return
	 */
	@RequestMapping("/list.html")
	public String list() {
		return "partymemberdues/list.html";
	}

	/**
	 * 详情界面
	 * 
	 * @return
	 */
	@RequestMapping("/detail.html")
	public String detail() {
		return "partymemberdues/detail.html";
	}

	/**
	 * 添加或修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/add.html")
	public String add() {
		return "partymemberdues/add.html";
	}

/*	@RequestMapping("/save")
	@ResponseBody
	public ResultVo<Long> saveOrUpdate(DjSection section) {
		ResultVo<Long> result = new ResultVo<>();
		Long data = sectionService.saveOrUpdate(section);
		if (data != null) {
			result.setCode(ResultCode.OK.getCode());
		}
		return result;
	}

	@RequestMapping("/deleteById")
	@ResponseBody
	public ResultVo<Long> deleteById(Long id) {
		ResultVo<Long> result = new ResultVo<>();
		Long data = sectionService.deleteById(id);
		if (data != null) {
			result.setCode(ResultCode.OK.getCode());
		}
		return result;
	}*/
	
}
