package cn.dlbdata.dj.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.mapper.DjSectionMapper;
import cn.dlbdata.dj.db.mapper.DjUserMapper;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.db.vo.dept.DeptIdNameDto;
import cn.dlbdata.dj.db.vo.dept.DeptTreeVo;
import cn.dlbdata.dj.db.vo.party.BranchDeptInfoVo;
import cn.dlbdata.dj.db.vo.party.SectionInfoVo;
import cn.dlbdata.dj.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.service.IDeptService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import tk.mybatis.mapper.entity.Example;

@Service
public class DeptServiceImpl extends BaseServiceImpl implements IDeptService {
	@Autowired
	private DjDeptMapper deptMapper;
	@Autowired
    private DjSectionMapper sectionMapper;
	@Autowired
	private DjUserMapper userMapper;

	@Override
	public DjDept getDeptInfoById(Long id) {
		if (id == null) {
			logger.error("参数获取失败");
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
	public DjDept getDeptMessage(Long deptId) {
		if(deptId == null) {
			return null;
		}
		return deptMapper.selectByPrimaryKey(deptId);
	}

	/**
	 * 片区负责人获取支部信息列表
	 *
	 * @return
	 */
	@Override
	public List<BranchDeptInfoVo> getBranchDeptInfo(UserVo user) {
		if (user.getSectionId() == null) {
			return new ArrayList<>();
		}
		List<BranchDeptInfoVo> voList = deptMapper.getBranchDeptInfoBySectionId(user.getSectionId());
		return voList;
	}

    /**
     * 获取片区信息（片区负责人登录时首页）
     *
     * @param userId id
     * @return
     */
    @Override
    public SectionInfoVo getSectionInfo(Long userId) {
        DjSection  section = sectionMapper.getByPrincipalId(userId);
        if (section == null) {//当前用户不是片区负责人
            return new SectionInfoVo();
        }
        SectionInfoVo sectionInfoVo = sectionMapper.getSectionInfo(section.getId());
        sectionInfoVo.setPartyCommittee(DlbConstant.PARTYCOMMITTEE_LJZ);
        return sectionInfoVo;
    }

	/* (non-Javadoc)
	 * <p>Title: selectDeptNameByDeptId</p>
	 * <p>Description: 获取支部名称</p> 
	 * @param deptId
	 * @return  
	 * @see cn.dlbdata.dj.service.IDeptService#selectDeptNameByDeptId(java.lang.Long)
	 */
//	@Override
//	public DjDept selectDeptNameByDeptId(Long deptId) {
//		
//		return deptMapper.selectByPrimaryKey(deptId);
//	}
	/**
	 * 查询片区内的党支部Id和Name
	 *
	 * @param sectionId 片区Id
	 * @return DeptIdNameDto
	 */
	@Override
	public List<DeptIdNameDto> getBranchDeptNameAndId(Long sectionId) {
		List<DeptIdNameDto> list = deptMapper.getBranchDeptIdAndName(sectionId);
		return list;
	}

	@Override
	public DjUser getDeptBranch(Long deptId) {
		if (deptId == null) {
			return null;
		}
		DjDept dept = deptMapper.selectByPrimaryKey(deptId);

		if (dept != null) {
			DjUser user = userMapper.selectByPrimaryKey(dept.getPrincipalId());
			return user;
		}

		return null;
	}


    @Override
    public List<DeptTreeVo> getDeptTree(Long sectionId) {
        List<DeptTreeVo> tree = deptMapper.getDeptTree(sectionId);
        return tree;
    }
}
