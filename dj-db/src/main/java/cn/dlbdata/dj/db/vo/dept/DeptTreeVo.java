package cn.dlbdata.dj.db.vo.dept;

import java.util.List;

public class DeptTreeVo {
	private String name;
	private Long deptId;
	private Long parentId;
	private List<DeptTreeVo> children;

	public DeptTreeVo() {

	}

	public DeptTreeVo(Long deptId, String name, Long parentId) {
		this.deptId = deptId;
		this.name = name;
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<DeptTreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<DeptTreeVo> children) {
		this.children = children;
	}
}
