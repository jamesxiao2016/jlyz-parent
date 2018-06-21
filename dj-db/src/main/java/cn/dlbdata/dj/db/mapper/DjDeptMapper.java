package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.vo.dept.DeptAndApplyInfoVo;
import cn.dlbdata.dj.db.vo.dept.DeptAndPartyMemberVo;
import cn.dlbdata.dj.db.vo.dept.DeptIdNameDto;
import cn.dlbdata.dj.db.vo.dept.DeptTreeVo;
import cn.dlbdata.dj.db.vo.party.BranchDeptInfoVo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DjDeptMapper extends Mapper<DjDept> {
	List<BranchDeptInfoVo> getBranchDeptInfoBySectionId(Long sectionId);

	List<DeptIdNameDto> getBranchDeptIdAndName(Long sectionId);

	/**
	 * 获取片区总人数
	 * 
	 * @param sectionId
	 *            片区ID
	 * @return
	 */
	Integer getSectionPeopleNum(Long sectionId);

	/**
	 * 通过片区Id查找下属的党支部Id
	 * @param sectionId
	 * @return
	 */
	List<Long> getDeptIdsBySectionId(long sectionId);

	@Select("select name as name,id as deptId,parent_id as parentId from dj_dept where dj_section_id = #{sectionId}")
	List<DeptTreeVo> getDeptTree(Long sectionId);

	List<DjDept> getByBuildingId(long buildingId);

	DeptAndPartyMemberVo getDeptNameAndPeopleSum(long id);

	List<DeptAndApplyInfoVo>getDeptListAndApplyInfo(Long sectionId);
}