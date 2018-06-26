package cn.dlbdata.dj.serviceimpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.dlbdata.dj.db.vo.dept.*;
import cn.dlbdata.dj.db.vo.party.AllPartyMemberVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dlbdata.dj.common.core.exception.BusinessException;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.constant.RoleEnum;
import cn.dlbdata.dj.db.dto.dept.DeptAddOrUpdateDto;
import cn.dlbdata.dj.db.mapper.DjBuildingMapper;
import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.mapper.DjPartymemberMapper;
import cn.dlbdata.dj.db.mapper.DjSectionMapper;
import cn.dlbdata.dj.db.mapper.DjUserMapper;
import cn.dlbdata.dj.db.pojo.DjBuilding;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.db.vo.party.BranchDeptInfoVo;
import cn.dlbdata.dj.db.vo.party.SectionInfoVo;
import cn.dlbdata.dj.service.IDeptService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.UserVo;
import tk.mybatis.mapper.entity.Example;

@Service
public class DeptServiceImpl extends BaseServiceImpl implements IDeptService {
	@Autowired
	private DjDeptMapper deptMapper;
	@Autowired
	private DjSectionMapper sectionMapper;
	@Autowired
	private DjUserMapper userMapper;
	@Autowired
	private DjBuildingMapper buildingMapper;
	@Autowired
	private DjPartymemberMapper partymemberMapper;

	@Override
	public DjDept getDeptInfoById(Long id) {
		if (id == null) {
			logger.error("参数获取失败");
			return null;
		}
		return deptMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<DjDept> getDeptListByParentId(Long parentId) {
		if (parentId == null) {
			return null;
		}
		Example example = new Example(DjDept.class);
		example.createCriteria().andEqualTo("parentId", parentId);
		return deptMapper.selectByExample(example);
	}

	/*
	 * (non-Javadoc) <p>Title: getDeptMessage</p> <p>Description: 党支部信息</p>
	 * 
	 * @return
	 * 
	 * @see cn.dlbdata.dj.service.IDeptService#getDeptMessage()
	 */
	@Override
	public DjDept getDeptMessage(Long deptId) {
		if (deptId == null) {
			return null;
		}
		return deptMapper.selectByPrimaryKey(deptId);
	}

	/**
	 * 片区负责人获取支部信息列表
	 *
	 * @return
	 */
	@Override
	public List<BranchDeptInfoVo> getBranchDeptInfo(UserVo user) {
		if (user.getSectionId() == null) {
			return new ArrayList<>();
		}
		List<BranchDeptInfoVo> voList = deptMapper.getBranchDeptInfoBySectionId(user.getSectionId());
		return voList;
	}

	/**
	 * 获取片区信息（片区负责人登录时首页）
	 *
	 * @param userId
	 *            id
	 * @return
	 */
	@Override
	public SectionInfoVo getSectionInfo(Long userId) {
		DjSection section = sectionMapper.getByPrincipalId(userId);
		if (section == null) {// 当前用户不是片区负责人
			return new SectionInfoVo();
		}
		SectionInfoVo sectionInfoVo = sectionMapper.getSectionInfo(section.getId());
		sectionInfoVo.setPartyCommittee(DlbConstant.PARTYCOMMITTEE_LJZ);
		return sectionInfoVo;
	}

	/*
	 * (non-Javadoc) <p>Title: selectDeptNameByDeptId</p> <p>Description: 获取支部名称</p>
	 * 
	 * @param deptId
	 * 
	 * @return
	 * 
	 * @see cn.dlbdata.dj.service.IDeptService#selectDeptNameByDeptId(java.lang.Long)
	 */
	// @Override
	// public DjDept selectDeptNameByDeptId(Long deptId) {
	//
	// return deptMapper.selectByPrimaryKey(deptId);
	// }
	/**
	 * 查询片区内的党支部Id和Name
	 *
	 * @param sectionId
	 *            片区Id
	 * @return DeptIdNameDto
	 */
	@Override
	public List<DeptIdNameDto> getBranchDeptNameAndId(Long sectionId) {
		List<DeptIdNameDto> list = deptMapper.getBranchDeptIdAndName(sectionId);
		return list;
	}

	@Override
	public DjUser getDeptBranch(Long deptId) {
		if (deptId == null) {
			return null;
		}
		DjDept dept = deptMapper.selectByPrimaryKey(deptId);

		if (dept != null) {
			DjUser user = userMapper.selectByPrimaryKey(dept.getPrincipalId());
			return user;
		}

		return null;
	}

	@Override
	public List<DeptTreeVo> getDeptTree(Long sectionId) {
		List<DeptTreeVo> tree = deptMapper.getDeptTree(sectionId);
		return tree;
	}

	/**
	 * 新增党支部
	 *
	 * @param dto
	 * @param user
	 * @return
	 */
	@Transactional
	@Override
	public boolean addBranch(DeptAddOrUpdateDto dto, UserVo user) {
		DjBuilding building = buildingMapper.selectByPrimaryKey(dto.getBuildingId());
		if (building == null) {
			throw new BusinessException("所选的大楼不存在!", CoreConst.ResultCode.NotFound.getCode());
		}
		if (building.getDjSectionId() == null) {
			throw new BusinessException("大楼对应的片区不存在!", CoreConst.ResultCode.NotFound.getCode());
		}
		DjSection section = sectionMapper.selectByPrimaryKey(building.getDjSectionId());
		if (section == null) {
			throw new BusinessException("大楼对应的片区不存在!", CoreConst.ResultCode.NotFound.getCode());
		}
		// 当传入的上级党支部为0或者null，该支部为根支部，否则则为叶子支部(此时需要校验上级支部是否存在)
		if (dto.getParentId() != null && dto.getParentId() != 0) {
			DjDept parentDept = deptMapper.selectByPrimaryKey(dto.getParentId());
			if (parentDept == null) {
				throw new BusinessException("所选择的上级党支部不存在!", CoreConst.ResultCode.NotFound.getCode());
			}
//			if (!parentDept.getDjSectionId().equals(section.getId())) {
//				throw new BusinessException("所选的上级党支部不属于所选片区!", CoreConst.ResultCode.Forbidden.getCode());
//			}
		}

		DjDept dept = new DjDept();
		dept.setId(DigitUtil.generatorLongId());
		dept.setDjSectionId(section.getId());
		dept.setDjBuildingId(building.getId());
		dept.setFloor(dto.getFloor());
		dept.setName(dto.getName());
		dept.setAddress(dto.getAddress());
		dept.setPhone(dto.getPhone());
		dept.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
		dept.setParentId(dto.getParentId());
		dept.setBuildingCode(building.getCode());
		deptMapper.insert(dept);
		return true;
	}

	/**
	 * 修改党支部.
	 *
	 * @param id
	 *            党支部Id
	 * @return
	 */
	@Transactional
	@Override
	public boolean updateBranch(Long id, DeptAddOrUpdateDto dto, UserVo user) {
		DjDept dept = deptMapper.selectByPrimaryKey(id);
		if (dept == null) {
			throw new BusinessException("所选党支部不存在!", CoreConst.ResultCode.NotFound.getCode());
		}
		if (dept.getStatus() != DlbConstant.BASEDATA_STATUS_VALID) {
			throw new BusinessException("只有有效状态的党支部才能修改!", CoreConst.ResultCode.Forbidden.getCode());
		}
		DjBuilding building = buildingMapper.selectByPrimaryKey(dto.getBuildingId());
		if (building == null) {
			throw new BusinessException("所选的大楼不存在!", CoreConst.ResultCode.NotFound.getCode());
		}
		if (building.getDjSectionId() == null) {
			throw new BusinessException("大楼对应的片区不存在!", CoreConst.ResultCode.NotFound.getCode());
		}
		DjSection section = sectionMapper.selectByPrimaryKey(building.getDjSectionId());
		if (section == null) {
			throw new BusinessException("大楼对应的片区不存在!", CoreConst.ResultCode.NotFound.getCode());
		}
		// 当传入的上级党支部为0或者null，该支部为根支部，否则则为叶子支部(此时需要校验上级支部是否存在)
		if (dto.getParentId() != null && dto.getParentId() != 0) {
			DjDept parentDept = deptMapper.selectByPrimaryKey(dto.getParentId());
			if (parentDept == null) {
				throw new BusinessException("所选择的上级党支部不存在!", CoreConst.ResultCode.NotFound.getCode());
			}
			if (!parentDept.getDjSectionId().equals(section.getId())) {
				throw new BusinessException("所选的上级党支部不属于所选片区!", CoreConst.ResultCode.Forbidden.getCode());
			}
		}
		if (dto.getPrincipalId() != null) {
			DjUser newPrincipal = userMapper.selectByPrimaryKey(dto.getPrincipalId());

			if (newPrincipal == null) {
				throw new BusinessException("所选的支部书记不存在!", CoreConst.ResultCode.NotFound.getCode());
			}
			if (!newPrincipal.getDeptId().equals(dept.getId())) {
				throw new BusinessException("只有本支部的人才能被设为本支部的支部书记!", CoreConst.ResultCode.Forbidden.getCode());
			}
			if (dept.getPrincipalId() != null) {
				if (!dept.getPrincipalId().equals(dto.getPrincipalId())) {
					DjUser oldPrincipal = userMapper.selectByPrimaryKey(dept.getPrincipalId());
					// 修改原部门负责人的角色为普通党员
					// 修改新的部门负责人的角色为党支部书记
					oldPrincipal.setRoleId(RoleEnum.PARTY.getId());
					newPrincipal.setRoleId(RoleEnum.BRANCH_PARTY.getId());
					userMapper.updateByPrimaryKey(oldPrincipal);
					dept.setPrincipalName(newPrincipal.getUserName());
					dept.setPrincipalId(newPrincipal.getId());
				}
			} else {
				dept.setPrincipalName(newPrincipal.getUserName());
				dept.setPrincipalId(newPrincipal.getId());
				// 修改新的部门负责人的角色为党支部书记
				newPrincipal.setRoleId(RoleEnum.BRANCH_PARTY.getId());
			}
			userMapper.updateByPrimaryKey(newPrincipal);
		} else {
			// 清空支部书记
			if (dept.getPrincipalId() != null) {// 如果原来支部书记则需要将原支部书记的角色置为普通党员
				DjUser oldPrincipal = userMapper.selectByPrimaryKey(dept.getPrincipalId());
				if (oldPrincipal != null) {
					oldPrincipal.setRoleId(RoleEnum.PARTY.getId());
					userMapper.updateByPrimaryKey(oldPrincipal);
				}
			}
			dept.setPrincipalId(null);
			dept.setPrincipalName(null);
		}

		dept.setDjSectionId(section.getId());
		dept.setDjBuildingId(building.getId());
		dept.setFloor(dto.getFloor());
		dept.setName(dto.getName());
		dept.setAddress(dto.getAddress());
		dept.setPhone(dto.getPhone());
		dept.setParentId(dto.getParentId());
		dept.setBuildingCode(building.getCode());
		deptMapper.updateByPrimaryKey(dept);
		return true;
	}

	/**
	 * 作废党支部.
	 *
	 * @param id
	 * @param user
	 * @return
	 */
	@Transactional
	@Override
	public boolean invalidBranch(Long id, UserVo user) {
		DjDept dept = deptMapper.selectByPrimaryKey(id);
		if (dept == null) {
			throw new BusinessException("所选党支部不存在!", CoreConst.ResultCode.NotFound.getCode());
		}
		dept.setStatus(DlbConstant.BASEDATA_STATUS_DEL);
		deptMapper.updateByPrimaryKey(dept);
		return true;
	}

	@Override
	public DjDept getDeptInfoByName(String deptName) {
		if (StringUtils.isEmpty(deptName)) {
			return null;
		}
		deptName = deptName.trim();

		DjDept condition = new DjDept();
		condition.setName(deptName);

		List<DjDept> list = deptMapper.select(condition);
		if (list != null && list.size() > 0) {
			if (list.size() > 1) {
				logger.error(deptName + "->部门的数量:" + list.size());
			}
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查询党支部详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public DeptDetailVo getDetailBy(Long id) {
		DjDept dept = deptMapper.selectByPrimaryKey(id);
		if (dept == null) {
			return new DeptDetailVo();
		}
		DeptDetailVo vo = new DeptDetailVo();
		vo.setBuildingId(dept.getDjBuildingId());
		vo.setFloor(dept.getFloor());
		vo.setName(dept.getName());
		vo.setAddress(dept.getAddress());
		vo.setPhone(dept.getPhone());
		vo.setParentId(dept.getParentId());
		vo.setPrincipalId(dept.getPrincipalId());
		vo.setHonor(dept.getHonor());
		vo.setPeopleNum(dept.getPeopleNum());
		if (dept.getParentId() != null && dept.getParentId() > 0) {
			DjDept parentDept = deptMapper.selectByPrimaryKey(dept.getParentId());
			if (parentDept != null) {
				vo.setParentName(parentDept.getName());
			} else {
				vo.setParentName("-");
			}
		} else {
			vo.setParentName("-");
		}
		return vo;
	}

	/**
	 * 查询党支部和党员列表
	 *
	 * @param id
	 *            党支部Id
	 * @return
	 */
	@Override
	public DeptAndPartyMemberVo getDeptAndPartyMemberList(Long id) {
		DeptAndPartyMemberVo vo = deptMapper.getDeptNameAndPeopleSum(id);
		if (vo == null) {
			return new DeptAndPartyMemberVo();
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		List<AllPartyMemberVo> partyMembers = partymemberMapper.getPartyMembersVoByDeptId(id, year);
		vo.setPartyMembers(partyMembers);
		return vo;
	}

	/**
	 * 获取片区内的党支部列表和支部内是否有先锋作用申请
	 *
	 * @param sectionId
	 * @return
	 */
	@Override
	public List<DeptAndApplyInfoVo> getDeptListAndApplyInfo(Long sectionId) {
		List<DeptAndApplyInfoVo> list = deptMapper.getDeptListAndApplyInfo(sectionId);
		return list;
	}

	@Override
	public List<SelectTreeVo> getSectionAndDeptTree() {
		List<SelectTreeVo> rlist = new ArrayList<SelectTreeVo>();

		List<DjSection> sectionList = sectionMapper.selectAll();
		if (sectionList != null) {
			SelectTreeVo vo = null;
			for (DjSection section : sectionList) {
				vo = new SelectTreeVo("s" + section.getId(), section.getName(), "0");
				vo.setChildren(getDeptBySectionId(section.getId()));
				rlist.add(vo);
			}
		}
		return rlist;
	}

	private List<SelectTreeVo> getDeptBySectionId(Long sectionId) {
		List<SelectTreeVo> rlist = new ArrayList<>();
		List<DeptIdNameDto> list = getBranchDeptNameAndId(sectionId);
		if (list != null) {
			for (DeptIdNameDto dto : list) {
				rlist.add(new SelectTreeVo("" + dto.getValue(), dto.getName(), "s" + sectionId));
			}
		}

		return rlist;
	}
}
