package cn.dlbdata.dj.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.util.DatetimeUtil;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.mapper.DjActiveDeptMapper;
import cn.dlbdata.dj.db.mapper.DjActiveMapper;
import cn.dlbdata.dj.db.mapper.DjPartymemberMapper;
import cn.dlbdata.dj.db.mapper.DjPicRecordMapper;
import cn.dlbdata.dj.db.mapper.DjScoreMapper;
import cn.dlbdata.dj.db.mapper.DjStudyMapper;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.db.pojo.DjActiveDept;
import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.dto.PartyMemberLifeNotice;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.service.IWorkflowService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.ActiveVo;
import cn.dlbdata.dj.vo.ApplyVo;
import cn.dlbdata.dj.vo.PageVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.vo.study.PendingPtMemberVo;
import cn.dlbdata.dj.vo.study.StudyDetailVo;

@Service
public class ActiveServiceImpl extends BaseServiceImpl implements IActiveService {
	@Autowired
	private DjActiveMapper activeMapper;
	@Autowired
	private DjActiveDeptMapper activeDeptMapper;
	@Autowired
	private DjPartymemberMapper partymemberMapper;
	@Autowired
	private DjScoreMapper scoreMapper;
	@Autowired
	private DjStudyMapper studyMapper;
	@Autowired
	private IWorkflowService workflowService;
	@Autowired
	private DjPicRecordMapper picRecordMapper;

	@Override
	public DjActive getActiveInfoById(Long id) {
		if (id == null) {
			return null;
		}
		return activeMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<DjActive> getActiveListByDeptId(Long deptId) {
		if (deptId == null) {
			return null;
		}
		DjActiveDept condition = new DjActiveDept();
		condition.setDjDeptId(deptId);
		List<DjActiveDept> list = activeDeptMapper.select(condition);
		return null;
	}

	@Override
	public List<DjActive> getActiveListByDeptIds(Long[] deptIds) {
		if (deptIds == null) {
			return null;
		}
		return null;
	}

	@Override
	public Integer getActiveNumByUserId(Long userId, Long activeType) {
		if (userId == null) {
			return 0;
		}
		Date startTime = DatetimeUtil.getCurrYearFirst();
		Date endTime = DatetimeUtil.getCurrYearLast();
		Integer count = activeMapper.getUserActiveCountByActiveTypeAndTime(userId, activeType, startTime, endTime);
		return count;
	}

	@Override
	public PageVo<List<Map<String, Object>>> getParticipateActive(PartyMemberLifeNotice partyMemberLifeNotice) {

		PageVo<List<Map<String, Object>>> result = new PageVo<>();
		if (partyMemberLifeNotice == null) {
			return result;
		}
		partyMemberLifeNotice.setEndTime(new Date());
		Map<String, Object> map = new HashMap<>();
		map.put("userId", partyMemberLifeNotice.getUserId());
		map.put("startTime", partyMemberLifeNotice.getStartTime());
		map.put("endTime", partyMemberLifeNotice.getEndTime());
		map.put("departmentId", partyMemberLifeNotice.getDepartmentId());
		List<Map<String, Object>> activeList = activeMapper.getRunningActive(map);
		result.setTotal(getParticipateActiveCount(partyMemberLifeNotice));
		result.setData(activeList);
		return result;
	}

	/*
	 * (non-Javadoc) <p>Title: getParticipateActiveCount</p> <p>Description: 党员生活通知总数</p>
	 *
	 * @param PartyMemberLifeNotice
	 *
	 * @return
	 *
	 * @see
	 * cn.dlbdata.dj.service.IActiveService#getParticipateActiveCount(cn.dlbdata.dj.db.resquest.PartyMemberLifeNotice)
	 */
	@Override
	public int getParticipateActiveCount(PartyMemberLifeNotice partyMemberLifeNotice) {
		if (partyMemberLifeNotice == null) {
			return 0;
		}
		partyMemberLifeNotice.setEndTime(new Date());
		Map<String, Object> map = new HashMap<>();
		map.put("userId", partyMemberLifeNotice.getUserId());
		map.put("startTime", partyMemberLifeNotice.getStartTime());
		map.put("endTime", partyMemberLifeNotice.getEndTime());
		map.put("departmentId", partyMemberLifeNotice.getDepartmentId());
		List<Map<String, Object>> activeList = activeMapper.getRunningActive(map);
		int count = activeMapper.getParticipateActiveCount(map);
		return count;
	}

	@Override
	public List<PendingPtMemberVo> getPendingList(Long deptId, Long subTypeId) {
		List<DjStudy> studies = studyMapper.getStudysByDeptIdAndSubTypeId(deptId, subTypeId);
		List<PendingPtMemberVo> voList = new ArrayList<>();
		for (DjStudy study : studies) {
			PendingPtMemberVo vo = new PendingPtMemberVo();
			vo.setCreateTime(new Timestamp(study.getCreateTime().getTime()));
			vo.setStudyId(study.getId());
			vo.setName(study.getUserName());
			vo.setStatus(study.getStatus());
			voList.add(vo);
		}
		return voList;
	}

	@Override
	public ResultVo<Long> createActive(ActiveVo activeVo, UserVo user) {
		ResultVo<Long> result = new ResultVo<>();
		if (activeVo == null || user == null) {
			logger.error("参数错误");
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("参数错误");
			return result;
		}
		// 保存活动信息
		DjActive active = new DjActive();
		active.setAddress(activeVo.getAddress());
		active.setContent(activeVo.getContent());
		active.setCreateTime(new Date());
		active.setCreateUserId(user.getUserId());
		active.setEndTime(activeVo.getEndActiveTime());
		active.setHasAudit(1);
		active.setId(DigitUtil.generatorLongId());
		active.setName(activeVo.getActiveName());
		active.setDjPicId(activeVo.getPicId());
		active.setPrincipalName(activeVo.getPrincipalName());
		active.setStartTime(activeVo.getStartActiveTime());
		active.setStatus(1);
		active.setDjSubTypeId(activeVo.getSubTypeId());
		active.setDjTypeId(activeVo.getTypeId());
		active.setDjDeptId(user.getDeptId());
		activeMapper.insertSelective(active);

		return result;
	}

	/**
	 * 获取自主学习详情
	 *
	 * @param studyId 自主学习Id
	 * @return
	 */
	@Override
	public StudyDetailVo getStudyDetail(Long studyId) {
		DjStudy study = studyMapper.selectByPrimaryKey(studyId);
		if (study == null) {
			return new StudyDetailVo();
		}
		StudyDetailVo detailVo = new StudyDetailVo();
		detailVo.setName(study.getUserName());
		detailVo.setStatus(study.getStatus());
		detailVo.setContent(study.getContent());
		detailVo.setStartTime(study.getStartTime());
		detailVo.setEndTime(study.getEndTime());
		String tableName =DlbConstant.TABLE_NAME_STUDY;
		List<Long> picIds = picRecordMapper.getIdsByTableNameAndRecordId(tableName,studyId);
		detailVo.setPicIds(picIds);
		return detailVo;
	}
}
