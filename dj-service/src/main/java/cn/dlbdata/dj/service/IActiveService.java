package cn.dlbdata.dj.service;

import java.util.List;
import java.util.Map;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.dto.PartyMemberLifeNotice;
import cn.dlbdata.dj.dto.active.ActiveSignUpRequest;
import cn.dlbdata.dj.vo.ActiveVo;
import cn.dlbdata.dj.vo.PageVo;

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
	 * @param PartyMemberLifeNotice
	 * @return
	 */
	public int getParticipateActiveCount(PartyMemberLifeNotice partyMemberLifeNotice);
	
}
