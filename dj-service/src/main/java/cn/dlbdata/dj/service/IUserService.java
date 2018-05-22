package cn.dlbdata.dj.service;

import java.util.List;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.vo.LoginVo;
import cn.dlbdata.dj.vo.UserVo;

/**
 * 用户相关的业务逻辑处理
 * 
 * @author xiaowei
 *
 */
public interface IUserService {

	/**
	 * 用户登录
	 * 
	 * @param vo
	 * @return
	 */
	public ResultVo<UserVo> login(LoginVo vo);

	/**
	 * 获取党员详细信息
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	public UserVo getUserDetailById(Long id);

	/**
	 * 根据ID获取用户信息
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	public DjUser getUserInfoById(Long id);

	/**
	 * 根据账号获取用户信息
	 * 
	 * @param name
	 * @return
	 */
	public DjUser getUserInfoByName(String name);

	/**
	 * 根据党员ID获取用户信息
	 * 
	 * @param memberId
	 * @return
	 */
	public DjUser getUserInfoByPartyMemberId(Long memberId);

	/**
	 * 根据用户ID获取党员详细信息
	 * 
	 * @param userId
	 * @return
	 */
	public DjPartymember getPartyMemberInfoByUserId(Long userId);

	/**
	 * 根据用户ID获取用户部门信息
	 * 
	 * @param userId
	 * @return
	 */
	public DjDept getUserDeptByUser(Long userId);

	/**
	 * 修改密码
	 * 
	 * @param vo
	 * @return
	 */
	public ResultVo<String> updatePwd(LoginVo vo);

	/**
	 * 获取用户的年度总积分
	 * 
	 * @param id
	 * @return
	 */
	public Double getSumScoreByUserId(Long userId, Integer year);
	
	/**
	 * 获取所有的用户列表
	 * @return
	 */
	public List<DjUser> getALlUser();
}
