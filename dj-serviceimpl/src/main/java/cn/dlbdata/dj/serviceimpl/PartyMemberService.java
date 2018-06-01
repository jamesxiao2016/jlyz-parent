package cn.dlbdata.dj.serviceimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.dlbdata.dj.db.mapper.*;
import cn.dlbdata.dj.db.pojo.DjPicRecord;
import cn.dlbdata.dj.db.vo.party.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.dlbdata.dj.common.core.util.DatetimeUtil;
import cn.dlbdata.dj.common.core.util.PageUtils;
import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.ActiveSubTypeEnum;
import cn.dlbdata.dj.constant.ActiveTypeEnum;
import cn.dlbdata.dj.constant.AuditStatusEnum;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.db.vo.apply.ScoreTypeVo;
import cn.dlbdata.dj.db.vo.score.ScoreVo;
import cn.dlbdata.dj.service.IPartyMemberService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.PartyVo;

@Service
public class PartyMemberService extends BaseServiceImpl implements IPartyMemberService {
	@Autowired
	private DjPartymemberMapper partyMemberMapper;
	@Autowired
	private DjScoreMapper scoreMapper;
	@Autowired
	private DjActiveMapper activeMapper;
	@Autowired
	private DjThoughtsMapper thoughtsMapper;
	@Autowired
	private DjDisciplineMapper disciplineMapper;
	@Autowired
	private DjApplyMapper applyMapper;
	@Autowired
	private DjStudyMapper studyMapper;
	@Autowired
	private DjPicRecordMapper picRecordMapper;

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
	public PartyVo getScoreAndNumByMemberId(Long memberId, Integer year) {
		PartyVo result = new PartyVo();
		result.setMemberId(memberId);

		Date startTime = DatetimeUtil.getYearFirst(year);
		Date endTime = DatetimeUtil.getYearLast(year);
		// 获取参与活动次数
		int activeCount = activeMapper.getUserActiveCountByActiveTypeAndTime(memberId, null, startTime, endTime);
		// 获取参与金领驿站活动次数
		int jlyzCount = activeMapper.getUserActiveCountByActiveTypeAndTime(memberId,
				ActiveSubTypeEnum.ACTIVE_SUB_E.getActiveSubId(), startTime, endTime);
		result.setJlyzActiveNum(jlyzCount);
		result.setActiveNum(activeCount);
		return result;
	}

	@Override
	public List<ScoreVo> getScoreListByUserId(Long userId, Integer year) {
		if (userId == null) {
			return null;
		}
		return scoreMapper.getScoreListByUserId(userId, year);
	}

	@Override
	public List<ScoreTypeVo> getTypeScoreListByUserId(Long userId, Integer year) {
		if (userId == null) {
			return null;
		}
		List<ScoreTypeVo> list = scoreMapper.getTypeScoreListByUserId(userId, year);
		if (list != null) {
			for (ScoreTypeVo vo : list) {
				if (vo.getId() == ActiveTypeEnum.ACTIVE_A.getActiveId()
						|| vo.getId() == ActiveTypeEnum.ACTIVE_B.getActiveId()
						|| vo.getId() == ActiveTypeEnum.ACTIVE_F.getActiveId()) {
					DjStudy record = new DjStudy();
					record.setDjTypeId(vo.getId());
					record.setStatus(DlbConstant.BASEDATA_STATUS_INVALID);
					record.setCreateUserId(userId);
					int count = studyMapper.selectCount(record);
					vo.setPendingNum(count);
				} else if (vo.getId() == ActiveTypeEnum.ACTIVE_G.getActiveId()) {
					vo.setPendingNum(0);
				} else {
					vo.setPendingNum(-1);
				}
			}
		}
		return list;
	}

	@Override
	public Paged<ReportPartyMemberVo> getReportPartyMember(long deptId, long subTypeId, int pageNum, int pageSize) {
		if (subTypeId != ActiveSubTypeEnum.ACTIVE_SUB_K.getActiveSubId()
				&& subTypeId != ActiveSubTypeEnum.ACTIVE_SUB_L.getActiveSubId()) {
			return new Paged<>();
		}
		pageNum = PageUtils.normalizePageIndex(pageNum);
		pageSize = PageUtils.normalizePageSize(pageSize);
		Date yearTimeStart = DatetimeUtil.getCurrYearFirst();
		Date yearTimeEnd = DatetimeUtil.getCurrYearLast();
		Page<ReportPartyMemberVo> page = PageHelper.startPage(pageNum, pageSize);
		List<ReportPartyMemberVo> voList = partyMemberMapper.getReportPartyMembers(deptId);
		for (ReportPartyMemberVo vo : voList) {
			vo.setSubTypeId(subTypeId);
		}
		if (!voList.isEmpty()) {
			// 查询有积分记录的用户ID
			List<Long> userIdList = thoughtsMapper.getByDeptIdAndTypeIdAndSubTypeIdAndYear(deptId, subTypeId,
					yearTimeStart, yearTimeEnd);
			for (int i = 0; i < userIdList.size(); i++) {
				for (int j = 0; j < voList.size(); j++) {
					if (voList.get(j).getId().equals(userIdList.get(i))) {
						voList.get(j).setStatus(AuditStatusEnum.PASS.getValue());
					}
				}
			}
		}
		return PageUtils.toPaged(page);
	}

	/**
	 * 思想汇报详情
	 *
	 * @param id        党员ID
	 * @param subTypeId 活动二级分类ID
	 * @return
	 */
	@Override
	public ReportDetailVo getReportDetail(Long id, Long subTypeId) {
		if (!subTypeId.equals(ActiveSubTypeEnum.ACTIVE_SUB_K.getActiveSubId()) &&
				!subTypeId.equals(ActiveSubTypeEnum.ACTIVE_SUB_L.getActiveSubId())) {
			return new ReportDetailVo();
		}
		Date yearTimeStart = DatetimeUtil.getCurrYearFirst();
		Date yearTimeEnd = DatetimeUtil.getCurrYearLast();
		List<ReportDetailVo> vos = thoughtsMapper.getReportDetail(id,subTypeId,yearTimeStart,yearTimeEnd);

		if (vos.size()>0) {
			ReportDetailVo detailVo = vos.get(0);

			List<Long> picIds = picRecordMapper.getIdsByTableNameAndRecordId(DlbConstant.TABLE_NAME_THOUGHTS,detailVo.getId());
			detailVo.setPicIds(picIds);
			return detailVo;
		} else {
			return new ReportDetailVo();
		}
	}

	/**
	 * 思想汇报直接加分
	 * 
	 * @param request
	 *            请求Data
	 */
	// @Transactional
	// @Override
	// public void reportAddScore(ReportAddScoreRequest request, int year, UserVo userVo) {
	// if (request.getId() == null || request.getSubTypeId() == null || request.getReportTime() == null
	// || "".equals(request.getReportTime())) {
	// throw new DlbException("请求参数不完整");
	// }
	// DjPartymember partymember = partymemberMapper.selectByPrimaryKey(request.getId());
	// if (partymember == null) {
	// throw new DlbException("该党员不存在");
	// }
	// // TODO校验当前登录用户做该操作的权限
	// // 查询是否有思想汇报记录
	// List<DjScore> scoreIds = scoreMapper.getScoreIdsByTypeIdAndSubTypeIdAndYearAndPartyMemberId(
	// ActiveTypeEnum.ACTIVE_C.getActiveId(), request.getSubTypeId(), year, request.getId());
	// if (scoreIds.size() >= 1) {
	// throw new DlbException("请勿重复加分!");
	// }
	// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// Date reportDate = null;
	// try {
	// reportDate = formatter.parse(request.getReportTime());
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// // TODO图片ID待设置
	// DjThoughts djThoughts = new DjThoughts();
	// djThoughts.setId(DigitUtil.generatorLongId());
	// djThoughts.setThoughtsInfo(request.getContent());
	// djThoughts.setThoughtsTime(reportDate);
	// djThoughts.setScore(new Float(5));
	// djThoughts.setDjUserId(request.getId());
	// djThoughts.setCreateTime(new Date());
	// djThoughts.setDjDeptId(request.getDeptId());
	// djThoughts.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
	// thoughtsMapper.insert(djThoughts);
	//
	// todo后面需改为batchInsert
	// for (Long pid : request.getPicIds()) {
	// DjPicRecord picRecord = new DjPicRecord();
	// picRecord.setId(DigitUtil.generatorLongId());
	// picRecord.setTableName("dj_thoughts");
	// picRecord.setRecordId(djThoughts.getId());
	// picRecord.setDjPicId(pid);
	// picRecord.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
	// picRecord.setCreateTime(new Date());
	// picRecordMapper.insert(picRecord);
	// }
	//
	// DjScore djScore = new DjScore();
	// djScore.setId(DigitUtil.generatorLongId());
	// djScore.setDjTypeId(ActiveTypeEnum.ACTIVE_C.getActiveId());
	// djScore.setDjSubTypeId(request.getSubTypeId());
	// djScore.setScore(new Float(5));
	// djScore.setUserId(request.getId());
	// djScore.setAddTime(reportDate);
	// //TODO目前User的信息获取不到，先写成1
	// djScore.setApplyUserId(1L);
	// djScore.setApproverId(1L);
	// djScore.setAddYear(year);
	// djScore.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
	// djScore.setCreateTime(new Date());
	// djScore.setRecordId(djThoughts.getId());
	// scoreMapper.insert(djScore);
	// }

	/**
	 * 先锋作用评分党员列表
	 * 
	 * @param deptId
	 * @return
	 */
	@Override
	public Paged<PioneeringPartyMemberVo> getPioneeringPartyMembers(Long deptId, int pageNum, int pageSize) {
		pageNum = PageUtils.normalizePageIndex(pageNum);
		pageSize = PageUtils.normalizePageSize(pageSize);
		// 获取支部全部党员
		Page<PioneeringPartyMemberVo> page = PageHelper.startPage(pageNum, pageSize);
		List<PioneeringPartyMemberVo> voList = partyMemberMapper.getPioneeringPartyMembers(deptId);
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		for (PioneeringPartyMemberVo vo : voList) {

			// 1.查询党员是否有先锋作用的审核申请,没有就说明该党员为未处理（去处理）（typeId =4），否则走2
			int haveApply = applyMapper.countUnAuditByPtMemberIdAndType(vo.getId(),
					ActiveTypeEnum.ACTIVE_D.getActiveId(), year);
			if (haveApply == 0) {
				// 未审核
				vo.setAuditStatus(AuditStatusEnum.UNDONE.getValue());
			} else {
				// 2.判断该党员有待审核的记录（status =0），则该党员为待审核，否则该党员为已审核
				int haveWaitApply = applyMapper.countByPtMemberIdStatus(vo.getId(), AuditStatusEnum.WAITING.getValue(),
						ActiveTypeEnum.ACTIVE_D.getActiveId(), year);
				if (haveWaitApply > 0) {
					vo.setAuditStatus(AuditStatusEnum.WAITING.getValue());
				} else {
					vo.setAuditStatus(AuditStatusEnum.PASS.getValue());
				}
			}
		}
		return PageUtils.toPaged(page);
	}

	/*
	 * (non-Javadoc) <p>Title: queryAllPartyMembersByDeptId</p> <p>Description: 查询支部全部党员的信息以及分数</p>
	 * 
	 * @param deptId
	 * 
	 * @return
	 * 
	 * @see cn.dlbdata.dj.service.IPartyMemberService#queryAllPartyMembersByDeptId(java.lang.Long)
	 */
	@Override
	public List<DjPartymember> queryAllPartyMembersByDeptId(Long deptId) {
		if (deptId == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("deptId", deptId);
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		map.put("year", year);
		return partyMemberMapper.queryAllPartyMembersByDeptId(map);
	}

	/**
	 * 违章守纪评分党员列表
	 *
	 * @param deptId
	 *            支部Id
	 * @return
	 */
	@Override
	public Paged<ObserveLowPartyMemberVo> getObserveLowPartyMember(Long deptId, int pageNum, int pageSize) {
		Calendar cale = Calendar.getInstance();
		pageNum = PageUtils.normalizePageIndex(pageNum);
		pageSize = PageUtils.normalizePageSize(pageSize);
		Date yearTimeStart = DatetimeUtil.getCurrYearFirst();
		Date yearTimeEnd = DatetimeUtil.getCurrYearLast();
		int year = cale.get(Calendar.YEAR);
		Page<ObserveLowPartyMemberVo> page = PageHelper.startPage(pageNum, pageSize);
		List<ObserveLowPartyMemberVo> voList = partyMemberMapper.getObserveLowPartyMember(deptId, year);
		for (ObserveLowPartyMemberVo vo : voList) {
			// 取最后一条遵章守纪记录 没有记录则说明该党员未处理，有记录则取最后一条记录的状态
			Integer status = disciplineMapper.getOneByUserIdOrderByCreateTimeDesc(vo.getId(), yearTimeStart,
					yearTimeEnd);
			if (status == null) {
				vo.setStatus(-1);
			} else {
				vo.setStatus(status);
			}
		}
		return PageUtils.toPaged(page);
	}

	/**
	 * 遵章守纪详情
	 *
	 * @param applyId 申请Id
	 * @return
	 */
	@Override
	public ObserveLowDetailVo getObserveLowDetail(Long applyId) {
		ObserveLowDetailVo vo = applyMapper.getObserveLowDetail(applyId);
		if (vo.getDisId() != null) {
			List<Long> picIds = picRecordMapper.getIdsByTableNameAndRecordId(DlbConstant.TABLE_NAME_DISCIPLINE,vo.getDisId());
			vo.setPicIds(picIds);
		}
		return vo;
	}

	/* (non-Javadoc)
	 * <p>Title: getSumScoreByIdCard</p>
	 * <p>Description: 根据身份证查询总积分</p> 
	 * @param idCard
	 * @return  
	 * @see cn.dlbdata.dj.service.IPartyMemberService#getSumScoreByIdCard(java.lang.String)
	 */
	@Override
	public ResultVo<Float> getSumScoreByIdCard(String idCard) {
		ResultVo<Float> result = new ResultVo<>();
		if(idCard == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("获取总积分失败");
			return result;
		}
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idCard", idCard);
		map.put("year", year);
		Float score = partyMemberMapper.getSumScoreByIdCard(map);
		result.setData(score);
		return result;
	}
}
