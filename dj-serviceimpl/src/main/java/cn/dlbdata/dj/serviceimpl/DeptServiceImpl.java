package cn.dlbdata.dj.serviceimpl;

import java.util.List;

import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.mapper.DjSectionMapper;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.db.vo.dept.DeptIdNameDto;
import cn.dlbdata.dj.db.vo.party.BranchDeptInfoVo;
import cn.dlbdata.dj.db.vo.party.SectionInfoVo;
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
	public DjDept getDeptMessage(Long deptId) {
		if(deptId == null) {
			return null;
		}
		return deptMapper.selectByPrimaryKey(deptId);
	}

	/**
	 * 片区负责人获取支部信息列表
	 *
	 * @param sectionId
	 * @return
	 */
	@Override
	public List<BranchDeptInfoVo> getBranchDeptInfo(Long sectionId) {
		List<BranchDeptInfoVo> voList = deptMapper.getBranchDeptInfoBySectionId(sectionId);
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
}
