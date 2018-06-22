package cn.dlbdata.dj.service;

import java.util.List;

import cn.dlbdata.dj.db.dto.dept.DeptAddOrUpdateDto;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.db.vo.dept.*;
import cn.dlbdata.dj.db.vo.party.BranchDeptInfoVo;
import cn.dlbdata.dj.db.vo.party.SectionInfoVo;
import cn.dlbdata.dj.vo.UserVo;

/**
 * 部门处理相关的业务逻辑
 * @author xiaowei
 *
 */
public interface IDeptService {

	/**
	 * 根据ID获取部门信息
	 * @param id 部门ID
	 * @return
	 */
	public DjDept getDeptInfoById(Long id);
	
	/**
	 * 根据部门名称获取部门对象
	 * @param deptName
	 * @return
	 */
	public DjDept getDeptInfoByName(String deptName);
	
	/**
	 * 根据ID获取下级部门列表
	 * @param parentId 部门ID
	 * @return
	 */
	public List<DjDept> getDeptListByParentId(Long parentId);
	/**
	 * 
	 * <p>Title: getDeptMessage</p> 
	 * <p>Description:党支部相关信息 </p> 
	 * @param departmentId
	 * @return
	 */
	public DjDept getDeptMessage(Long departmentId);

	/**
	 * 片区负责人获取支部信息列表
	 * @return
	 */
	List<BranchDeptInfoVo>getBranchDeptInfo(UserVo user);

	/**
	 * 获取片区信息（片区负责人登录时首页）
	 * @param userId id
	 * @return
	 */
	SectionInfoVo getSectionInfo(Long userId);



	/**
	 * 查询片区内的党支部Id和Name
	 * @param sectionId 片区Id
	 * @return DeptIdNameDto
	 */
	List<DeptIdNameDto> getBranchDeptNameAndId(Long sectionId);
	
	/**
	 * 获取部门的党支部书记
	 * @param deptId
	 * @return
	 */
	DjUser getDeptBranch(Long deptId);

	List<DeptTreeVo> getDeptTree(Long sectionId);

	/**
	 * 新增党支部
	 * @param dto
	 * @param user
	 * @return
	 */
	boolean addBranch(DeptAddOrUpdateDto dto,UserVo user);

	/**
	 * 修改党支部.
	 * @param id 党支部Id
	 * @return
	 */
	boolean updateBranch(Long id,DeptAddOrUpdateDto dto,UserVo user);

	/**
	 * 作废党支部.
	 * @param id
	 * @param user
	 * @return
	 */
	boolean invalidBranch(Long id,UserVo user);

	/**
	 * 查询党支部详情
	 * @param id
	 * @return
	 */
	DeptDetailVo getDetailBy(Long id);

	/**
	 * 查询党支部和党员列表
	 * @param id 党支部Id
	 * @return
	 */
	DeptAndPartyMemberVo getDeptAndPartyMemberList(Long id);

	/**
	 * 获取片区内的党支部列表和支部内是否有先锋作用申请
	 * @param sectionId
	 * @return
	 */
	List<DeptAndApplyInfoVo> getDeptListAndApplyInfo(Long sectionId);

	/**
	 * 获取片区及党支部
	 * @return
	 */
	List<SelectTreeVo> getSectionAndDeptTree();
}
