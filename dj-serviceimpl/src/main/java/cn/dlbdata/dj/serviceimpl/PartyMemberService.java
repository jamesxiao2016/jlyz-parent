package cn.dlbdata.dj.serviceimpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.dlbdata.dj.common.core.exception.DlbException;
import cn.dlbdata.dj.constant.ActiveSubTypeEnum;
import cn.dlbdata.dj.constant.ActiveTypeEnum;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.vo.party.ReportPartyMemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.db.mapper.DjPartymemberMapper;
import cn.dlbdata.dj.db.mapper.DjScoreMapper;
import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.pojo.DjScore;
import cn.dlbdata.dj.service.IPartyMemberService;
import cn.dlbdata.dj.serviceimpl.base.BaseService;
import cn.dlbdata.dj.vo.PartyVo;


@Service
public class PartyMemberService extends BaseService implements IPartyMemberService {
	@Autowired
	private DjPartymemberMapper partyMemberMapper;

	@Autowired
	private DjScoreMapper scoreMapper;

	@Override
	public DjPartymember getInfoById(Long id) {
		if (id == null) {
			return null;
		}
		return partyMemberMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<DjPartymember> getPartyMemberListByDeptId(Long deptId) {
		if (deptId == null) {
			return null;
		}
		DjPartymember member = new DjPartymember();
		member.setDeptId(deptId);
		return partyMemberMapper.select(member);
	}

	@Override
	public PartyVo getScoreAndNumByMemberId(Long memberId) {
		PartyVo result = new PartyVo();
		result.setMemberId(memberId);
		return result;
	}

	@Override
	public List<DjScore> getScoreListByUserId(Long userId, Integer year) {
		if (userId == null) {
			return null;
		}
		return scoreMapper.getScoreListByUserId(userId, year);
	}

	@Override
	public List<DjScore> getTypeScoreListByUserId(Long userId, Integer year) {
		if (userId == null) {
			return null;
		}
		return scoreMapper.getTypeScoreListByUserId(userId, year);
	}

	@Override
	public List<ReportPartyMemberVo> getReportPartyMember(long deptId, int subTypeId) {
		if(subTypeId != ActiveSubTypeEnum.ACTIVE_SUB_K.getActiveSubId() &&
				subTypeId != ActiveSubTypeEnum.ACTIVE_SUB_L.getActiveSubId()) {
			throw new DlbException("subTypeId不合法!");
		}
		Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR);
		System.out.println(year);
		List<DjPartymember> pojoList = partyMemberMapper.getReportPartyMember(deptId);
		List<ReportPartyMemberVo> voList = new ArrayList<>();
		for (DjPartymember pojo :pojoList) {
			ReportPartyMemberVo vo = new ReportPartyMemberVo();
			vo.setId(pojo.getId());
			vo.setName(pojo.getName());
			vo.setSubTypeId(subTypeId);
			vo.setTypeId(ActiveTypeEnum.ACTIVE_C.getActiveId());
			vo.setStatus(DlbConstant.AUDIT_STATUS_NO.getValue());
		}
		if (!voList.isEmpty()) {
			//查询有积分记录的用户ID
			List<Long> userIdList = scoreMapper.getByDeptIdAndTypeIdAndSubTypeIdAndYear(deptId,
					ActiveTypeEnum.ACTIVE_C.getActiveId(),subTypeId,year);
			for (int i = 0;i<userIdList.size();i++) {
				for (int j = 0;j<voList.size();j++) {
					if (voList.get(j).getId().equals(userIdList.get(i))) {
						voList.get(j).setStatus(DlbConstant.AUDIT_STATUS_YES.getValue());
					}
				}
			}
		}
		return voList;
	}
}
