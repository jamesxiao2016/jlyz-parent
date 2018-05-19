package cn.dlbdata.dj.serviceimpl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.mapper.DjPartymemberMapper;
import cn.dlbdata.dj.db.mapper.DjUserMapper;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.service.IUserServcie;
import cn.dlbdata.dj.serviceimpl.base.BaseService;

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

}
