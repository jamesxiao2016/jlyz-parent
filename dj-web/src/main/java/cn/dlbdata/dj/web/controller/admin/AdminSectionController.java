package cn.dlbdata.dj.web.controller.admin;

import java.util.List;

import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.db.dto.section.SectionAddOrUpdateDto;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.common.core.web.vo.SelectResultVo;
import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.service.IPartyMemberService;
import cn.dlbdata.dj.service.ISectionService;
import cn.dlbdata.dj.web.base.BaseController;

import java.util.List;

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
	@Autowired
	private IPartyMemberService partyMemberService;

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
	public ModelAndView detail(Long id) {
		ModelAndView view = new ModelAndView("section/detail.html");
		DjSection record = sectionService.getSectionInfoById(id);
		view.addObject("record", record);
		return view;
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

	/**
	 * 新增片区
	 * 
	 * @return
	 */
	@RequestMapping("/addSection")
	@ResponseBody
	public ResultVo<Long> addSection(@RequestBody SectionAddOrUpdateDto dto) {
		UserVo user = getCurrentUserFromCache();
		ResultVo<Long> resultVo = new ResultVo<>();
//		if (user == null) {
//			logger.error("用户未登录");
//			resultVo.setCode(CoreConst.ResultCode.NOT_LOGIN.getCode());
//			resultVo.setMsg("用户未登录或用户已退出");
//			return resultVo;
//		}
		Long id = sectionService.addSection(dto, user);
		resultVo.setCode(CoreConst.ResultCode.OK.getCode());
		resultVo.setMsg("新增片区成功!");
		resultVo.setData(id);
		return resultVo;
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

	/**
	 * 获取所有片区列表
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPartyMembersBySectionId")
	public SelectResultVo getPartyMembersBySectionId(Long sectionId) {
		return partyMemberService.getPartyMembersBySectionId(sectionId);
	}

	/**
	 * 获取党支部内的所有成员
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPartyMembersByDeptId")
	public SelectResultVo getPartyMembersByDeptId(Long deptId) {
		return partyMemberService.getPartyMembersByDeptId(deptId);
	}

	@RequestMapping("/updateSection/{id}")
	@ResponseBody
	public ResultVo<Long> updateSection(@RequestBody SectionAddOrUpdateDto dto, @PathVariable Long id) {
		UserVo user = getCurrentUserFromCache();
		ResultVo<Long> resultVo = new ResultVo<>();
//		if (user == null) {
//			logger.error("用户未登录");
//			resultVo.setCode(CoreConst.ResultCode.NOT_LOGIN.getCode());
//			resultVo.setMsg("用户未登录或用户已退出");
//			return resultVo;
//		}
		sectionService.updateSection(dto, id, user);
		resultVo.setCode(CoreConst.ResultCode.OK.getCode());
		resultVo.setMsg("修改片区信息成功!");
		return resultVo;

	}
}
