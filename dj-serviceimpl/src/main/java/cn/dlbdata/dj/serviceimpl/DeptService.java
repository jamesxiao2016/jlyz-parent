package cn.dlbdata.dj.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.service.IDeptService;
import cn.dlbdata.dj.serviceimpl.base.BaseService;
import tk.mybatis.mapper.entity.Example;

public class DeptService extends BaseService implements IDeptService {
	@Autowired
	private DjDeptMapper deptMapper;

	@Override
	public DjDept getDeptInfoById(Long id) {
		if (id == null) {
			return null;
		}
		return deptMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<DjDept> getDeptListByParentId(Long parentId) {
		if (parentId == null) {
			return null;
		}
		Example example = new Example(DjDept.class);
		example.createCriteria().andEqualTo("parentId", parentId);
		return deptMapper.selectByExample(example);
	}

}
