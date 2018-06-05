package cn.dlbdata.dj.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hazelcast.mapreduce.impl.operation.RequestPartitionResult.ResultState;

import cn.dlbdata.dj.common.core.util.DatetimeUtil;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.PageUtils;
import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.ActiveSubTypeEnum;
import cn.dlbdata.dj.constant.ActiveTypeEnum;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.constant.RoleEnum;
import cn.dlbdata.dj.db.mapper.DjActiveDeptMapper;
import cn.dlbdata.dj.db.mapper.DjActiveMapper;
import cn.dlbdata.dj.db.mapper.DjActivePicMapper;
import cn.dlbdata.dj.db.mapper.DjActiveUserMapper;
import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.mapper.DjPartymemberMapper;
import cn.dlbdata.dj.db.mapper.DjScoreMapper;
import cn.dlbdata.dj.db.mapper.DjSubTypeMapper;
import cn.dlbdata.dj.db.mapper.DjTypeMapper;
import cn.dlbdata.dj.db.mapper.DjUserMapper;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.db.pojo.DjActiveDept;
import cn.dlbdata.dj.db.pojo.DjActivePic;
import cn.dlbdata.dj.db.pojo.DjActiveUser;
import cn.dlbdata.dj.db.pojo.DjApply;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.pojo.DjScore;
import cn.dlbdata.dj.db.pojo.DjSubType;
import cn.dlbdata.dj.db.pojo.DjType;
import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.dto.PartyMemberLifeNotice;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.ActiveVo;
import cn.dlbdata.dj.vo.UserVo;
import tk.mybatis.mapper.entity.Example;

@Service
public class ActiveServiceImpl extends BaseServiceImpl implements IActiveService {
	@Autowired
	private DjActiveMapper activeMapper;
	@Autowired
	private DjActiveDeptMapper activeDeptMapper;
	@Autowired
	private DjDeptMapper deptMapper;
	@Autowired
	private DjUserMapper userMapper;
	@Autowired
	private DjPartymemberMapper partymemberMapper;
	@Autowired
	private DjActiveUserMapper activeUserMapper;
	@Autowired
	private DjActivePicMapper activePicMapper;
	@Autowired
	private DjSubTypeMapper subTypeMapper;
	@Autowired
	private DjScoreMapper scoreMapper;
	@Autowired
	private DjTypeMapper typeMapper;

	@Override
	public DjActive getActiveInfoById(Long id) {
		if (id == null) {
			return null;
		}
		return activeMapper.selectByPrimaryKey(id);
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

	/**
	 * 党员生活通知列表
	 */
	@Override
	public Paged<Map<String, Object>> getParticipateActive(PartyMemberLifeNotice partyMemberLifeNotice) {
		if (partyMemberLifeNotice == null) {
			return new Paged<>();
		}
		partyMemberLifeNotice.setPageNum(PageUtils.normalizePageIndex(partyMemberLifeNotice.getPageNum()));
		partyMemberLifeNotice.setPageSize(PageUtils.normalizePageSize(partyMemberLifeNotice.getPageSize()));
		partyMemberLifeNotice.setEndTime(new Date());
		// 报名的集合
		Map<String, Object> map = new HashMap<>();
		map.put("userId", partyMemberLifeNotice.getUserId());
		map.put("startTime", partyMemberLifeNotice.getStartTime());
		map.put("endTime", partyMemberLifeNotice.getEndTime());
		map.put("departmentId", partyMemberLifeNotice.getDepartmentId());
		map.put("signUp", partyMemberLifeNotice.getSignUp());
		Page<Map<String, Object>> page = PageHelper.startPage(partyMemberLifeNotice.getPageNum(),
				partyMemberLifeNotice.getPageSize());
		activeMapper.getRunningActive(map);
		return PageUtils.toPaged(page);
	}

	/**
	 * 党员生活通知列表第一条
	 */
	@Override
	public ResultVo<Map<String, Object>> getParticipateActiveOne(PartyMemberLifeNotice partyMemberLifeNotice) {
		ResultVo<Map<String, Object>> result = new ResultVo<>();
		if (partyMemberLifeNotice == null) {
			return result;
		}
		partyMemberLifeNotice.setEndTime(new Date());
		// 请求参数map集合
		Map<String, Object> map = new HashMap<>();
		map.put("userId", partyMemberLifeNotice.getUserId());
		map.put("startTime", partyMemberLifeNotice.getStartTime());
		map.put("endTime", partyMemberLifeNotice.getEndTime());
		map.put("departmentId", partyMemberLifeNotice.getDepartmentId());
		Map<String, Object> map1 = activeMapper.getParticipateActiveOne(map);
		int count = getParticipateActiveCount(partyMemberLifeNotice);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("count", count);
		map2.put("djActive", map1);
		result.setData(map2);
		return result;
	}

	/*
	 * (non-Javadoc) <p>Title: getParticipateActiveCount</p> <p>Description: 党员生活通知总数</p>
	 * 
	 * @param PartyMemberLifeNotice
	 *
	 * @return
	 *
	 * @see cn.dlbdata.dj.service.IActiveService#getParticipateActiveCount(cn.dlbdata.dj.
	 * db.resquest.PartyMemberLifeNotice)
	 */
	@Override
	public int getParticipateActiveCount(PartyMemberLifeNotice partyMemberLifeNotice) {
		if (partyMemberLifeNotice == null) {
			logger.error("参数错误");
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

	// @Override
	// public Paged<PendingPtMemberVo> getPendingList(Long deptId, Long subTypeId,
	// int pageNum, int pageSize) {
	// Page<PendingPtMemberVo> page = PageHelper.startPage(pageNum, pageSize);
	// studyMapper.getStudysByDeptIdAndSubTypeId(deptId, subTypeId);
	// return PageUtils.toPaged(page);
	// }

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
		active.setEndTime(DatetimeUtil.getDateByStr(activeVo.getEndActiveTime()));
		active.setHasAudit(1);
		active.setId(DigitUtil.generatorLongId());
		active.setName(activeVo.getActiveName());
		active.setDjPicId(activeVo.getPicId());
		active.setPrincipalName(activeVo.getPrincipalName());
		active.setStartTime(DatetimeUtil.getDateByStr(activeVo.getStartActiveTime()));
		active.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
		active.setDjSubTypeId(activeVo.getSubTypeId());
		active.setDjTypeId(activeVo.getTypeId());
		active.setUserName(user.getUserName());
		if (user.getRoleId() != null && user.getRoleId() == RoleEnum.BRANCH_PARTY.getId()) {
			active.setDjDeptId(user.getDeptId());
		} else {
			active.setDjDeptId(0L);
		}
		activeMapper.insertSelective(active);

		// 保存活动参与部门表
		if (activeVo.getDeptIds() != null) {
			for (Long deptId : activeVo.getDeptIds()) {
				DjActiveDept dept = new DjActiveDept();
				dept.setCreateTime(new Date());
				dept.setDjActiveId(active.getId());
				dept.setDjDeptId(deptId);
				dept.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
				activeDeptMapper.insertSelective(dept);
			}
		}

		// TODO 不是金领驿站的项目自动报名
		if (activeVo.getSubTypeId() != ActiveSubTypeEnum.ACTIVE_SUB_E.getActiveSubId()) {
			if (activeVo.getDeptIds() != null) {
				List<Long> deptIds = Arrays.asList(activeVo.getDeptIds());
				Example condition = new Example(DjUser.class);
				condition.createCriteria().andIn("deptId", deptIds);
				List<DjUser> users = userMapper.selectByExample(condition);
				if (users != null) {
					activeUserMapper.insertList(users, active.getId());
				}
			}
		}
		result.setCode(ResultCode.OK.getCode());
		result.setData(active.getId());
		return result;
	}

	/*
	 * (non-Javadoc) <p>Title: queryActiveById</p> <p>Description: 获取活动详情</p>
	 * 
	 * @param activeId
	 * 
	 * @return
	 * 
	 * @see cn.dlbdata.dj.service.IActiveService#queryActiveById(java.lang.Long)
	 */
	@Override
	public ResultVo<Map<String, Object>> queryActiveById(Long activeId, Long roleId) {
		ResultVo<Map<String, Object>> result = new ResultVo<>();
		if (activeId == null) {
			logger.error("参数错误");
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		if (roleId == null) {
			roleId = RoleEnum.PARTY.getId();
		}
		DjActive active = activeMapper.selectActiveIndexById(activeId);
		if (active == null) {
			logger.error("参数错误");
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("获取活动失败");
			return result;
		}
		JSONObject json = JSON.parseObject(JSON.toJSONString(active));
		// 获取活动图集
		Long[] picIds = new Long[0];
		Example picExample = new Example(DjActivePic.class);
		picExample.createCriteria().andEqualTo("djActiveId", active.getId());
		List<DjActivePic> picList = activePicMapper.selectByExample(picExample);
		/* 将与该活动相关的图片的id加入数组中 */
		if (picList != null && picList.size() > 0) {
			picIds = new Long[picList.size()];
			for (int i = 0, count = picList.size(); i < count; i++) {
				picIds[i] = picList.get(i).getDjPicId();
			}
			active.setPicIds(picIds);
		}
		json.put("picIds", picIds);
		if (roleId == RoleEnum.PARTY.getId()) {
			result.setCode(ResultCode.OK.getCode());
			result.setData(json);
			return result;
		}

		/**
		 * 获取参与活动的部门列表
		 */
		Map<Long, String> deptNameMap = new HashMap<>();
		DjActiveDept deptCondition = new DjActiveDept();
		List<DjActiveDept> deptList = activeDeptMapper.select(deptCondition);
		if (deptList != null) {
			for (DjActiveDept d : deptList) {
				DjDept dept = deptMapper.selectByPrimaryKey(d.getDjDeptId());
				if (dept != null) {
					deptNameMap.put(d.getDjDeptId(), dept.getName());
				} else {
					deptNameMap.put(d.getDjDeptId(), "");
				}
			}
		}

		/**
		 * 查询参与人员列表
		 */
		Example example = new Example(DjActiveUser.class);
		example.createCriteria().andEqualTo("djActiveId", activeId);
		List<DjActiveUser> participateList = activeUserMapper.selectByExample(example);
		if (participateList != null && participateList.size() > 0) {
			List<Long> inList = new ArrayList<>();
			List<Long> outList = new ArrayList<>();
			for (DjActiveUser item : participateList) {
				if (Integer.valueOf(DlbConstant.BASEDATA_STATUS_VALID).equals(item.getStatus())) {
					inList.add(item.getDjUserId());
				} else if (Integer.valueOf(DlbConstant.BASEDATA_STATUS_INVALID).equals(item.getStatus())) {
					outList.add(item.getDjUserId());
				}
			}
			List<DjPartymember> inUserList = new ArrayList<>();

			List<DjPartymember> outUserList = new ArrayList<>();
			if (inList.size() > 0) {
				Example inUserExample = new Example(DjPartymember.class);
				inUserExample.createCriteria().andIn("id", inList);
				inUserExample.setOrderByClause("party_post_code");
				inUserList.addAll(partymemberMapper.selectByExample(inUserExample));
			}
			int inUserListCount = inUserList.size();
			// 参与人员集合
			Map<String, List<DjPartymember>> inUserMap = new TreeMap<String, List<DjPartymember>>();

			if (inUserList.size() > 0) {
				for (int i = 0; i < inUserList.size(); i++) {
					DjPartymember p = inUserList.get(i);
					if (p == null) {
						continue;
					}
					p.setDeptName(deptNameMap.get(p.getDeptId()));
					List<DjPartymember> list = null;
					if (i == 0) {
						list = new ArrayList<DjPartymember>();
						list.add(p);
						inUserMap.put(p.getDeptName(), list);
					} else {
						list = inUserMap.get(p.getDeptName());
						if (list == null) {
							list = new ArrayList<DjPartymember>();
						}
						list.add(p);

						inUserMap.put(p.getDeptName(), list);
					}
				}
			}
			if (outList.size() > 0) {
				Example outUserExample = new Example(DjPartymember.class);
				outUserExample.createCriteria().andIn("id", outList);
				outUserExample.setOrderByClause("party_post_code");
				outUserList.addAll(partymemberMapper.selectByExample(outUserExample));
			}
			int outUserListCount = outUserList.size();
			// 未参与人集合
			Map<String, List<DjPartymember>> outUserMap = new TreeMap<String, List<DjPartymember>>();
			if (outUserList.size() > 0) {
				for (int i = 0; i < outUserList.size(); i++) {
					DjPartymember p = outUserList.get(i);
					if (p == null) {
						continue;
					}
					p.setDeptName(deptNameMap.get(p.getDeptId()));
					List<DjPartymember> list = null;
					if (i == 0) {
						list = new ArrayList<DjPartymember>();
						list.add(p);
						outUserMap.put(p.getDeptName(), list);
					} else {
						list = outUserMap.get(p.getDeptName());
						if (list == null) {
							list = new ArrayList<DjPartymember>();
						}
						list.add(p);

						outUserMap.put(p.getDeptName(), list);
					}
				}
			}
			// 参与人员总人数
			json.put("participateCount", inUserListCount);
			// 未参与人员总人数
			json.put("notParticipateCount", outUserListCount);
			// 参与人员集合
			json.put("participate", inUserMap);
			// 未参与人员集合
			json.put("notParticipate", outUserMap);
			result.setData(json);
		}
		return result;
	}

	@Override
	public ResultVo<String> signIn(Long activeId, UserVo user) {
		ResultVo<String> result = new ResultVo<>();
		if (activeId == null || user == null) {
			result.setCode(ResultCode.ParameterError.getCode());
			logger.error("参数错误");
			result.setMsg("签到失败");
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
			result.setMsg("活动未报名，请先报名");
			return result;
		}

		// 获取报名信息
		activeUser = list.get(0);
		Integer status = activeUser.getStatus();
		if (DlbConstant.BASEDATA_STATUS_VALID == status) {
			result.setCode(ResultCode.NotFound.getCode());
			result.setMsg("已签到，请勿重复签到");
			return result;
		}

		// 更新签到信息
		activeUser.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
		activeUser.setSignTime(new Date());
		int count = activeUserMapper.updateByPrimaryKeySelective(activeUser);
		if (count > 0) {
			DjApply apply = new DjApply();
			Long subTypeId = active.getDjSubTypeId();
			DjSubType subType = subTypeMapper.selectByPrimaryKey(subTypeId);
			DjDept dept = deptMapper.selectByPrimaryKey(user.getDeptId());
			handScore(subTypeId, user.getUserId(), user.getUserId(), user.getUserName(), dept.getPrincipalId(),
					dept.getPrincipalName(), subType.getScore(), apply.getRecordId(), apply.getRemark(),
					apply.getApplyYear());
		} else {

		}
		result.setCode(ResultCode.OK.getCode());
		result.setMsg("签到成功，积分已发放");

		return result;
	}

	public void handScore(Long subTypeId, Long userId, Long applyerId, String applyerName, Long approverId,
			String approverName, Float applySocre, Long recordId, String recordDesc, Integer year) {
		DjSubType subType = subTypeMapper.selectByPrimaryKey(subTypeId);
		if (subType == null) {
			return;
		}
		DjType type = typeMapper.selectByPrimaryKey(subType.getDjTypeId());
		if (type == null) {
			return;
		}
		// 写入积分记录表
		// 根据类型判断最大分数
		if (year == null) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		Float subTypeMaxScore = subType.getMaxScore();
		if (subTypeMaxScore == null) {
			subTypeMaxScore = 0F;
		}
		Float typeMaxScore = type.getMaxScore();
		if (typeMaxScore == null) {
			typeMaxScore = type.getScore();
			if (typeMaxScore == null)
				typeMaxScore = 0F;
		}
		Float userSubTypeScore = scoreMapper.getSumScoreByUserIdAndType(userId, year, null, subTypeId);
		if (userSubTypeScore == null) {
			userSubTypeScore = 0F;
		}
		Float userTypeScore = scoreMapper.getSumScoreByUserIdAndType(userId, year, subType.getDjTypeId(), null);
		if (userTypeScore == null) {
			userTypeScore = 0F;
		}
		// 积分没有积满，则往积分表中插入记录
		if (userSubTypeScore < subTypeMaxScore && userTypeScore < typeMaxScore) {
			DjScore record = new DjScore();
			record.setAddStatus(DlbConstant.BASEDATA_STATUS_VALID);
			record.setAddTime(new Date());
			record.setAddYear(year);
			record.setUserId(userId);
			record.setApplyUserId(userId);
			record.setApproverId(approverId);
			record.setCreateTime(new Date());
			record.setDjSubTypeId(subTypeId);
			record.setDjTypeId(type.getId());
			record.setRecordId(recordId);
			record.setRecrodDesc(recordDesc);
			record.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
			record.setApplyUserName(applyerName);
			record.setApproverName(approverName);
			record.setScoreDesc(subType.getName());
			Float score = applySocre;
			// 公益服务,处理9分的问题
			if (type.getId() == ActiveTypeEnum.ACTIVE_F.getActiveId()) {
				if ((userTypeScore + applySocre) > typeMaxScore) {
					score = typeMaxScore - userTypeScore;
				}
			}

			record.setScore(score);
			scoreMapper.insertSelective(record);
		}
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

	@Override
	public Paged<Map<String, Object>> getActiveListByDeptId(PartyMemberLifeNotice partyMemberLifeNotice) {
		if (partyMemberLifeNotice == null || partyMemberLifeNotice.getDepartmentId() == null) {
			return new Paged<>();
		}
		partyMemberLifeNotice.setPageNum(PageUtils.normalizePageIndex(partyMemberLifeNotice.getPageNum()));
		partyMemberLifeNotice.setPageSize(PageUtils.normalizePageSize(partyMemberLifeNotice.getPageSize()));
		// 报名的集合
		Map<String, Object> map = new HashMap<>();
		map.put("startTime", partyMemberLifeNotice.getStartTime());
		map.put("endTime", partyMemberLifeNotice.getEndTime());
		map.put("deptId", partyMemberLifeNotice.getDepartmentId());
		Page<Map<String, Object>> page = PageHelper.startPage(partyMemberLifeNotice.getPageNum(),
				partyMemberLifeNotice.getPageSize());
		List<Map<String, Object>> list = activeMapper.getActiveListByDeptId(map);
		if (list != null && list.size() > 0) {
			for (Map<String, Object> data : list) {
				Long activeId = (Long) data.get("id");
				data.put("picIds", getActivePicList(activeId));
			}
		}
		return PageUtils.toPaged(page);
	}

	/**
	 * 获取活动图片ID列表
	 * 
	 * @param activeId
	 * @return
	 */
	private List<Long> getActivePicList(Long activeId) {
		List<Long> rlist = new ArrayList<>();
		if (activeId == null) {
			return rlist;
		}
		DjActivePic condition = new DjActivePic();
		condition.setDjActiveId(activeId);
		List<DjActivePic> list = activePicMapper.select(condition);
		if (list != null && list.size() > 0) {
			for (DjActivePic p : list) {
				rlist.add(p.getDjPicId());
			}
		}
		return rlist;
	}

}
