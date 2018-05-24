package cn.dlbdata.dj.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.service.IDeptService;
import cn.dlbdata.dj.serviceimpl.base.BaseService;
import tk.mybatis.mapper.entity.Example;

@Service
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

	/* (non-Javadoc)
	 * <p>Title: getDeptMessage</p>
	 * <p>Description: 党支部信息</p> 
	 * @return  
	 * @see cn.dlbdata.dj.service.IDeptService#getDeptMessage()
	 */
	@Override
	public DjDept getDeptMessage(Long detpartmentId) {
		if(detpartmentId == null) {
			return null;
		}
		return deptMapper.selectByPrimaryKey(detpartmentId);
	}

}
