package cn.dlbdata.dj.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.dlbdata.dj.constant.RoleEnum;
import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.mapper.DjSectionMapper;
import cn.dlbdata.dj.db.mapper.DjUserMapper;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.db.pojo.DjUser;
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
	@Autowired
	private DjUserMapper userMapper;

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
			throw new BusinessException("请选择片区", CoreConst.ResultCode.Forbidden.getCode());
		}
		DjSection section = sectionMapper.selectByPrimaryKey(dto.getSectionId());
		if (section == null) {
			throw new BusinessException("所选的片区不存在!", CoreConst.ResultCode.NotFound.getCode());
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
			throw new BusinessException("请选择片区", CoreConst.ResultCode.Forbidden.getCode());
		}
		DjSection section = sectionMapper.selectByPrimaryKey(dto.getSectionId());
		if (section == null) {
			throw new BusinessException("所选的片区不存在!", CoreConst.ResultCode.NotFound.getCode());
		}
		DjBuilding building = buildingMapper.selectByPrimaryKey(id);

		if (building == null) {
			throw new BusinessException("楼宇不存在!", CoreConst.ResultCode.NotFound.getCode());
		}
		if (building.getStatus() != DlbConstant.BASEDATA_STATUS_VALID) {
			throw new BusinessException("楼宇状态不是有效状态!", CoreConst.ResultCode.Forbidden.getCode());
		}
		if (!dto.getCode().equals(building.getCode())) {
			boolean existWithCode = buildingMapper.existWithCode(dto.getCode(), building.getId());
			if (existWithCode) {
				throw new BusinessException("已存在同编号的楼宇!", CoreConst.ResultCode.Forbidden.getCode());
			}
		}
		boolean existWithName = buildingMapper.existWithName(dto.getName(), building.getId());
		if (existWithName) {
			throw new BusinessException("已存在同名的楼宇!", CoreConst.ResultCode.Forbidden.getCode());
		}

		building.setName(dto.getName());
		building.setFloorNum(dto.getFloorNum());
		building.setAddress(dto.getAddress());
		building.setCode(dto.getCode());

		List<DjDept> depts = djDeptMapper.getByBuildingId(building.getId());
		// 修改楼的编号，楼里面的片区的楼编号也要修改
		for (DjDept dept : depts) {
			dept.setBuildingCode(building.getCode());
			djDeptMapper.updateByPrimaryKey(dept);
		}
		// 当楼的片区发生改变时：
		if (!dto.getSectionId().equals(building.getDjSectionId())) {
			DjSection originalSection = sectionMapper.selectByPrimaryKey(building.getDjSectionId());// 原片区
			// 修改楼宇对应的片区
			building.setDjSectionId(dto.getSectionId());
			// 修改楼宇对应的片区时，楼里面的党支部的所属片区也要修改
			List<Long> deptIds = new ArrayList<>();
			for (DjDept dept : depts) {
				deptIds.add(dept.getId());
				dept.setDjSectionId(section.getId());
				djDeptMapper.updateByPrimaryKey(dept);
			}
			if (!deptIds.isEmpty()) {
				// 一般情况下片区负责人只有一个，防止错误，以List来查询
				List<DjUser> districtLeader = userMapper.getByRoleIdAndDeptIdIn(RoleEnum.HEADER_OF_DISTRICT.getId(),
						deptIds);
				// 若楼里面有片区负责人，则将这个人的职位设为普通党员,该党员对应的原片区的片区负责人id/name也要清空
				for (DjUser leader : districtLeader) {
					if (leader.getId().equals(originalSection.getPrincipalId())) {
						originalSection.setPrincipalId(null);
						originalSection.setPrincipalName(null);
						sectionMapper.updateByPrimaryKey(originalSection);
					}
					leader.setRoleId(RoleEnum.PARTY.getId());
					userMapper.updateByPrimaryKey(leader);
				}
			}
		}

		buildingMapper.updateByPrimaryKey(building);
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

	@Override
	public DjBuilding getInfoById(Long id) {
		if (id == null) {
			return null;
		}
		return buildingMapper.selectByPrimaryKey(id);
	}
}
