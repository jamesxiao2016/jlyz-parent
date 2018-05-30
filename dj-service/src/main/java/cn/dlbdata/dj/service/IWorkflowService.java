package cn.dlbdata.dj.service;

import org.springframework.transaction.annotation.Transactional;

import cn.dlbdata.dj.common.core.util.Paged;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjApply;
import cn.dlbdata.dj.db.vo.apply.PioneeringApplyDetailVo;
import cn.dlbdata.dj.db.vo.apply.ScoreApplyVo;
import cn.dlbdata.dj.vo.ApplyVo;
import cn.dlbdata.dj.vo.AuditVo;
import cn.dlbdata.dj.vo.DisciplineVo;
import cn.dlbdata.dj.vo.ThoughtsVo;
import cn.dlbdata.dj.vo.UserVo;
import cn.dlbdata.dj.vo.VanguardVo;

public interface IWorkflowService {

	/**
	 * 提交申请
	 * 
	 * @param vo
	 * @param user
	 *            当前登录用户
	 * @return
	 */
	@Transactional
	public String doApply(ApplyVo vo, UserVo user);

	/**
	 * 遵章守纪申请
	 * 
	 * @param param
	 * @param user
	 * @return
	 */
	@Transactional
	public ResultVo<Long> doApplyDiscipline(DisciplineVo param, UserVo user);

	/**
	 * 先锋作用申请
	 * 
	 * @param params
	 * @param user
	 * @return
	 */
	@Transactional
	public ResultVo<Long> doApplyVanguard(VanguardVo[] params, UserVo user);

	/**
	 * 思想汇报申请
	 * 
	 * @param param
	 * @param user
	 * @return
	 */
	@Transactional
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
	@Transactional
	public ResultVo<String> doAudit(AuditVo auditVo, UserVo user);

	/**
	 * 获取待办列表
	 * 
	 * @param userId
	 *            用户ID
	 * @param deptId
	 *            部门ID
	 * @param typeId
	 *            分类ID
	 * @param roleId
	 *            角色ID
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            每页显示数量
	 * @return
	 */
	public Paged<DjApply> getPendingList(Long userId, Long deptId, Long typeId, Long roleId, Integer pageNum,
			Integer pageSize);

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
}
