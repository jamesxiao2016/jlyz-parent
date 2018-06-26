package cn.dlbdata.dj.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.common.core.web.vo.SelectResultVo;
import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.db.dto.building.BuildingAddOrUpdateDto;
import cn.dlbdata.dj.service.IBuildingService;
import cn.dlbdata.dj.web.base.BaseController;


@Controller
@RequestMapping("/admin/building")
public class AdminBuildingController extends BaseController {
    @Autowired
    private IBuildingService buildingService;

    /**
     *查询楼宇列表
     * @return
     */
    @RequestMapping("/building_list.html")
    public String list() {
        return "building/building_list.html";
    }
    
    @RequestMapping("/building_add.html")
    public String add() {
        return "building/building_add.html";
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultVo<Long> save(@RequestBody BuildingAddOrUpdateDto dto) {
        ResultVo<Long> result = new ResultVo<>();
        buildingService.add(dto);
        result.setCode(CoreConst.ResultCode.OK.getCode());
        result.setMsg("新增成功!");
        return result;

    }
    @PostMapping("/updateBuilding/{id}")
    @ResponseBody
    public ResultVo update (@RequestBody BuildingAddOrUpdateDto dto, @PathVariable Long id) {
        ResultVo result = new ResultVo<>();
        buildingService.update(id,dto);
        result.setCode(CoreConst.ResultCode.OK.getCode());
        result.setMsg("修改成功!");
        return result;
    }

    @PostMapping("/invalid/{id}")
    @ResponseBody
    public ResultVo invalid(@PathVariable Long id) {
        ResultVo result = new ResultVo<>();
        buildingService.invalid(id);
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
