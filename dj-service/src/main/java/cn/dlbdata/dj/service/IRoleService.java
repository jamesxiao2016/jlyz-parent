package cn.dlbdata.dj.service;

import cn.dlbdata.dj.db.pojo.DjRole;

/**
 * 角色相关的业务逻辑处理
 * @author xiaowei
 *
 */
public interface IRoleService {
	
	public DjRole getRoleInfoById(Long id);
}
