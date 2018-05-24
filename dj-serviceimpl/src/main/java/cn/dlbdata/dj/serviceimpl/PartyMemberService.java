package cn.dlbdata.dj.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.dlbdata.dj.common.core.exception.DlbException;
import cn.dlbdata.dj.common.core.util.DatetimeUtil;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.constant.ActiveSubTypeEnum;
import cn.dlbdata.dj.constant.ActiveTypeEnum;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.dto.active.ReportAddScoreRequest;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.vo.party.ReportPartyMemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.db.mapper.DjActiveMapper;
import cn.dlbdata.dj.db.mapper.DjPartymemberMapper;
import cn.dlbdata.dj.db.mapper.DjScoreMapper;
import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.pojo.DjScore;
import cn.dlbdata.dj.service.IPartyMemberService;
import cn.dlbdata.dj.serviceimpl.base.BaseService;
import cn.dlbdata.dj.vo.PartyVo;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PartyMemberService extends BaseService implements IPartyMemberService {
	@Autowired
	private DjPartymemberMapper partyMemberMapper;

	@Autowired
	private DjScoreMapper scoreMapper;

	@Autowired
	private DjPartymemberMapper partymemberMapper;
	
	@Autowired
	private DjActiveMapper activeMapper;

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
		
		Date startTime = DatetimeUtil.getCurrYearFirst();
		Date endTime = DatetimeUtil.getCurrYearLast();
		//获取参与活动次数
		int activeCount = activeMapper.getUserActiveCountByActiveTypeAndTime(memberId, null, startTime, endTime);
		//获取参与金领驿站活动次数
		int jlyzCount = activeMapper.getUserActiveCountByActiveTypeAndTime(memberId, ActiveSubTypeEnum.ACTIVE_SUB_E.getActiveSubId(), startTime, endTime);
		result.setJlyzActiveNum(jlyzCount);
		result.setActiveNum(activeCount);
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
	public List<ReportPartyMemberVo> getReportPartyMember(long deptId, long subTypeId) {
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
			vo.setStatus(DlbConstant.AUDIT_STATUS_NO.getValue());
			voList.add(vo);
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

	/**
	 *思想汇报直接加分
	 * @param request 请求Data
	 */
	@Transactional
	@Override
	public void reportAddScore(ReportAddScoreRequest request, int year, UserVo userVo) {
		if (request.getId() == null ||
				request.getSubTypeId() == null || request.getReportTime() == null ||
				"".equals(request.getReportTime())) {
			throw new DlbException("请求参数不完整");
		}
		DjPartymember partymember = partymemberMapper.selectByPrimaryKey(request.getId());
		if (partymember == null) {
			throw new DlbException("该党员不存在");
		}
		// TODO 校验当前登录用户做该操作的权限
		//查询是否有思想汇报记录
		List<DjScore> scoreIds = scoreMapper.getScoreIdsByTypeIdAndSubTypeIdAndYearAndPartyMemberId(
				ActiveTypeEnum.ACTIVE_C.getActiveId(),request.getSubTypeId(),year,request.getId());
		if (scoreIds.size()>=1) {
			throw new DlbException("请勿重复加分!");
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date reportDate = null;
		try {
			reportDate = formatter.parse(request.getReportTime());
		}catch (ParseException e) {
			e.printStackTrace();
		}
		DjScore djScore = new DjScore();
		djScore.setId(DigitUtil.generatorLongId());
		djScore.setDjTypeId(ActiveTypeEnum.ACTIVE_C.getActiveId());
		djScore.setDjSubTypeId(request.getSubTypeId());
		djScore.setScore(new Float(5));
		djScore.setUserId(request.getId());
		djScore.setAddTime(reportDate);
		//TODO 目前User的信息获取不到，先写成1
		djScore.setApplyUserId(1L);
		djScore.setApproverId(1L);
		djScore.setAddYear(year);
		djScore.setStatus(DlbConstant.BASEDATA_STATUS_VALID.getValue());
		djScore.setCreateTime(new Date());
		scoreMapper.insert(djScore);
	}
}
