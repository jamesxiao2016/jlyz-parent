package cn.dlbdata.dj.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.service.ISectionService;
import cn.dlbdata.dj.web.base.BaseController;

/**
 * 处理后台管理员登录的登录
 * 
 * @author xiaowei
 *
 */
@Controller
@RequestMapping("/admin/section")
public class AdminSectionController extends BaseController {

	@Autowired
	private ISectionService sectionService;

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	@RequestMapping("/list.html")
	public String list() {
		return "section/list.html";
	}

	/**
	 * 详情界面
	 * 
	 * @return
	 */
	@RequestMapping("/detail.html")
	public String detail() {
		return "section/detail.html";
	}

	/**
	 * 添加或修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/add.html")
	public String add() {
		return "section/add.html";
	}

	@RequestMapping("/save")
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
	}
	
	/**
	 * 获取所有片区列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSectionList")
	public List<SelectVo> getSectionList() {
		return sectionService.getSectionList();
	}
}
