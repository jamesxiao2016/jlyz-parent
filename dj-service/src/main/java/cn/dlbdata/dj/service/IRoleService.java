package cn.dlbdata.dj.service;

import cn.dlbdata.dj.db.pojo.DjRole;

/**
 * 角色相关的业务逻辑处理
 * @author xiaowei
 *
 */
public interface IRoleService {
	
	public DjRole getRoleInfoById(Long id);
	
	/**
	 * 
	 * <p>Title: deleteById</p> 
	 * <p>Description: 删除角色</p> 
	 * @param id
	 * @return
	 */
	public Long deleteById(Long id);
	
	/**
	 * 保存或更新
	 * 
	 * @param section
	 * @return
	 */
	public Long saveOrUpdate(DjRole djRole);
	
}
