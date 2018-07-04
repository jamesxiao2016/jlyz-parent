package cn.dlbdata.dj.service;

import java.util.Map;

import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.dto.PartyMemberLifeNotice;
import cn.dlbdata.dj.vo.ActiveVo;
import cn.dlbdata.dj.vo.UserVo;

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
	 * 根据用户ID获取活动次数（包含驿站活动次数）
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public Integer getActiveNumByUserId(Long userId, Long activeType);

	/**
	 * 获取党员通知列表
	 */
	public Paged<Map<String, Object>> getParticipateActive(PartyMemberLifeNotice PartyMemberLifeNotice);

	/**
	 * 党支书获取活动列表
	 */
	public Paged<Map<String, Object>> getActiveListByDeptId(PartyMemberLifeNotice PartyMemberLifeNotice);

	/**
	 * 
	 * <p>
	 * Title: getParticipateActiveOne
	 * </p>
	 * <p>
	 * Description: 获取党员生活通知第一条
	 * </p>
	 * 
	 * @param PartyMemberLifeNotice
	 * @return
	 */
	public ResultVo<Map<String, Object>> getParticipateActiveOne(PartyMemberLifeNotice PartyMemberLifeNotice);

	/**
	 * 党员生活通知总数
	 * <p>
	 * Title: getParticipateActiveCount
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param
	 * @return
	 */
	public int getParticipateActiveCount(PartyMemberLifeNotice partyMemberLifeNotice);

	/**
	 * 创建活动
	 * 
	 * @param vo
	 *            活动内容
	 * @param user
	 *            当前用户
	 * @return
	 */
	public ResultVo<Long> createActive(ActiveVo vo, UserVo user);

	/**
	 * 
	 * <p>
	 * Title: queryActiveById
	 * </p>
	 * <p>
	 * Description: 获取活动详情
	 * </p>
	 * 
	 * @param activeId
	 * @return
	 */
	ResultVo<Map<String, Object>> queryActiveById(Long activeId, Long roleId);

	/**
	 * 活动报名
	 * 
	 * @param activeId
	 * @param user
	 * @return
	 */
	public ResultVo<String> signUp(Long activeId, UserVo user);

	/**
	 * 取消活动
	 * 
	 * @param activeId
	 * @param user
	 * @return
	 */
	public ResultVo<String> cancelActiveById(Long activeId, UserVo user);

	/**
	 * 活动签到
	 * 
	 * @param activeId
	 * @param user
	 * @return
	 */
	public ResultVo<String> signIn(Long activeId, UserVo user);
	/**
	 * 
	 * <p>Title: deleteById</p> 
	 * <p>Description: 物理删除活动</p> 
	 * @param id
	 * @return
	 */
	public Long deleteById(Long id);
	
	/**
	 * 
	 * <p>
	 * Title: queryActiveById
	 * </p>
	 * <p>
	 * Description: 获取活动详情
	 * </p>
	 * 
	 * @param activeId
	 * @return
	 */
	ResultVo<Map<String, Object>> queryAdminActiveById(Long activeId, Long roleId);
	
	
	ResultVo<Map<String, Object>> getActiveIndex(Long recordId);
}
