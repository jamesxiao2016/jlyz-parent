package cn.dlbdata.dj.serviceimpl;

import java.util.*;

import cn.dlbdata.dj.db.dto.IdNameDto;
import cn.dlbdata.dj.db.mapper.*;
import cn.dlbdata.dj.db.pojo.*;
import cn.dlbdata.dj.db.vo.building.SelectBuildingVo;
import cn.dlbdata.dj.db.vo.party.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.dlbdata.dj.common.core.exception.BusinessException;
import cn.dlbdata.dj.common.core.util.DatetimeUtil;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.PageUtils;
import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.common.core.web.vo.SelectResultVo;
import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.common.util.StringUtil;
import cn.dlbdata.dj.constant.ActiveSubTypeEnum;
import cn.dlbdata.dj.constant.ActiveTypeEnum;
import cn.dlbdata.dj.constant.AuditStatusEnum;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.constant.RoleEnum;
import cn.dlbdata.dj.db.dto.partymember.PartyMemberAddOrUpdateDto;
import cn.dlbdata.dj.db.vo.DjPartyMemberVo;
import cn.dlbdata.dj.db.vo.apply.ScoreTypeVo;
import cn.dlbdata.dj.db.vo.score.ScoreVo;
import cn.dlbdata.dj.service.IDeptService;
import cn.dlbdata.dj.service.IPartyMemberService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.PartyVo;
import cn.dlbdata.dj.vo.UserVo;

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
	@Autowired
	private DjUserMapper userMapper;
	@Autowired
	private IDeptService deptService;
	@Autowired
    private DjDeptMapper deptMapper;
	@Autowired
    private DjSectionMapper sectionMapper;

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
					int count = studyMapper.getPendingCount(vo.getId(), userId);
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
		String timeStr;
		if (subTypeId == ActiveSubTypeEnum.ACTIVE_SUB_K.getActiveSubId()) {
			timeStr = "上半年";
		} else {
			timeStr = "下半年";
		}
		for (ReportPartyMemberVo vo : voList) {
			vo.setSubTypeId(subTypeId);
			vo.setTime(timeStr);
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
	 * @param id
	 *            党员ID
	 * @param subTypeId
	 *            活动二级分类ID
	 * @return
	 */
	@Override
	public ReportDetailVo getReportDetail(Long id, Long subTypeId) {
		if (!subTypeId.equals(ActiveSubTypeEnum.ACTIVE_SUB_K.getActiveSubId())
				&& !subTypeId.equals(ActiveSubTypeEnum.ACTIVE_SUB_L.getActiveSubId())) {
			return new ReportDetailVo();
		}
		Date yearTimeStart = DatetimeUtil.getCurrYearFirst();
		Date yearTimeEnd = DatetimeUtil.getCurrYearLast();
		List<ReportDetailVo> vos = thoughtsMapper.getReportDetail(id, subTypeId, yearTimeStart, yearTimeEnd);

		if (vos.size() > 0) {
			ReportDetailVo detailVo = vos.get(0);

			List<Long> picIds = picRecordMapper.getIdsByTableNameAndRecordId(DlbConstant.TABLE_NAME_THOUGHTS,
					detailVo.getId());
			detailVo.setPicIds(picIds);
			return detailVo;
		} else {
			return new ReportDetailVo();
		}
	}

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
	public List<AllPartyMemberVo> queryAllPartyMembersByDeptId(Long deptId) {
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
	 * 遵章守纪详情 支部书记使用
	 *
	 * @param partyMemberId
	 *            党员Id
	 * @return
	 */
	@Override
	public ObserveLowDetailVo getObserveLowDetailForDept(Long partyMemberId) {
		Date yearTimeStart = DatetimeUtil.getCurrYearFirst();
		Date yearTimeEnd = DatetimeUtil.getCurrYearLast();
		ObserveLowDetailVo vo = disciplineMapper.getByPartyMemberId(partyMemberId, yearTimeStart, yearTimeEnd);
		if (vo != null) {
			if (vo.getDisId() != null) {
				List<Long> picIds = picRecordMapper.getIdsByTableNameAndRecordId(DlbConstant.TABLE_NAME_DISCIPLINE,
						vo.getDisId());
				vo.setPicIds(picIds);
			}
		} else {// 即使查不出数据也应当返回正确的结构
			vo = new ObserveLowDetailVo();
		}
		return vo;
	}

	/*
	 * (non-Javadoc) <p>Title: getSumScoreByIdCard</p> <p>Description: 根据身份证查询总积分</p>
	 * 
	 * @param idCard
	 * 
	 * @return
	 * 
	 * @see cn.dlbdata.dj.service.IPartyMemberService#getSumScoreByIdCard(java.lang.String)
	 */
	@Override
	public ResultVo<Float> getSumScoreByIdCard(String idCard) {
		ResultVo<Float> result = new ResultVo<>();
		if (idCard == null) {
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

	/**
	 * 获取党员年度活动信息
	 *
	 * @param user
	 * @return
	 */
	@Override
	public AnnualActiveInfo getAnnualActiveInfo(UserVo user, Integer year) {
		AnnualActiveInfo result = new AnnualActiveInfo();

		Date startTime = DatetimeUtil.getYearFirst(year);
		Date endTime = DatetimeUtil.getYearLast(year);
		// 获取参与活动次数
		int activeCount = activeMapper.getUserActiveCountByActiveTypeAndTime(user.getUserId(), null, startTime,
				endTime);
		// 获取参与金领驿站活动次数
		int jlyzCount = activeMapper.getUserActiveCountByActiveTypeAndTime(user.getUserId(),
				ActiveSubTypeEnum.ACTIVE_SUB_E.getActiveSubId(), startTime, endTime);
		Float totalScore = scoreMapper.getSumScoreByUserId(user.getUserId(), year);
		result.setScore(totalScore);
		result.setJlyzActiveNum(jlyzCount);
		result.setActiveNum(activeCount);
		return result;
	}

	/*
	 * (non-Javadoc) <p>Title: selectPartymemberByDeptId</p> <p>Description: 提供给外部使用的部门党员信息</p>
	 * 
	 * @param departmentid
	 * 
	 * @return
	 * 
	 * @see cn.dlbdata.dj.service.IPartyMemberService#selectPartymemberByDeptId(java.lang.Integer)
	 */
	@Override
	public List<DjPartyMemberVo> selectPartymemberByDeptId(Long deptId, String deptName) {
		// 参数检查，2个参数必须传一个
		if (deptId == null && StringUtils.isEmpty(deptName)) {
			return Collections.emptyList();
		}

		// 获取部门信息
		DjDept dept = deptService.getDeptInfoById(deptId);
		if (StringUtils.isNotEmpty(deptName)) {
			dept = deptService.getDeptInfoByName(deptName);
		}

		// 无部门信息
		if (dept == null) {
			return Collections.emptyList();
		}

		return partyMemberMapper.selectPartymemberByDeptId(dept.getId());
	}

	/*
	 * (non-Javadoc) <p>Title: getRadarChartByUserId</p> <p>Description: </p>
	 * 
	 * @param userId
	 * 
	 * @param year
	 * 
	 * @return
	 * 
	 * @see cn.dlbdata.dj.service.IPartyMemberService#getRadarChartByUserId(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public List<ScoreTypeVo> getRadarChartByUserId(Long userId, Integer year) {
		if (userId == null) {
			return null;
		}
		List<ScoreTypeVo> list = scoreMapper.getRadarChartByUserId(userId, year);
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

	/**
	 * 新增党员账号
	 *
	 * @param dto
	 * @param user
	 */
	@Transactional
	@Override
	public void addPartyMember(PartyMemberAddOrUpdateDto dto, UserVo user) {
		boolean exist = userMapper.existWithUserName(dto.getUserName(), null);
		if (exist) {
			throw new BusinessException("该用户名已存在!", ResultCode.Forbidden.getCode());
		}
		if (dto.getIdcard() == null || "".equals(dto.getIdcard())) {
			throw new BusinessException("身份证编号为必填项!", ResultCode.Forbidden.getCode());
		}
		//校验身份证唯一性
		boolean existWithIdCard = partyMemberMapper.existWithIdCard(dto.getIdcard(),null);
		if (existWithIdCard) {
			throw new BusinessException("系统中已存在该身份证党员!", ResultCode.Forbidden.getCode());
		}

		DjUser newUser = new DjUser();
		newUser.setId(DigitUtil.generatorLongId());
		newUser.setDjPartymemberId(newUser.getId());
		newUser.setName(dto.getUserName());
		newUser.setPwd(StringUtil.getMD5Digest32("12345678"));
		newUser.setRoleId(RoleEnum.PARTY.getId());
		newUser.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
		newUser.setDeptId(dto.getDeptId());
		newUser.setCreateTime(new Date());
		newUser.setUserName(dto.getName());
		userMapper.insert(newUser);

		DjPartymember partymember = new DjPartymember();

		partymember.setName(dto.getName());
		partymember.setSexCode(dto.getSexCode());
		partymember.setPhone(dto.getPhone());
		partymember.setEmail(dto.getEmail());
		partymember.setIdcard(dto.getIdcard());
		partymember.setDeptId(dto.getDeptId());
		partymember.setEducationCode(dto.getEducationCode());
		partymember.setPartyPostCode(dto.getPartyPostCode());
		partymember.setId(newUser.getId());
		partymember.setBirthDate(DatetimeUtil.getDateByStr(dto.getBirthDate(), null));
		partymember.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
		partyMemberMapper.insert(partymember);
        //基础分
        int year = Calendar.getInstance().get(Calendar.YEAR);
        DjScore score = new DjScore();
        score.setId(DigitUtil.generatorLongId());
        score.setDjTypeId(ActiveTypeEnum.ACTIVE_E.getActiveId());
        score.setDjSubTypeId(ActiveSubTypeEnum.ACTIVE_SUB_P.getActiveSubId());
        score.setScore(20F);
        score.setUserId(partymember.getId());
        score.setAddTime(new Date());
        score.setApplyUserId(null);
        score.setApplyUserName("系统自动");
        score.setApproverId(null);
        score.setApproverName("系统自动");
        score.setAddYear(year);
        score.setAddStatus(1);
        score.setScoreDesc("遵纪守法基础积分");
        score.setCreateTime(new Date());
        scoreMapper.insert(score);
	}

	/**
	 * 更新党员信息
	 *
	 * @param id
	 * @param dto
	 * @param userVo
	 * @return
	 */
	@Transactional
	@Override
	public boolean updatePartyMember(Long id, PartyMemberAddOrUpdateDto dto, UserVo userVo) {
		DjPartymember partymember = partyMemberMapper.selectByPrimaryKey(id);

		DjUser user = userMapper.selectByPrimaryKey(id);
		if (partymember == null || user == null) {
			throw new BusinessException("该党员不存在!", ResultCode.NotFound.getCode());
		}
        Long oldDeptId = partymember.getDeptId();
		boolean exist = userMapper.existWithUserName(dto.getUserName(), id);
		if (exist) {
			throw new BusinessException("该用户名已存在!", ResultCode.Forbidden.getCode());
		}
		if (dto.getIdcard() == null || "".equals(dto.getIdcard())) {
			throw new BusinessException("身份证编号为必填项!", ResultCode.Forbidden.getCode());
		}
		//校验身份证唯一性
		boolean existWithIdCard = partyMemberMapper.existWithIdCard(dto.getIdcard(),id);
		if (existWithIdCard) {
			throw new BusinessException("系统中已存在该身份证的其他党员!", ResultCode.Forbidden.getCode());
		}
        user.setName(dto.getUserName());
        user.setDeptId(dto.getDeptId());
        user.setUserName(dto.getName());


		partymember.setName(dto.getName());
		partymember.setSexCode(dto.getSexCode());
		partymember.setPhone(dto.getPhone());
		partymember.setEmail(dto.getEmail());
		partymember.setIdcard(dto.getIdcard());
		partymember.setDeptId(dto.getDeptId());
		partymember.setEducationCode(dto.getEducationCode());
		partymember.setPartyPostCode(dto.getPartyPostCode());

		partymember.setBirthDate(DatetimeUtil.getDateByStr(dto.getBirthDate(), null));

		//当前党员为党支部书记时,要将之前所在的支部的党支部书记清空,并且将该党员的角色职位普通党员
        DjDept oldDept = deptMapper.selectByPrimaryKey(oldDeptId);
        DjDept newDept = deptMapper.selectByPrimaryKey(dto.getDeptId());
        if (oldDept != null && !oldDept.equals(newDept) && oldDept.getPrincipalId().equals(partymember.getId())) {
            oldDept.setPrincipalId(null);
            oldDept.setPrincipalName(null);
            user.setRoleId(RoleEnum.PARTY.getId());
            deptMapper.updateByPrimaryKey(oldDept);
        }

        //当前党员为片区负责人时,要将之前所在的片区的片区负责人清空,并且将该党员的角色职位普通党员
        if (oldDept != null) {
            DjSection oldSection = sectionMapper.selectByPrimaryKey(oldDept.getDjSectionId());
            DjSection newSection = sectionMapper.selectByPrimaryKey(newDept.getDjSectionId());
            if (oldSection!= null && !oldSection.equals(newSection) && oldSection.getPrincipalId().equals(partymember.getId())) {
                oldSection.setPrincipalId(null);
                oldSection.setPrincipalName(null);
                sectionMapper.updateByPrimaryKey(oldSection);
                user.setRoleId(RoleEnum.PARTY.getId());
            }
        }
        userMapper.updateByPrimaryKey(user);
        partyMemberMapper.updateByPrimaryKey(partymember);

		return true;
	}

	/**
	 * 作废党员
	 *
	 * @param id
	 * @return
	 */
	@Override
	public boolean invalidPartyMember(Long id, UserVo user) {
		DjPartymember partymember = partyMemberMapper.selectByPrimaryKey(id);
		DjUser oldUser = userMapper.selectByPrimaryKey(id);
		if (partymember == null || oldUser == null) {
			throw new BusinessException("该党员不存在!", ResultCode.NotFound.getCode());
		}
		partymember.setStatus(DlbConstant.BASEDATA_STATUS_DEL);
		partyMemberMapper.updateByPrimaryKey(partymember);

		oldUser.setStatus(DlbConstant.BASEDATA_STATUS_DEL);
		userMapper.updateByPrimaryKey(oldUser);
		return true;
	}

	@Override
	public SelectResultVo getPartyMembersBySectionId(Long sectionId) {
		SelectResultVo result = new SelectResultVo();
		if(sectionId == null) {
			return result;
		}
		
		List<SelectVo> results = partyMemberMapper.getPartyMembersBySectionId(sectionId);
		result.setResults(results);
		return result;
	}

	/**
	 * 获取支部下的所有党员信息
	 *
	 * @param deptId
	 * @return
	 */
	@Override
	public SelectResultVo getPartyMembersByDeptId(Long deptId) {
		SelectResultVo result = new SelectResultVo();
		if(deptId == null) {
			return result;
		}
		List<SelectVo> results = partyMemberMapper.getPartyMembersByDeptId(deptId);
		result.setResults(results);
		return result;
	}

	/**
	 * 查询党员详细信息.
	 *
	 * @param id
	 * @return
	 */
	@Override
	public PartyMemberDetailVo getPartyMemberDetailById(long id) {
		PartyMemberDetailVo vo = partyMemberMapper.getPartyMemberDetailById(id);
		if (vo == null) {
			return new PartyMemberDetailVo();
		}
		return vo;
	}

	/* (non-Javadoc)
	 * <p>Title: getTop5Score</p>
	 * <p>Description: 获取积分最多的五个人</p> 
	 * @param year
	 * @return  
	 * @see cn.dlbdata.dj.service.IPartyMemberService#getTop5Score(java.lang.Integer)
	 */
	@Override
	public List<Map<String, Object>> getTop5Score(Integer year) {
		if(year == null) {
			logger.error("year ->"+year);
			return null;
		}
		return partyMemberMapper.getTop5Score(year);
	}

	/**
	 * 获取片区内的党支部以及党支部成员
	 *
	 * @param sectionId
	 * @return
	 */
	@Override
	public List<SelectBuildingVo> getDeptAndPtMemberTree(long sectionId) {
		List<SelectBuildingVo> rlist = new ArrayList<SelectBuildingVo>();
		List<IdNameDto> deptList = deptMapper.getDeptIdAndNameBySectionId(sectionId);
		if (deptList != null) {
			SelectBuildingVo vo = null;
			for (IdNameDto dept : deptList) {
				vo = new SelectBuildingVo(dept.getName(),"s" + dept.getId(), "0");
				List<IdNameDto> list = partyMemberMapper.getPtmemberIdAndNameBydeptId(dept.getId());
				List<SelectBuildingVo> childrenVo = new ArrayList<>();
				for(IdNameDto dto :list) {
					SelectBuildingVo cldVo = new SelectBuildingVo(
							dto.getName(),"" + dto.getId(), "s" + dept.getId());
					childrenVo.add(cldVo);
				}
				vo.setChildren(childrenVo);
				rlist.add(vo);
			}
		}
		return rlist;
	}
}
