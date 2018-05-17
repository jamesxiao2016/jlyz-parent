package cn.dlbdata.dangjian.service;

import org.springframework.beans.factory.annotation.Autowired;

import cn.dlbdata.dangjian.api.IUserService;
import cn.dlbdata.dangjian.db.mapper.DjUserMapper;
import cn.dlbdata.dangjian.db.pojo.DjUser;

public class UserService implements IUserService {
	@Autowired
	DjUserMapper userMapper;

	public DjUser getUserInfoById(Long id) {
		if (id == null) {
			return null;
		}
		DjUser user = userMapper.selectByPrimaryKey(id);
		return user;
	}
}
