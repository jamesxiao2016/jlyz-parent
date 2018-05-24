package cn.dlbdata.dj.service;

import cn.dlbdata.dj.vo.ApplyVo;
import cn.dlbdata.dj.vo.UserVo;

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
	public String audit(Long id, String result, String content, UserVo user);
}
