package cn.dlbdata.dj.serviceimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.util.DatetimeUtil;
import cn.dlbdata.dj.db.mapper.DjActiveDeptMapper;
import cn.dlbdata.dj.db.mapper.DjActiveMapper;
import cn.dlbdata.dj.db.mapper.DjPartymemberMapper;
import cn.dlbdata.dj.db.mapper.DjScoreMapper;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.db.pojo.DjActiveDept;
import cn.dlbdata.dj.dto.PartyMemberLifeNotice;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.serviceimpl.base.BaseService;
import cn.dlbdata.dj.vo.PageVo;

@Service
public class ActiveService extends BaseService implements IActiveService {
	@Autowired
	private DjActiveMapper activeMapper;
	@Autowired
	private DjActiveDeptMapper activeDeptMapper;
	@Autowired
	private DjPartymemberMapper partymemberMapper;
	@Autowired
	private DjScoreMapper scoreMapper;
	
	
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
		if(partyMemberLifeNotice == null) {
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

	/* (non-Javadoc)
	 * <p>Title: getParticipateActiveCount</p>
	 * <p>Description: 党员生活通知总数</p> 
	 * @param PartyMemberLifeNotice
	 * @return  
	 * @see cn.dlbdata.dj.service.IActiveService#getParticipateActiveCount(cn.dlbdata.dj.db.resquest.PartyMemberLifeNotice)
	 */
	@Override
	public int getParticipateActiveCount(PartyMemberLifeNotice partyMemberLifeNotice) {
		if(partyMemberLifeNotice == null) {
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

}
