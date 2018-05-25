package cn.dlbdata.dj.service;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.vo.ApplyVo;
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
	public String apply(ApplyVo vo, UserVo user);

	/**
	 * 遵章守纪申请
	 * 
	 * @param param
	 * @param user
	 * @return
	 */
	public ResultVo<Long> applyDiscipline(DisciplineVo param, UserVo user);

	/**
	 * 先锋作用申请
	 * 
	 * @param params
	 * @param user
	 * @return
	 */
	public ResultVo<Long> applyVanguard(VanguardVo[] params, UserVo user);

	/**
	 * 思想汇报申请
	 * 
	 * @param param
	 * @param user
	 * @return
	 */
	public ResultVo<Long> applyThoughts(ThoughtsVo param, UserVo user);

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
	public String audit(Long id, Integer result, String content, UserVo user);
}
