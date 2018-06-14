package cn.dlbdata.dj.serviceimpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.db.mapper.DjRoleMapper;
import cn.dlbdata.dj.db.pojo.DjRole;
import cn.dlbdata.dj.service.IRoleService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;

@Service
public class RoleServiceImpl extends BaseServiceImpl implements IRoleService {
	@Autowired
	private DjRoleMapper roleMapper;
	@Override
	public DjRole getRoleInfoById(Long id) {
		if(id == null) {
			return null;
		}
		return roleMapper.selectByPrimaryKey(id);
	}
	
	/* (non-Javadoc)
	 * <p>Title: deleteById</p>
	 * <p>Description: 删除角色</p> 
	 * @param id
	 * @return  
	 * @see cn.dlbdata.dj.service.IRoleService#deleteById(java.lang.Long)
	 */
	@Override
	public Long deleteById(Long id) {
		if (id == null) {
			logger.error("id is null");
			return null;
		}
		roleMapper.deleteByPrimaryKey(id);
		return id;
	}

	/* (non-Javadoc)
	 * <p>Title: saveOrUpdate</p>
	 * <p>Description: 保存或者更新</p> 
	 * @param djRole
	 * @return  
	 * @see cn.dlbdata.dj.service.IRoleService#saveOrUpdate(cn.dlbdata.dj.db.pojo.DjRole)
	 */
	@Override
	public Long saveOrUpdate(DjRole djRole) {
		if (djRole == null) {
			return null;
		}
		DjRole record = null;
		Long id = djRole.getId();
		if (id != null) {
			record = roleMapper.selectByPrimaryKey(id);
		}
		boolean isSave = false;
		if (record == null) {
			isSave = true;
			record = new DjRole();
		}
		BeanUtils.copyProperties(djRole, record);
		if (isSave) {
			id = DigitUtil.generatorLongId();
			record.setId(id);
			roleMapper.insertSelective(record);
		} else {
			roleMapper.updateByPrimaryKey(record);
		}

		return id;
	}

}
