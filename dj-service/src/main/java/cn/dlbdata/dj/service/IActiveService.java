package cn.dlbdata.dj.service;

import java.util.List;
import java.util.Map;

import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.common.core.web.vo.PageVo;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.dto.PartyMemberLifeNotice;
import cn.dlbdata.dj.vo.ActiveVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.db.vo.study.PendingPtMemberVo;
import cn.dlbdata.dj.vo.study.StudyDetailVo;

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
	 * 
	 */
	public PageVo<Map<String, Object>> getParticipateActive(PartyMemberLifeNotice PartyMemberLifeNotice);

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
	 * 支书查询待办列表
	 * 
	 * @param deptId
	 *            支部ID
	 * @param subTypeId
	 *            活动类型Id
	 * @return
	 */
	// TODO :分页暂缓
	Paged<PendingPtMemberVo> getPendingList(Long deptId, Long subTypeId, int pageNum, int pageSize);

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
	 * 获取自主学习详情
	 * 
	 * @param studyId
	 *            自主学习Id
	 * @return
	 */
	StudyDetailVo getStudyDetail(Long studyId);
	/**
	 * 
	 * <p>Title: queryActiveById</p> 
	 * <p>Description: 获取活动详情</p> 
	 * @param activeId
	 * @return
	 */
	ResultVo<Map<String, Object>> queryActiveById(Long activeId, Long userId);

	/**
	 * 活动报名
	 * 
	 * @param activeId
	 * @param user
	 * @return
	 */
	public ResultVo<String> signUp(Long activeId, UserVo user);

	/**
	 * 活动签到
	 * 
	 * @param activeId
	 * @param user
	 * @return
	 */
	public ResultVo<String> signIn(Long activeId, UserVo user);

}
