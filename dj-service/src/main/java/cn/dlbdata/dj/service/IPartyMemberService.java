package cn.dlbdata.dj.service;

import java.util.List;

import cn.dlbdata.dj.db.pojo.DjPartymember;

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
}
