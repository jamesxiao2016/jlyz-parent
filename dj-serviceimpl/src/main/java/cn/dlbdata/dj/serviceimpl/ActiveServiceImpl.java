package cn.dlbdata.dj.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.dlbdata.dj.db.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.dlbdata.dj.common.core.util.DatetimeUtil;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.PageUtils;
import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.ActiveSubTypeEnum;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.constant.RoleEnum;
import cn.dlbdata.dj.db.mapper.DjActiveDeptMapper;
import cn.dlbdata.dj.db.mapper.DjActiveMapper;
import cn.dlbdata.dj.db.mapper.DjActiveUserMapper;
import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.mapper.DjPartymemberMapper;
import cn.dlbdata.dj.db.mapper.DjPicRecordMapper;
import cn.dlbdata.dj.db.mapper.DjStudyMapper;
import cn.dlbdata.dj.db.mapper.DjUserMapper;
import cn.dlbdata.dj.db.vo.study.PendingPtMemberVo;
import cn.dlbdata.dj.dto.PartyMemberLifeNotice;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.ActiveVo;
import cn.dlbdata.dj.vo.UserVo;
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
	private DjDeptMapper deptMapper;
	@Autowired
	private DjUserMapper userMapper;
	@Autowired
	private DjPartymemberMapper partymemberMapper;
	@Autowired
	private DjActiveUserMapper activeUserMapper;

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
	public ResultVo<Map<String, Object>> getParticipateActive(PartyMemberLifeNotice partyMemberLifeNotice) {

		ResultVo<Map<String, Object>> result = new ResultVo<>();
		if (partyMemberLifeNotice == null) {
			return result;
		}
		partyMemberLifeNotice.setEndTime(new Date());
		//报名的集合
		Map<String, Object> map = new HashMap<>();
		map.put("userId", partyMemberLifeNotice.getUserId());
		map.put("startTime", partyMemberLifeNotice.getStartTime());
		map.put("endTime", partyMemberLifeNotice.getEndTime());
		map.put("departmentId", partyMemberLifeNotice.getDepartmentId());
		map.put("signUp", DlbConstant.BASEDATA_STATUS_VALID);
		Page<Map<String, Object>> page = PageHelper.startPage(1, 1);
		List<Map<String, Object>> inList = activeMapper.getRunningActive(map);
		//未报名的集合
		map.put("signUp", DlbConstant.BASEDATA_STATUS_INVALID);
		Page<Map<String, Object>> page2 = PageHelper.startPage(1, 1);
		List<Map<String, Object>> outList = activeMapper.getRunningActive(map);
		Map<String, Object> mapThree = new HashMap<String, Object>();
		mapThree.put("registered", page.getResult());
		mapThree.put("noRegistered", page2.getResult());
		int count = getParticipateActiveCount(partyMemberLifeNotice);
		result.setMsg(String.valueOf(count));
		result.setData(mapThree);
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

//	@Override
//	public Paged<PendingPtMemberVo> getPendingList(Long deptId, Long subTypeId, int pageNum, int pageSize) {
//		Page<PendingPtMemberVo> page = PageHelper.startPage(pageNum, pageSize);
//		studyMapper.getStudysByDeptIdAndSubTypeId(deptId, subTypeId);
//		return PageUtils.toPaged(page);
//	}

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
		if (user.getRoleId() != null && user.getRoleId() == RoleEnum.BRANCH_SECRETARY.getId()) {
			active.setDjDeptId(user.getDeptId());
		} else {
			active.setDjDeptId(0L);
		}
		activeMapper.insertSelective(active);

		// TODO 不是金领驿站的项目自动报名
		if (activeVo.getTypeId() != ActiveSubTypeEnum.ACTIVE_SUB_E.getActiveSubId()) {
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
	public ResultVo<Map<String, Object>> queryActiveById(Long activeId, Long userId) {
		ResultVo<Map<String, Object>> result = new ResultVo<>();
		if (activeId == null) {
			logger.error("参数错误");
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		if (userId == null) {
			logger.error("参数错误");
			result.setCode(ResultCode.Forbidden.getCode());
			return result;
		}
		DjActive active = activeMapper.selectByPrimaryKey(activeId);
		if (active == null) {
			logger.error("参数错误");
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("获取活动失败");
			return result;
		}
		JSONObject json = JSON.parseObject(JSON.toJSONString(active));
		DjUser createUser = userMapper.selectByPrimaryKey(userId);
		if (createUser == null) {
			logger.error("没有用户信息");
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("没有用户信息");
			return result;
			// json.put("activeCreatePeopleName", createUser.getName());
		}
		if (createUser.getRoleId() == RoleEnum.PARTY.getId()) {
			result.setCode(ResultCode.OK.getCode());
			result.setData(json);
			return result;
		}
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
