package cn.dlbdata.dj.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.dlbdata.dj.db.mapper.DjActiveDeptMapper;
import cn.dlbdata.dj.db.mapper.DjActiveMapper;
import cn.dlbdata.dj.db.pojo.DjActive;
import cn.dlbdata.dj.db.pojo.DjActiveDept;
import cn.dlbdata.dj.service.IActiveService;
import cn.dlbdata.dj.serviceimpl.base.BaseService;

public class ActiveService extends BaseService implements IActiveService {
	@Autowired
	private DjActiveMapper activeMapper;
	@Autowired
	private DjActiveDeptMapper activeDeptMapper;

	@Override
	public DjActive getActiveInfoById(Long id) {
		if (id == null) {
			return null;
		}
		return activeMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<DjActive> getActiveListByDeptId(Long deptId) {
		if (deptId == null) {
			return null;
		}
		DjActiveDept condition = new DjActiveDept();
		condition.setDjDeptId(deptId);
		List<DjActiveDept> list = activeDeptMapper.select(condition);
		return null;
	}

	@Override
	public List<DjActive> getActiveListByDeptIds(Long[] deptIds) {
		if (deptIds == null) {
			return null;
		}
		return null;
	}

}
