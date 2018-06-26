package cn.dlbdata.dj.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.db.dto.building.BuildingAddOrUpdateDto;
import cn.dlbdata.dj.db.pojo.DjBuilding;
import cn.dlbdata.dj.service.IBuildingService;
import cn.dlbdata.dj.web.base.BaseController;

@Controller
@RequestMapping("/admin/building")
public class AdminBuildingController extends BaseController {
	@Autowired
	private IBuildingService buildingService;

	/**
	 * 查询楼宇列表
	 * 
	 * @return
	 */
	@RequestMapping("/building_list.html")
	public String list() {
		return "building/building_list.html";
	}

	@RequestMapping("/building_add.html")
	public ModelAndView add(Long id) {
		ModelAndView view = new ModelAndView("building/building_add.html");
		if (id != null && id > 0) {
			DjBuilding building = buildingService.getInfoById(id);
			view.addObject("record", building);
		}
		return view;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResultVo<Long> save(@RequestBody BuildingAddOrUpdateDto dto) {
		ResultVo<Long> result = new ResultVo<>();
		try {
			buildingService.add(dto);
		} catch (Exception e) {
			logger.error("修改失败", e);
			result.setCode(CoreConst.ResultCode.BadRequest.getCode());
			result.setMsg(e.getMessage());
			return result;
		}
		result.setCode(CoreConst.ResultCode.OK.getCode());
		result.setMsg("新增成功!");
		return result;

	}

	@PostMapping("/updateBuilding/{id}")
	@ResponseBody
	public ResultVo<Long> update(@RequestBody BuildingAddOrUpdateDto dto, @PathVariable Long id) {
		ResultVo<Long> result = new ResultVo<>();
		try {
			buildingService.update(id, dto);
		} catch (Exception e) {
			logger.error("修改失败", e);
			result.setCode(CoreConst.ResultCode.BadRequest.getCode());
			result.setMsg(e.getMessage());
			return result;
		}
		result.setCode(CoreConst.ResultCode.OK.getCode());
		result.setMsg("修改成功!");
		return result;
	}

	@PostMapping("/invalid/{id}")
	@ResponseBody
	public ResultVo<Long> invalid(@PathVariable Long id) {
		ResultVo<Long> result = new ResultVo<>();
		try {
			buildingService.invalid(id);
		} catch (Exception e) {
			logger.error("修改失败", e);
			result.setCode(CoreConst.ResultCode.BadRequest.getCode());
			result.setMsg(e.getMessage());
			return result;
		}
		result.setCode(CoreConst.ResultCode.OK.getCode());
		result.setMsg("作废成功!");
		return result;
	}

	/**
	 * 根据片区ID获取楼宇列表
	 * 
	 * @param sectionId
	 * @return
	 */
	@RequestMapping("/getBuildingList")
	@ResponseBody
	public List<SelectVo> getBuildingList(Long sectionId) {
		List<SelectVo> data = buildingService.getBuildingListBySectionId(sectionId);
		return data;
	}

}
