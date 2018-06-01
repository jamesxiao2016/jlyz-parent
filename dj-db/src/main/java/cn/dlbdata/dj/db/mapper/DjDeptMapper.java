package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.vo.dept.DeptIdNameDto;
import cn.dlbdata.dj.db.vo.party.BranchDeptInfoVo;
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
}