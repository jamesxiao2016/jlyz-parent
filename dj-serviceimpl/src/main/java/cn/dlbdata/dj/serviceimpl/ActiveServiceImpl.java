package cn.dlbdata.dj.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.dlbdata.dj.common.core.util.DatetimeUtil;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.PageVo;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.mapper.DjActiveDeptMapper;
import cn.dlbdata.dj.db.mapper.DjActiveMapper;
import cn.dlbdata.dj.db.mapper.DjActiveUserMapper;
import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.mapper.DjPartymemberMapper;
import cn.dlbdata.dj.db.mapper.DjPicRecordMapper;
import cn.dlbdata.dj.db.mapper.DjStudyMapper;
import cn.dlbdata.dj.db.mapper.DjUserMapper;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.db.pojo.DjActiveDept;
import cn.dlbdata.dj.db.pojo.DjActiveUser;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.dto.PartyMemberLifeNotice;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.ActiveVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.vo.study.PendingPtMemberVo;
import cn.dlbdata.dj.vo.study.StudyDetailVo;
import tk.mybatis.mapper.entity.Example;

@Service
public class ActiveServiceImpl extends BaseServiceImpl implements IActiveService {
	@Autowired
	private DjActiveMapper activeMapper;
	@Autowired
	private DjActiveDeptMapper activeDeptMapper;
	@Autowired
	private DjStudyMapper studyMapper;
	@Autowired
	private DjPicRecordMapper picRecordMapper;
	@Autowired
	private DjActiveUserMapper activeUserMapper;
	@Autowired
	private DjDeptMapper deptMapper;
	@Autowired
	private DjUserMapper userMapper;
	@Autowired
	private DjPartymemberMapper partymemberMapper;

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
	 * (non-Javadoc) <p>Title: getParticipateActiveCount</p> <p>Description:
	 * 党员生活通知总数</p>
	 *
	 * @param PartyMemberLifeNotice
	 *
	 * @return
	 *
	 * @see
	 * cn.dlbdata.dj.service.IActiveService#getParticipateActiveCount(cn.dlbdata.dj.
	 * db.resquest.PartyMemberLifeNotice)
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
//		List<Map<String, Object>> activeList = activeMapper.getRunningActive(map);
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
	public ResultVo<Map<String, Object>> queryActiveById(Long activeId, Long userId) {
		ResultVo<Map<String, Object>> result = new ResultVo<>();
		if (activeId == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("活动id不能为空");
			return result;
		}
		if (userId == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("用户id不能为空");
			return result;
		}
		DjActive active = activeMapper.selectByPrimaryKey(activeId);
		if (active == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("没用相关的活动");
			return result;
		}
		JSONObject json = JSON.parseObject(JSON.toJSONString(active));
		DjUser createUser = userMapper.selectByPrimaryKey(userId);
		if (createUser == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("没有用户信息");
			return result;
			// json.put("activeCreatePeopleName", createUser.getName());
		}
		if (createUser.getRoleId() == 1) {
			result.setCode(ResultCode.OK.getCode());
			result.setData(json);
			return result;
		}
		Example example = new Example(DjActiveUser.class);
		example.createCriteria().andEqualTo("djActiveId", activeId);
		List<DjActiveUser> participateList = activeUserMapper.selectByExample(example);
		if (participateList != null && participateList.size() > 0) {

			Map<String, List<DjPartymember>> mapPartyMember = new HashMap<String, List<DjPartymember>>();
			List<Long> inList = new ArrayList<>();
			List<Long> outList = new ArrayList<>();
			for (DjActiveUser item : participateList) {
				if (Integer.valueOf(1).equals(item.getStatus())) {
					inList.add(item.getDjUserId());
				} else if (Integer.valueOf(0).equals(item.getStatus())) {
					outList.add(item.getDjUserId());
				}
			}
			List<DjPartymember> inUserList = new ArrayList<>();

			List<DjPartymember> outUserList = new ArrayList<>();
			if (inList.size() > 0) {
				Example inUserExample = new Example(DjPartymember.class);
				inUserExample.createCriteria().andIn("id", inList);
				inUserExample.setOrderByClause("post_id");
				inUserList.addAll(partymemberMapper.selectByExample(inUserExample));
			}
			int inUserListCount = inUserList.size();
			Map<String, List<DjPartymember>> inUserMap = new TreeMap<String, List<DjPartymember>>();

			if (inUserList.size() > 0) {
				for (int i = 0; i < inUserList.size(); i++) {
					DjPartymember p = inUserList.get(i);
					List<DjPartymember> list = null;
					if (i == 0) {
						list = new ArrayList<DjPartymember>();
						DjDept dept = deptMapper.selectByPrimaryKey(p.getDeptId());
						p.setDeptName(dept.getName());
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
				outUserExample.createCriteria().andIn("id", inList);
				outUserExample.setOrderByClause("post_id");
				outUserList.addAll(partymemberMapper.selectByExample(outUserExample));
			}
			int outUserListCount = outUserList.size();
			Map<String, List<DjPartymember>> outUserMap = new TreeMap<String, List<DjPartymember>>();
			if (outUserList.size() > 0) {
				for (int i = 0; i < outUserList.size(); i++) {
					DjPartymember p = outUserList.get(i);
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

			json.put("participateCount", inUserListCount);
			json.put("notParticipateCount", outUserListCount);
			for (Map.Entry<String, List<DjPartymember>> entry : inUserMap.entrySet()) {
				List<DjPartymember> list = entry.getValue();
				Collections.sort(list, new Comparator<DjPartymember>() {
					@Override
					public int compare(DjPartymember o1, DjPartymember o2) {
						if (o1 != null && o1.getPostId() != null && o2 != null && o2.getPostId() != null) {
							if (o1.getPostId() > o2.getPostId()) {
								return -1;
							} else if (o1.getPostId() == o2.getPostId()) {
								return 0;
							} else {
								return 1;
							}
						}
						return 0;
					}
				});
			}
			json.put("participate", inUserMap);
			for (Map.Entry<String, List<DjPartymember>> entry : outUserMap.entrySet()) {
				List<DjPartymember> list = entry.getValue();
				if (list == null || list.isEmpty()) {
					continue;
				}
				Collections.sort(list, new Comparator<DjPartymember>() {
					@Override
					public int compare(DjPartymember o1, DjPartymember o2) {
						if (o1 != null && o1.getPostId() != null && o2 != null && o2.getPostId() != null) {
							if (o1.getPostId() > o2.getPostId()) {
								return -1;
							} else if (o1.getPostId() == o2.getPostId()) {
								return 0;
							} else {
								return 1;
							}
						}
						return 0;
					}
				});
			}
			result.setData(json);
		}
		return result;
	}

}
