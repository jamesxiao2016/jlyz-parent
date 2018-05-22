package cn.dlbdata.dj.service;

import java.util.List;

import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.pojo.DjScore;
import cn.dlbdata.dj.vo.PartyVo;

/**
 * 党员相关的业务逻辑
 * 
 * @author xiaowei
 *
 */
public interface IPartyMemberService {

	/**
	 * 根据id获取党员信息
	 * 
	 * @param id
	 *            党员ID
	 * @return
	 */
	public DjPartymember getInfoById(Long id);

	/**
	 * 根据部门ID获取党员列表
	 * 
	 * @param deptId
	 *            部门ID
	 * @return
	 */
	public List<DjPartymember> getPartyMemberListByDeptId(Long deptId);

	/**
	 * 根据党员ID获取党员的积分及参加活动的次数
	 * 
	 * @param memberId
	 *            党员ID
	 * @return
	 */
	public PartyVo getScoreAndNumByMemberId(Long memberId);

	/**
	 * 获取积分明细
	 * 
	 * @param userId
	 * @param year
	 * @return
	 */
	public List<DjScore> getScoreListByUserId(Long userId, Integer year);

	/**
	 * 获取每个项目的积分及总积分
	 * 
	 * @param userId
	 * @param year
	 * @return
	 */
	public List<DjScore> getTypeScoreListByUserId(Long userId, Integer year);
}
