package cn.dlbdata.dj.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.dlbdata.dj.db.mapper.DjBuildingMapper;
import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.mapper.DjSectionMapper;
import cn.dlbdata.dj.db.mapper.TStatisticMapper;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.db.vo.jlyz.BuildingVo;
import cn.dlbdata.dj.db.vo.jlyz.MemberStatVo;
import cn.dlbdata.dj.db.vo.jlyz.PartyBranchVo;
import cn.dlbdata.dj.db.vo.jlyz.SectionVo;
import cn.dlbdata.dj.service.IJlyzService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;

public class JlyzServiceImpl extends BaseServiceImpl implements IJlyzService {
	@Autowired
	private DjSectionMapper sectionMapper;
	@Autowired
	private DjBuildingMapper buildingMapper;
	@Autowired
	private DjDeptMapper deptMapper;
	@Autowired
	private TStatisticMapper statisticMapper;

	@Override
	public SectionVo querySection(Long id) {
		SectionVo result = new SectionVo();
		if (id == null) {
			return result;
		}

		// 查询片区信息
		DjSection section = sectionMapper.selectByPrimaryKey(id);
		if (section == null) {
			return result;
		}
		// 查询片区负责人信息

		// 查询楼宇列表

		return result;
	}

	@Override
	public List<PartyBranchVo> queryPartyBranches(String buildingCode) {
		List<PartyBranchVo> result = new ArrayList();
		if (StringUtils.isEmpty(buildingCode)) {
			return null;
		}
		// 查询楼宇的党支部信息

		return result;
	}

	@Override
	public BuildingVo queryBuilding(String buildingCode) {
		BuildingVo result = new BuildingVo();
		if (StringUtils.isEmpty(buildingCode)) {
			return result;
		}

		return result;
	}

	@Override
	public MemberStatVo queryMemberStatistic(String buildingCode) {
		MemberStatVo result = new MemberStatVo();
		if (StringUtils.isEmpty(buildingCode)) {
			return result;
		}
		return result;
	}

}
