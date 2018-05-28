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
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.PageVo;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.mapper.DjActiveDeptMapper;
import cn.dlbdata.dj.db.mapper.DjActiveMapper;
import cn.dlbdata.dj.db.mapper.DjActiveUserMapper;
import cn.dlbdata.dj.db.mapper.DjPicRecordMapper;
import cn.dlbdata.dj.db.mapper.DjStudyMapper;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.db.pojo.DjActiveDept;
import cn.dlbdata.dj.db.pojo.DjActiveUser;
import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.dto.PartyMemberLifeNotice;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.ActiveVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.vo.study.PendingPtMemberVo;
import cn.dlbdata.dj.vo.study.StudyDetailVo;

@Service
public class ActiveServiceImpl extends BaseServiceImpl implements IActiveService {
	@Autowired
	private DjActiveMapper activeMapper;
	@Autowired
	private DjActiveUserMapper activeUserMapper;
	@Autowired
	private DjActiveDeptMapper activeDeptMapper;
	@Autowired
	private DjStudyMapper studyMapper;
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
	public PageVo<Map<String, Object>> getParticipateActive(PartyMemberLifeNotice partyMemberLifeNotice) {

		PageVo<Map<String, Object>> result = new PageVo<>();
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
		// List<Map<String, Object>> activeList = activeMapper.getRunningActive(map);
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
	 * @param studyId
	 *            自主学习Id
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
		String tableName = DlbConstant.TABLE_NAME_STUDY;
		List<Long> picIds = picRecordMapper.getIdsByTableNameAndRecordId(tableName, studyId);
		detailVo.setPicIds(picIds);
		return detailVo;
	}

	@Override
	public ResultVo<String> signIn(Long activeId, UserVo user) {
		ResultVo<String> result = new ResultVo<>();
		if (activeId == null || user == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			return result;
		}

		// 查询活动信息
		DjActive active = activeMapper.selectByPrimaryKey(activeId);
		if (active == null) {
			result.setCode(ResultCode.NotFound.getCode());
			result.setMsg("获取不存在或已取消");
			return result;
		}

		// 查询活动报名信息
		DjActiveUser activeUser = new DjActiveUser();
		activeUser.setDjActiveId(activeId);
		activeUser.setDjUserId(user.getUserId());
		List<DjActiveUser> list = activeUserMapper.select(activeUser);
		if (list == null || list.isEmpty()) {
			result.setCode(ResultCode.NotFound.getCode());
			result.setMsg("活动未报名，签到失败。");
			return result;
		}

		// 获取报名信息
		activeUser = list.get(0);
		Integer status = activeUser.getStatus();
		if (DlbConstant.BASEDATA_STATUS_VALID == status) {
			result.setCode(ResultCode.NotFound.getCode());
			result.setMsg("已签到，请勿重复签到。");
			return result;
		}

		// 更新签到信息
		activeUser.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
		activeUser.setSignTime(new Date());

		activeUserMapper.updateByPrimaryKeySelective(activeUser);

		result.setCode(ResultCode.OK.getCode());
		result.setMsg("签到成功");

		return result;
	}

	@Override
	public ResultVo<String> signUp(Long activeId, UserVo user) {
		ResultVo<String> result = new ResultVo<>(ResultCode.BadRequest.getCode());
		if (activeId == null || user == null) {
			logger.error("参数错误");
			result.setCode(ResultCode.ParameterError.getCode());
			return result;
		}

		// 查询活动信息
		DjActive active = activeMapper.selectByPrimaryKey(activeId);
		if (active == null) {
			logger.error("查询活动失败->" + activeId);
			result.setCode(ResultCode.NotFound.getCode());
			result.setMsg("获取不存在或已取消");
			return result;
		}

		// 查询活动报名信息
		DjActiveUser activeUserCondition = new DjActiveUser();
		activeUserCondition.setDjActiveId(activeId);
		activeUserCondition.setDjUserId(user.getUserId());
		List<DjActiveUser> list = activeUserMapper.select(activeUserCondition);
		if (list != null && !list.isEmpty()) {
			result.setCode(ResultCode.NotFound.getCode());
			result.setMsg("已报名。");
			return result;
		}

		// 更新签到信息
		DjActiveUser activeUser = new DjActiveUser();
		activeUser.setCreateTime(new Date());
		activeUser.setDjActiveId(activeId);
		activeUser.setDjDeptId(user.getDeptId());
		activeUser.setDjUserId(user.getUserId());
		activeUser.setStatus(DlbConstant.BASEDATA_STATUS_VALID);

		int count = activeUserMapper.insertSelective(activeUser);
		if (count > 0) {
			result.setCode(ResultCode.OK.getCode());
			result.setMsg("报名成功");
		}

		return result;
	}
}
