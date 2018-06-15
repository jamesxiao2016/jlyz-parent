package cn.dlbdata.dj.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.mapper.DjSectionMapper;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.pojo.DjSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dlbdata.dj.common.core.exception.BusinessException;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.dto.building.BuildingAddOrUpdateDto;
import cn.dlbdata.dj.db.mapper.DjBuildingMapper;
import cn.dlbdata.dj.db.pojo.DjBuilding;
import cn.dlbdata.dj.service.IBuildingService;

@Service
public class BuildingServiceImpl implements IBuildingService {

	@Autowired
	private DjBuildingMapper buildingMapper;
	@Autowired
	private DjSectionMapper sectionMapper;
	@Autowired
	private DjDeptMapper djDeptMapper;

	/**
	 * 新增楼宇
	 * 
	 * @param dto
	 * @return
	 */
	@Override
	public boolean add(BuildingAddOrUpdateDto dto) {

		boolean existWithCode = buildingMapper.existWithCode(dto.getCode(), null);
		if (existWithCode) {
			throw new BusinessException("已存在同编号的楼宇!", CoreConst.ResultCode.Forbidden.getCode());
		}
		boolean existWithName = buildingMapper.existWithName(dto.getName(), null);
		if (existWithName) {
			throw new BusinessException("已存在同名的楼宇!", CoreConst.ResultCode.Forbidden.getCode());
		}
        if (dto.getSectionId() == null) {
			throw new BusinessException("请选择片区",CoreConst.ResultCode.Forbidden.getCode());
		}
		DjSection section = sectionMapper.selectByPrimaryKey(dto.getSectionId());
		if (section == null) {
			throw new BusinessException("所选的片区不存在!",CoreConst.ResultCode.NotFound.getCode());
		}
		DjBuilding building = new DjBuilding();

		building.setDjSectionId(section.getId());
		building.setName(dto.getName());
		building.setFloorNum(dto.getFloorNum());
		building.setAddress(dto.getAddress());
		building.setCode(dto.getCode());

		building.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
		building.setId(DigitUtil.generatorLongId());
		building.setCreateTime(new Date());
		buildingMapper.insert(building);
		return false;
	}

	/**
	 * 楼宇信息修改
	 *
	 * @param id
	 *            楼宇Id
	 * @param dto
	 *            dto
	 */
	@Transactional
	@Override
	public boolean update(Long id, BuildingAddOrUpdateDto dto) {
		if (dto.getSectionId() == null) {
			throw new BusinessException("请选择片区",CoreConst.ResultCode.Forbidden.getCode());
		}
		DjSection section = sectionMapper.selectByPrimaryKey(dto.getSectionId());
		if (section == null) {
			throw new BusinessException("所选的片区不存在!",CoreConst.ResultCode.NotFound.getCode());
		}
		DjBuilding building = buildingMapper.selectByPrimaryKey(id);
		if (building == null) {
			throw new BusinessException("楼宇不存在!", CoreConst.ResultCode.NotFound.getCode());
		}
		if (building.getStatus() != DlbConstant.BASEDATA_STATUS_VALID) {
			throw new BusinessException("楼宇状态不是有效状态!", CoreConst.ResultCode.Forbidden.getCode());
		}

		boolean existWithCode = buildingMapper.existWithCode(dto.getCode(), building.getId());
		if (existWithCode) {
			throw new BusinessException("已存在同编号的楼宇!", CoreConst.ResultCode.Forbidden.getCode());
		}
		boolean existWithName = buildingMapper.existWithName(dto.getName(), building.getId());
		if (existWithName) {
			throw new BusinessException("已存在同名的楼宇!", CoreConst.ResultCode.Forbidden.getCode());
		}
		building.setDjSectionId(dto.getSectionId());
		building.setName(dto.getName());
		building.setFloorNum(dto.getFloorNum());
		building.setAddress(dto.getAddress());
		building.setCode(dto.getCode());

		buildingMapper.updateByPrimaryKey(building);
		List<DjDept> depts = djDeptMapper.getByBuildingId(building.getId());
		for (DjDept dept : depts) {
			dept.setBuildingCode(building.getCode());
			dept.setDjSectionId(section.getId());
			djDeptMapper.updateByPrimaryKey(dept);
		}
		return true;
	}

	/**
	 * 作废楼宇.
	 *
	 * @param id
	 *            楼宇Id
	 * @return
	 */
	@Override
	public boolean invalid(Long id) {
		DjBuilding building = buildingMapper.selectByPrimaryKey(id);
		if (building == null) {
			throw new BusinessException("楼宇不存在!", CoreConst.ResultCode.NotFound.getCode());
		}
		if (building.getStatus() != DlbConstant.BASEDATA_STATUS_VALID) {
			throw new BusinessException("楼宇状态已经是作废状态!", CoreConst.ResultCode.Forbidden.getCode());
		}
		building.setStatus(DlbConstant.BASEDATA_STATUS_INVALID);
		buildingMapper.updateByPrimaryKey(building);
		return true;
	}

	@Override
	public List<SelectVo> getBuildingListBySectionId(Long sectionId) {
		List<SelectVo> rlist = new ArrayList<>();
		if (sectionId == null || sectionId == 0) {
			return rlist;
		}

		DjBuilding condition = new DjBuilding();
		condition.setDjSectionId(sectionId);
		condition.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
		List<DjBuilding> list = buildingMapper.select(condition);
		if (list != null) {
			for (DjBuilding building : list) {
				rlist.add(new SelectVo(building.getId() + "", building.getName()));
			}
		}
		return rlist;
	}
}
