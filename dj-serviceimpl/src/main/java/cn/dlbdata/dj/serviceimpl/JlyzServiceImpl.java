package cn.dlbdata.dj.serviceimpl;

import java.util.List;

import cn.dlbdata.dj.db.vo.jlyz.BuildingVo;
import cn.dlbdata.dj.db.vo.jlyz.MemberStatVo;
import cn.dlbdata.dj.db.vo.jlyz.PartyBranchVo;
import cn.dlbdata.dj.db.vo.jlyz.SectionVo;
import cn.dlbdata.dj.service.IJlyzService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;

public class JlyzServiceImpl extends BaseServiceImpl implements IJlyzService {

	@Override
	public SectionVo querySection(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildingVo queryBuilding(String buildingCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PartyBranchVo> queryPartyBranches(String buildingCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberStatVo queryMemberStatistic(String buildingCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
