package cn.dlbdata.dj.service;

import java.util.List;
import java.util.Map;

import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.dto.PartyMemberLifeNotice;
import cn.dlbdata.dj.vo.PageVo;
import cn.dlbdata.dj.vo.study.PendingPtMemberVo;

/**
 * 活动相关的业务逻辑
 * 
 * @author xiaowei
 *
 */
public interface IActiveService {

	/**
	 * 根据ID获取活动详情
	 * 
	 * @param id
	 * @return
	 */
	public DjActive getActiveInfoById(Long id);

	/**
	 * 根据部门ID获取活动列表
	 * 
	 * @param deptId
	 * @return
	 */
	public List<DjActive> getActiveListByDeptId(Long deptId);

	/**
	 * 根据多个部门ID获取活动列表
	 * 
	 * @param deptIds
	 * @return
	 */
	public List<DjActive> getActiveListByDeptIds(Long[] deptIds);

	/**
	 * 根据用户ID获取活动次数（包含驿站活动次数）
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public Integer getActiveNumByUserId(Long userId, Long activeType);
	/**
	 * 
	 */
	public PageVo<List<Map<String, Object>>> getParticipateActive(PartyMemberLifeNotice PartyMemberLifeNotice);
	/**
	 *党员生活通知总数
	 * <p>Title: getParticipateActiveCount</p> 
	 * <p>Description: </p> 
	 * @param
	 * @return
	 */
	public int getParticipateActiveCount(PartyMemberLifeNotice partyMemberLifeNotice);

	/**
	 * 支书查询待办列表
	 * @param deptId 支部ID
	 * @param subTypeId 活动类型Id
	 * @return
	 */
	//TODO :分页暂缓
	List<PendingPtMemberVo>getPendingList(Long deptId,Long subTypeId);

	
}
