package cn.dlbdata.dj.service;

import cn.dlbdata.dj.db.vo.ToDoVo;
import cn.dlbdata.dj.db.vo.study.PendingPtMemberVo;
import cn.dlbdata.dj.dto.vangard.VanguardParamVo;
import cn.dlbdata.dj.vo.*;

import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.vo.apply.PioneeringApplyDetailVo;
import cn.dlbdata.dj.db.vo.apply.ScoreApplyVo;

import java.util.List;

public interface IWorkflowService {

	/**
	 * 提交申请
	 * 
	 * @param vo
	 * @param user
	 *            当前登录用户
	 * @return
	 */
	public String doApply(ApplyVo vo, UserVo user);

	/**
	 * 驿站生活违纪违规扣分申请
	 * 
	 * @param param
	 * @param user
	 * @return
	 */

	public ResultVo<Long> doApplyDiscipline(DisciplineVo param, UserVo user);

	/**
	 * 先锋作用申请
	 * 
	 * @param param
	 * @param user
	 * @return
	 */
	public ResultVo<Long> doApplyVanguard(VanguardParamVo param, UserVo user);

	/**
	 * 思想汇报申请
	 * 
	 * @param param
	 * @param user
	 * @return
	 */
	public ResultVo<Long> doApplyThoughts(ThoughtsVo param, UserVo user);

	/**
	 * 业务审批
	 * 
	 * @param id
	 *            记录ID
	 * @param result
	 *            审批意见（1：审批通过 -1：驳回）
	 * @param content
	 *            审批说明
	 * @param user
	 *            当前登录用户
	 * @return
	 */
	public ResultVo<String> doAudit(AuditVo auditVo, UserVo user);

	/**
	 * 支书查询待办列表
	 *
	 * @param deptId
	 *            支部ID
	 * @param subTypeId
	 *            活动类型Id
	 * @return
	 */
	Paged<PendingPtMemberVo> getPendingList(Long deptId, Long subTypeId, int pageNum, int pageSize);

	/**
	 * 查询积分审核列表
	 * @param user user
	 * @param status 审核状态
	 * @return
	 */
	Paged<ScoreApplyVo> getScoreAuditList(UserVo user,Integer status,int pageNum,int pageSize,Long deptId);

    /**
     *查询积分审核详情(先锋作用的三个)
     * @param partyMemberId 党员Id
     * @return
     */
    PioneeringApplyDetailVo getPioneeringApplyDetail(Long partyMemberId);

	/**
	 * 代办列表
	 * @return
	 */
	List<ToDoVo> getTodoList(UserVo user);

	/**
	 * 获取自主活动流程中的积分
	 * @return
	 */
	Float sumScoreInProcess(UserVo userVo);

	/**
	 *新增基础分接口.
	 * @param userId 党员Id
	 * @return
	 */
	ResultVo addBaseScore(Long userId,int year);



}
