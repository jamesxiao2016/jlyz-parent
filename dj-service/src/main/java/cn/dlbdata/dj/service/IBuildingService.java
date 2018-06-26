package cn.dlbdata.dj.service;

import java.util.List;

import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.db.dto.building.BuildingAddOrUpdateDto;
import cn.dlbdata.dj.db.pojo.DjBuilding;

public interface IBuildingService {

	/**
	 * 新增楼宇
	 * 
	 * @param dto
	 * @return
	 */
	boolean add(BuildingAddOrUpdateDto dto);

	/**
	 * 楼宇信息修改
	 * 
	 * @param id
	 *            楼宇Id
	 * @param dto
	 *            dto
	 */
	boolean update(Long id, BuildingAddOrUpdateDto dto);

	/**
	 * 作废楼宇.
	 * 
	 * @param id
	 *            楼宇Id
	 * @return
	 */
	boolean invalid(Long id);

	/**
	 * 根据片区ID获取楼宇列表
	 * 
	 * @param sectionId 片区ID
	 * @return
	 */
	public List<SelectVo> getBuildingListBySectionId(Long sectionId);
	
	/**
	 * 根据ID获取详情
	 * @param id
	 * @return
	 */
	public DjBuilding getInfoById(Long id);
}
