package cn.dlbdata.dj.serviceimpl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.DlbConstant.ResultCode;
import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.mapper.DjPartymemberMapper;
import cn.dlbdata.dj.db.mapper.DjUserMapper;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.service.IUserServcie;
import cn.dlbdata.dj.serviceimpl.base.BaseService;
import cn.dlbdata.dj.vo.LoginVo;
import cn.dlbdata.dj.vo.UserVo;

public class UserService extends BaseService implements IUserServcie {

	@Autowired
	private DjUserMapper userMapper;
	@Autowired
	private DjDeptMapper deptMapper;
	@Autowired
	private DjPartymemberMapper partyMemberMapper;

	@Override
	public DjUser getUserInfoById(Long id) {
		if (id == null) {
			return null;
		}
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public DjUser getUserInfoByName(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		DjUser user = new DjUser();
		user.setName(name);
		List<DjUser> list = userMapper.select(user);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public DjUser getUserInfoByPartyMemberId(Long memberId) {
		if (memberId == null) {
			return null;
		}
		DjUser user = new DjUser();
		user.setDjPartymemberId(memberId);
		List<DjUser> list = userMapper.select(user);
		if (list == null || list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	@Override
	public DjPartymember getPartyMemberInfoByUserId(Long userId) {
		if (userId == null) {
			return null;
		}

		DjUser user = userMapper.selectByPrimaryKey(userId);
		if (user == null) {
			return null;
		}
		DjPartymember member = partyMemberMapper.selectByPrimaryKey(user.getDjPartymemberId());

		return member;
	}

	@Override
	public DjDept getUserDeptByUser(Long userId) {
		if (userId == null) {
			return null;
		}

		DjUser user = userMapper.selectByPrimaryKey(userId);
		if (user == null) {
			return null;
		}
		DjDept dept = deptMapper.selectByPrimaryKey(user.getDeptId());

		return dept;
	}

	@Override
	public ResultVo<UserVo> login(LoginVo vo) {
		ResultVo<UserVo> result = new ResultVo<>(ResultCode.Forbidden.getCode());
		if (StringUtils.isEmpty(vo.getName()) || StringUtils.isEmpty(vo.getPwd())) {
			result.setCode(ResultCode.ParameterError.getCode());
			result.setMsg("请输入用户名或密码");
			return result;
		}

		// 用户检查
		DjUser user = getUserInfoByName(vo.getName());
		if (user == null) {
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("用户名或密码错误");
			return result;
		}

		// 密码验证
		if (!vo.getPwd().equalsIgnoreCase(user.getPwd())) {
			logger.error("密码错误");
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("用户名或密码错误");
		}

		// TODO 角色权限判断

		// 返回数据处理
		UserVo data = new UserVo();
		data.setDeptId(user.getDeptId());
		data.setMemeberId(user.getDjPartymemberId());
		data.setName(vo.getName());
		//data.setToken(token);
		data.setUserId(user.getId());

		DjPartymember member = partyMemberMapper.selectByPrimaryKey(user.getDeptId());
		if (member != null) {
			data.setUserName(member.getName());
		}
		DjDept dept = deptMapper.selectByPrimaryKey(user.getDeptId());
		if (dept != null) {
			data.setPartyBranchName(dept.getPrincipalId() + "");
			data.setDeptName(dept.getName());
		}
		// 返回结果
		result.setCode(ResultCode.OK.getCode());

		return result;
	}

}
